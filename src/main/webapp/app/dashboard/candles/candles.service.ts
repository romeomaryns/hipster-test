import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { Subscription } from 'rxjs/Rx';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { StockEntry } from '../index';
import { CandleStickDataService } from '../../entities/candle-stick-data/index';
import { CandleStickData } from '../../entities/candle-stick-data/candle-stick-data.model';
import { Instrument } from '../../entities/instrument/index';
import { CandleStickGranularity } from '../../entities/candle-stick-granularity/index';

@Injectable()
export class CandlesService {

    constructor(
        private http: Http) {
                }

    getMetrics(): Observable<any> {
        return this.http.get('management/metrics').map((res: Response) => res.json());
    }

    threadDump(): Observable<any> {
        return this.http.get('management/dump').map((res: Response) => res.json());
    }

    fetchChartEntries(from: any, to: any , instrument: Instrument, granularity: CandleStickGranularity): Array<StockEntry> {
        console.log('fetchChartEntries : ' + from.year + '-' + from.month + '-' + from.day);
        console.log(to.year + '-' + to.month + '-' + to.day + ' , ' + instrument.name + ' , ' + granularity.name);
        const fromString: string = from.year + '-' + from.month + '-' + from.day + 'T00:00:00Z';
        const toString: string = to.year + '-' + to.month + '-' + to.day + 'T23:59:59Z'
        this.http.get(SERVER_API_URL + `api/chart/generate${fromString}&${toString}&${instrument.name}&${granularity.name}`).map((res: Response) => {
            const jsonResponse = res.json();
            console.log(res.json());
            const candles = <Array<CandleStickData>> this.convertItemFromServer(jsonResponse);
            console.log(candles);
            for ( const candle of candles){
                console.log(candle);
            }
        });
        return new Array<StockEntry>();
    }

    /**
     * Convert a returned JSON object to CandleStickData.
     */
    private convertItemFromServer(json: any): CandleStickData {
        const entity: CandleStickData = Object.assign(new CandleStickData(), json);
        return entity;
    }

}
