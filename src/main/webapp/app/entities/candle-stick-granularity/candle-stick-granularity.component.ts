import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { CandleStickGranularity } from './candle-stick-granularity.model';
import { CandleStickGranularityService } from './candle-stick-granularity.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-candle-stick-granularity',
    templateUrl: './candle-stick-granularity.component.html'
})
export class CandleStickGranularityComponent implements OnInit, OnDestroy {

    candleStickGranularities: CandleStickGranularity[];
    currentAccount: any;
    eventSubscriber: Subscription;
    itemsPerPage: number;
    links: any;
    page: any;
    predicate: any;
    queryCount: any;
    reverse: any;
    totalItems: number;

    constructor(
        private candleStickGranularityService: CandleStickGranularityService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private parseLinks: JhiParseLinks,
        private principal: Principal
    ) {
        this.candleStickGranularities = [];
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.page = 0;
        this.links = {
            last: 0
        };
        this.predicate = 'id';
        this.reverse = true;
    }

    loadAll() {
        this.candleStickGranularityService.query({
            page: this.page,
            size: this.itemsPerPage,
            sort: this.sort()
        }).subscribe(
            (res: ResponseWrapper) => this.onSuccess(res.json, res.headers),
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    reset() {
        this.page = 0;
        this.candleStickGranularities = [];
        this.loadAll();
    }

    loadPage(page) {
        this.page = page;
        this.loadAll();
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInCandleStickGranularities();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: CandleStickGranularity) {
        return item.id;
    }
    registerChangeInCandleStickGranularities() {
        this.eventSubscriber = this.eventManager.subscribe('candleStickGranularityListModification', (response) => this.reset());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    private onSuccess(data, headers) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = headers.get('X-Total-Count');
        for (let i = 0; i < data.length; i++) {
            this.candleStickGranularities.push(data[i]);
        }
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
