import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PositionSide } from './position-side.model';
import { PositionSidePopupService } from './position-side-popup.service';
import { PositionSideService } from './position-side.service';

@Component({
    selector: 'jhi-position-side-dialog',
    templateUrl: './position-side-dialog.component.html'
})
export class PositionSideDialogComponent implements OnInit {

    positionSide: PositionSide;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private positionSideService: PositionSideService,
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
        if (this.positionSide.id !== undefined) {
            this.subscribeToSaveResponse(
                this.positionSideService.update(this.positionSide));
        } else {
            this.subscribeToSaveResponse(
                this.positionSideService.create(this.positionSide));
        }
    }

    private subscribeToSaveResponse(result: Observable<PositionSide>) {
        result.subscribe((res: PositionSide) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: PositionSide) {
        this.eventManager.broadcast({ name: 'positionSideListModification', content: 'OK'});
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
    selector: 'jhi-position-side-popup',
    template: ''
})
export class PositionSidePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private positionSidePopupService: PositionSidePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.positionSidePopupService
                    .open(PositionSideDialogComponent as Component, params['id']);
            } else {
                this.positionSidePopupService
                    .open(PositionSideDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
