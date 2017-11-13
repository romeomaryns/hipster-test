import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CandleStickData } from './candle-stick-data.model';
import { CandleStickDataPopupService } from './candle-stick-data-popup.service';
import { CandleStickDataService } from './candle-stick-data.service';

@Component({
    selector: 'jhi-candle-stick-data-delete-dialog',
    templateUrl: './candle-stick-data-delete-dialog.component.html'
})
export class CandleStickDataDeleteDialogComponent {

    candleStickData: CandleStickData;

    constructor(
        private candleStickDataService: CandleStickDataService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.candleStickDataService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'candleStickDataListModification',
                content: 'Deleted an candleStickData'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-candle-stick-data-delete-popup',
    template: ''
})
export class CandleStickDataDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private candleStickDataPopupService: CandleStickDataPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.candleStickDataPopupService
                .open(CandleStickDataDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
