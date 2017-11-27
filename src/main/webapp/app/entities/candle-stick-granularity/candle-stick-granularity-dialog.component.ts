import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CandleStickGranularity } from './candle-stick-granularity.model';
import { CandleStickGranularityPopupService } from './candle-stick-granularity-popup.service';
import { CandleStickGranularityService } from './candle-stick-granularity.service';

@Component({
    selector: 'jhi-candle-stick-granularity-dialog',
    templateUrl: './candle-stick-granularity-dialog.component.html'
})
export class CandleStickGranularityDialogComponent implements OnInit {

    candleStickGranularity: CandleStickGranularity;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private candleStickGranularityService: CandleStickGranularityService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.candleStickGranularity.id !== undefined) {
            this.subscribeToSaveResponse(
                this.candleStickGranularityService.update(this.candleStickGranularity));
        } else {
            this.subscribeToSaveResponse(
                this.candleStickGranularityService.create(this.candleStickGranularity));
        }
    }

    private subscribeToSaveResponse(result: Observable<CandleStickGranularity>) {
        result.subscribe((res: CandleStickGranularity) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: CandleStickGranularity) {
        this.eventManager.broadcast({ name: 'candleStickGranularityListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-candle-stick-granularity-popup',
    template: ''
})
export class CandleStickGranularityPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private candleStickGranularityPopupService: CandleStickGranularityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.candleStickGranularityPopupService
                    .open(CandleStickGranularityDialogComponent as Component, params['id']);
            } else {
                this.candleStickGranularityPopupService
                    .open(CandleStickGranularityDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
