import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { OandaAccount } from './oanda-account.model';
import { OandaAccountPopupService } from './oanda-account-popup.service';
import { OandaAccountService } from './oanda-account.service';
import { User, UserService } from '../../shared';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-oanda-account-dialog',
    templateUrl: './oanda-account-dialog.component.html'
})
export class OandaAccountDialogComponent implements OnInit {

    oandaAccount: OandaAccount;
    isSaving: boolean;

    users: User[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private oandaAccountService: OandaAccountService,
        private userService: UserService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.userService.query()
            .subscribe((res: ResponseWrapper) => { this.users = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.oandaAccount.id !== undefined) {
            this.subscribeToSaveResponse(
                this.oandaAccountService.update(this.oandaAccount));
        } else {
            this.subscribeToSaveResponse(
                this.oandaAccountService.create(this.oandaAccount));
        }
    }

    private subscribeToSaveResponse(result: Observable<OandaAccount>) {
        result.subscribe((res: OandaAccount) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: OandaAccount) {
        this.eventManager.broadcast({ name: 'oandaAccountListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-oanda-account-popup',
    template: ''
})
export class OandaAccountPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private oandaAccountPopupService: OandaAccountPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.oandaAccountPopupService
                    .open(OandaAccountDialogComponent as Component, params['id']);
            } else {
                this.oandaAccountPopupService
                    .open(OandaAccountDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
