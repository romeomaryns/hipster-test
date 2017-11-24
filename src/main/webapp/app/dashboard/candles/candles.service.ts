import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Subscription } from 'rxjs/Rx';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';

import { Stat } from '../../entities/stat/stat.model';
import { StatService } from '../../entities/stat/stat.service';
import { CandleStickGranularity } from '../../entities/candle-stick/index';

@Injectable()
export class CandlesService {
    predicate: any;
    reverse: true;
    stats: Set<Stat>;
    itemsPerPage: number;
    page: any;

    constructor(
        private http: Http,
        private statService: StatService) {
            this.predicate = 'id';
            this.reverse = true;
            this.stats = new Set();
            this.itemsPerPage = 5000;
                }

    getMetrics(): Observable<any> {
        return this.http.get('management/metrics').map((res: Response) => res.json());
    }

    getGranularityStats(): any {
        this.stats.clear();
        this.loadAll();
        return this.stats;
    }

    loadAll() {
        this.statService.query({
            page: this.page,
            size: this.itemsPerPage,
            sort: this.sort()
        }).subscribe(
            (res: ResponseWrapper) => this.onSuccess(res.json, res.headers),
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    private onSuccess(data, headers) {
   /*     this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = headers.get('X-Total-Count');*/
        for (let i = 0; i < data.length; i++) {
            this.stats.add(data[i]);
        }
    }

    private onError(error) {
      //  this.jhiAlertService.error(error.message, null, null);
    }

    getGranularities(): Set<String> {
        const granularities = new Set();
        granularities.add('S5');
        granularities.add('S10');
        granularities.add('S15');
        granularities.add('S30');
        granularities.add('M1');
        granularities.add('M2');
        granularities.add('M4');
        granularities.add('M5');
        granularities.add('M10');
        granularities.add('M15');
        granularities.add('M30');
        granularities.add('H1');
        granularities.add('H2');
        granularities.add('H3');
        granularities.add('H4');
        granularities.add('H6');
        granularities.add('H8');
        granularities.add('H12');
        granularities.add('D');
        granularities.add('W');
        granularities.add('M');
        return granularities;
    }

    threadDump(): Observable<any> {
        return this.http.get('management/dump').map((res: Response) => res.json());
    }
}
