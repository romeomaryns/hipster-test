import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { CandleStick } from './candle-stick.model';
import { CandleStickService } from './candle-stick.service';

@Component({
    selector: 'jhi-candle-stick-detail',
    templateUrl: './candle-stick-detail.component.html'
})
export class CandleStickDetailComponent implements OnInit, OnDestroy {

    candleStick: CandleStick;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private candleStickService: CandleStickService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCandleSticks();
    }

    load(id) {
        this.candleStickService.find(id).subscribe((candleStick) => {
            this.candleStick = candleStick;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCandleSticks() {
        this.eventSubscriber = this.eventManager.subscribe(
            'candleStickListModification',
            (response) => this.load(this.candleStick.id)
        );
    }
}
