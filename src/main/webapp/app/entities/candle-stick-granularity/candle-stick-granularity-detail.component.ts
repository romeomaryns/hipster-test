import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { CandleStickGranularity } from './candle-stick-granularity.model';
import { CandleStickGranularityService } from './candle-stick-granularity.service';

@Component({
    selector: 'jhi-candle-stick-granularity-detail',
    templateUrl: './candle-stick-granularity-detail.component.html'
})
export class CandleStickGranularityDetailComponent implements OnInit, OnDestroy {

    candleStickGranularity: CandleStickGranularity;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private candleStickGranularityService: CandleStickGranularityService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCandleStickGranularities();
    }

    load(id) {
        this.candleStickGranularityService.find(id).subscribe((candleStickGranularity) => {
            this.candleStickGranularity = candleStickGranularity;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCandleStickGranularities() {
        this.eventSubscriber = this.eventManager.subscribe(
            'candleStickGranularityListModification',
            (response) => this.load(this.candleStickGranularity.id)
        );
    }
}
