import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PositionSide } from './position-side.model';
import { PositionSidePopupService } from './position-side-popup.service';
import { PositionSideService } from './position-side.service';

@Component({
    selector: 'jhi-position-side-delete-dialog',
    templateUrl: './position-side-delete-dialog.component.html'
})
export class PositionSideDeleteDialogComponent {

    positionSide: PositionSide;

    constructor(
        private positionSideService: PositionSideService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.positionSideService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'positionSideListModification',
                content: 'Deleted an positionSide'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-position-side-delete-popup',
    template: ''
})
export class PositionSideDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private positionSidePopupService: PositionSidePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.positionSidePopupService
                .open(PositionSideDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
