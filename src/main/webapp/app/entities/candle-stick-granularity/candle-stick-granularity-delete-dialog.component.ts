import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CandleStickGranularity } from './candle-stick-granularity.model';
import { CandleStickGranularityPopupService } from './candle-stick-granularity-popup.service';
import { CandleStickGranularityService } from './candle-stick-granularity.service';

@Component({
    selector: 'jhi-candle-stick-granularity-delete-dialog',
    templateUrl: './candle-stick-granularity-delete-dialog.component.html'
})
export class CandleStickGranularityDeleteDialogComponent {

    candleStickGranularity: CandleStickGranularity;

    constructor(
        private candleStickGranularityService: CandleStickGranularityService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.candleStickGranularityService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'candleStickGranularityListModification',
                content: 'Deleted an candleStickGranularity'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-candle-stick-granularity-delete-popup',
    template: ''
})
export class CandleStickGranularityDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private candleStickGranularityPopupService: CandleStickGranularityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.candleStickGranularityPopupService
                .open(CandleStickGranularityDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
