import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CandleStick } from './candle-stick.model';
import { CandleStickPopupService } from './candle-stick-popup.service';
import { CandleStickService } from './candle-stick.service';
import { CandleStickData, CandleStickDataService } from '../candle-stick-data';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-candle-stick-dialog',
    templateUrl: './candle-stick-dialog.component.html'
})
export class CandleStickDialogComponent implements OnInit {

    candleStick: CandleStick;
    isSaving: boolean;

    bids: CandleStickData[];

    asks: CandleStickData[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private candleStickService: CandleStickService,
        private candleStickDataService: CandleStickDataService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.candleStickDataService
            .query({filter: 'candlestick-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.candleStick.bid || !this.candleStick.bid.id) {
                    this.bids = res.json;
                } else {
                    this.candleStickDataService
                        .find(this.candleStick.bid.id)
                        .subscribe((subRes: CandleStickData) => {
                            this.bids = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.candleStickDataService
            .query({filter: 'candlestick-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.candleStick.ask || !this.candleStick.ask.id) {
                    this.asks = res.json;
                } else {
                    this.candleStickDataService
                        .find(this.candleStick.ask.id)
                        .subscribe((subRes: CandleStickData) => {
                            this.asks = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.candleStick.id !== undefined) {
            this.subscribeToSaveResponse(
                this.candleStickService.update(this.candleStick));
        } else {
            this.subscribeToSaveResponse(
                this.candleStickService.create(this.candleStick));
        }
    }

    private subscribeToSaveResponse(result: Observable<CandleStick>) {
        result.subscribe((res: CandleStick) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: CandleStick) {
        this.eventManager.broadcast({ name: 'candleStickListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackCandleStickDataById(index: number, item: CandleStickData) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-candle-stick-popup',
    template: ''
})
export class CandleStickPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private candleStickPopupService: CandleStickPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.candleStickPopupService
                    .open(CandleStickDialogComponent as Component, params['id']);
            } else {
                this.candleStickPopupService
                    .open(CandleStickDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
