import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CandleStick } from './candle-stick.model';
import { CandleStickPopupService } from './candle-stick-popup.service';
import { CandleStickService } from './candle-stick.service';

@Component({
    selector: 'jhi-candle-stick-delete-dialog',
    templateUrl: './candle-stick-delete-dialog.component.html'
})
export class CandleStickDeleteDialogComponent {

    candleStick: CandleStick;

    constructor(
        private candleStickService: CandleStickService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.candleStickService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'candleStickListModification',
                content: 'Deleted an candleStick'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-candle-stick-delete-popup',
    template: ''
})
export class CandleStickDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private candleStickPopupService: CandleStickPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.candleStickPopupService
                .open(CandleStickDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
