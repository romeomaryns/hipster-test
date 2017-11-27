import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { CandleStickGranularity } from './candle-stick-granularity.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class CandleStickGranularityService {

    private resourceUrl = SERVER_API_URL + 'api/candle-stick-granularities';

    constructor(private http: Http) { }

    create(candleStickGranularity: CandleStickGranularity): Observable<CandleStickGranularity> {
        const copy = this.convert(candleStickGranularity);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(candleStickGranularity: CandleStickGranularity): Observable<CandleStickGranularity> {
        const copy = this.convert(candleStickGranularity);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<CandleStickGranularity> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to CandleStickGranularity.
     */
    private convertItemFromServer(json: any): CandleStickGranularity {
        const entity: CandleStickGranularity = Object.assign(new CandleStickGranularity(), json);
        return entity;
    }

    /**
     * Convert a CandleStickGranularity to a JSON which can be sent to the server.
     */
    private convert(candleStickGranularity: CandleStickGranularity): CandleStickGranularity {
        const copy: CandleStickGranularity = Object.assign({}, candleStickGranularity);
        return copy;
    }
}
