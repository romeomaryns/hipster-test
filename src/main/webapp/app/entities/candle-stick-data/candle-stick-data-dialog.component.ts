import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CandleStickData } from './candle-stick-data.model';
import { CandleStickDataPopupService } from './candle-stick-data-popup.service';
import { CandleStickDataService } from './candle-stick-data.service';

@Component({
    selector: 'jhi-candle-stick-data-dialog',
    templateUrl: './candle-stick-data-dialog.component.html'
})
export class CandleStickDataDialogComponent implements OnInit {

    candleStickData: CandleStickData;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private candleStickDataService: CandleStickDataService,
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
        if (this.candleStickData.id !== undefined) {
            this.subscribeToSaveResponse(
                this.candleStickDataService.update(this.candleStickData));
        } else {
            this.subscribeToSaveResponse(
                this.candleStickDataService.create(this.candleStickData));
        }
    }

    private subscribeToSaveResponse(result: Observable<CandleStickData>) {
        result.subscribe((res: CandleStickData) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: CandleStickData) {
        this.eventManager.broadcast({ name: 'candleStickDataListModification', content: 'OK'});
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
    selector: 'jhi-candle-stick-data-popup',
    template: ''
})
export class CandleStickDataPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private candleStickDataPopupService: CandleStickDataPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.candleStickDataPopupService
                    .open(CandleStickDataDialogComponent as Component, params['id']);
            } else {
                this.candleStickDataPopupService
                    .open(CandleStickDataDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
