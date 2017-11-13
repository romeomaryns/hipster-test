import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { CandleStickData } from './candle-stick-data.model';
import { CandleStickDataService } from './candle-stick-data.service';

@Component({
    selector: 'jhi-candle-stick-data-detail',
    templateUrl: './candle-stick-data-detail.component.html'
})
export class CandleStickDataDetailComponent implements OnInit, OnDestroy {

    candleStickData: CandleStickData;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private candleStickDataService: CandleStickDataService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCandleStickData();
    }

    load(id) {
        this.candleStickDataService.find(id).subscribe((candleStickData) => {
            this.candleStickData = candleStickData;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCandleStickData() {
        this.eventSubscriber = this.eventManager.subscribe(
            'candleStickDataListModification',
            (response) => this.load(this.candleStickData.id)
        );
    }
}
