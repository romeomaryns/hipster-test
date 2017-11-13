import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Instrument } from './instrument.model';
import { InstrumentPopupService } from './instrument-popup.service';
import { InstrumentService } from './instrument.service';

@Component({
    selector: 'jhi-instrument-dialog',
    templateUrl: './instrument-dialog.component.html'
})
export class InstrumentDialogComponent implements OnInit {

    instrument: Instrument;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private instrumentService: InstrumentService,
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
        if (this.instrument.id !== undefined) {
            this.subscribeToSaveResponse(
                this.instrumentService.update(this.instrument));
        } else {
            this.subscribeToSaveResponse(
                this.instrumentService.create(this.instrument));
        }
    }

    private subscribeToSaveResponse(result: Observable<Instrument>) {
        result.subscribe((res: Instrument) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Instrument) {
        this.eventManager.broadcast({ name: 'instrumentListModification', content: 'OK'});
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
    selector: 'jhi-instrument-popup',
    template: ''
})
export class InstrumentPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private instrumentPopupService: InstrumentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.instrumentPopupService
                    .open(InstrumentDialogComponent as Component, params['id']);
            } else {
                this.instrumentPopupService
                    .open(InstrumentDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
