import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { OandaAccount } from './oanda-account.model';
import { OandaAccountPopupService } from './oanda-account-popup.service';
import { OandaAccountService } from './oanda-account.service';

@Component({
    selector: 'jhi-oanda-account-delete-dialog',
    templateUrl: './oanda-account-delete-dialog.component.html'
})
export class OandaAccountDeleteDialogComponent {

    oandaAccount: OandaAccount;

    constructor(
        private oandaAccountService: OandaAccountService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.oandaAccountService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'oandaAccountListModification',
                content: 'Deleted an oandaAccount'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-oanda-account-delete-popup',
    template: ''
})
export class OandaAccountDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private oandaAccountPopupService: OandaAccountPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.oandaAccountPopupService
                .open(OandaAccountDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
