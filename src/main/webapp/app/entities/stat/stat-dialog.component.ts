import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Stat } from './stat.model';
import { StatPopupService } from './stat-popup.service';
import { StatService } from './stat.service';
import { Instrument, InstrumentService } from '../instrument';
import { CandleStickGranularity, CandleStickGranularityService } from '../candle-stick-granularity';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-stat-dialog',
    templateUrl: './stat-dialog.component.html'
})
export class StatDialogComponent implements OnInit {

    stat: Stat;
    isSaving: boolean;

    instruments: Instrument[];

    granularities: CandleStickGranularity[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private statService: StatService,
        private instrumentService: InstrumentService,
        private candleStickGranularityService: CandleStickGranularityService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.instrumentService.query()
            .subscribe((res: ResponseWrapper) => { this.instruments = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.candleStickGranularityService
            .query({filter: 'stat-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.stat.granularity || !this.stat.granularity.id) {
                    this.granularities = res.json;
                } else {
                    this.candleStickGranularityService
                        .find(this.stat.granularity.id)
                        .subscribe((subRes: CandleStickGranularity) => {
                            this.granularities = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.stat.id !== undefined) {
            this.subscribeToSaveResponse(
                this.statService.update(this.stat));
        } else {
            this.subscribeToSaveResponse(
                this.statService.create(this.stat));
        }
    }

    private subscribeToSaveResponse(result: Observable<Stat>) {
        result.subscribe((res: Stat) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Stat) {
        this.eventManager.broadcast({ name: 'statListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackInstrumentById(index: number, item: Instrument) {
        return item.id;
    }

    trackCandleStickGranularityById(index: number, item: CandleStickGranularity) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-stat-popup',
    template: ''
})
export class StatPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private statPopupService: StatPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.statPopupService
                    .open(StatDialogComponent as Component, params['id']);
            } else {
                this.statPopupService
                    .open(StatDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
