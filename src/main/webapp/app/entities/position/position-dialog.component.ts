import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Position } from './position.model';
import { PositionPopupService } from './position-popup.service';
import { PositionService } from './position.service';
import { OandaAccount, OandaAccountService } from '../oanda-account';
import { PositionSide, PositionSideService } from '../position-side';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-position-dialog',
    templateUrl: './position-dialog.component.html'
})
export class PositionDialogComponent implements OnInit {

    position: Position;
    isSaving: boolean;

    oandaaccounts: OandaAccount[];

    longvalues: PositionSide[];

    shortvalues: PositionSide[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private positionService: PositionService,
        private oandaAccountService: OandaAccountService,
        private positionSideService: PositionSideService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.oandaAccountService.query()
            .subscribe((res: ResponseWrapper) => { this.oandaaccounts = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.positionSideService
            .query({filter: 'position-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.position.longValue || !this.position.longValue.id) {
                    this.longvalues = res.json;
                } else {
                    this.positionSideService
                        .find(this.position.longValue.id)
                        .subscribe((subRes: PositionSide) => {
                            this.longvalues = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.positionSideService
            .query({filter: 'position-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.position.shortValue || !this.position.shortValue.id) {
                    this.shortvalues = res.json;
                } else {
                    this.positionSideService
                        .find(this.position.shortValue.id)
                        .subscribe((subRes: PositionSide) => {
                            this.shortvalues = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.position.id !== undefined) {
            this.subscribeToSaveResponse(
                this.positionService.update(this.position));
        } else {
            this.subscribeToSaveResponse(
                this.positionService.create(this.position));
        }
    }

    private subscribeToSaveResponse(result: Observable<Position>) {
        result.subscribe((res: Position) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Position) {
        this.eventManager.broadcast({ name: 'positionListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackOandaAccountById(index: number, item: OandaAccount) {
        return item.id;
    }

    trackPositionSideById(index: number, item: PositionSide) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-position-popup',
    template: ''
})
export class PositionPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private positionPopupService: PositionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.positionPopupService
                    .open(PositionDialogComponent as Component, params['id']);
            } else {
                this.positionPopupService
                    .open(PositionDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
