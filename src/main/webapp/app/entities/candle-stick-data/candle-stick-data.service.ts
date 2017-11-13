import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { CandleStickData } from './candle-stick-data.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class CandleStickDataService {

    private resourceUrl = SERVER_API_URL + 'api/candle-stick-data';

    constructor(private http: Http) { }

    create(candleStickData: CandleStickData): Observable<CandleStickData> {
        const copy = this.convert(candleStickData);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(candleStickData: CandleStickData): Observable<CandleStickData> {
        const copy = this.convert(candleStickData);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<CandleStickData> {
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
     * Convert a returned JSON object to CandleStickData.
     */
    private convertItemFromServer(json: any): CandleStickData {
        const entity: CandleStickData = Object.assign(new CandleStickData(), json);
        return entity;
    }

    /**
     * Convert a CandleStickData to a JSON which can be sent to the server.
     */
    private convert(candleStickData: CandleStickData): CandleStickData {
        const copy: CandleStickData = Object.assign({}, candleStickData);
        return copy;
    }
}
