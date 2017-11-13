import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { CandleStick } from './candle-stick.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class CandleStickService {

    private resourceUrl = SERVER_API_URL + 'api/candle-sticks';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(candleStick: CandleStick): Observable<CandleStick> {
        const copy = this.convert(candleStick);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(candleStick: CandleStick): Observable<CandleStick> {
        const copy = this.convert(candleStick);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<CandleStick> {
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
     * Convert a returned JSON object to CandleStick.
     */
    private convertItemFromServer(json: any): CandleStick {
        const entity: CandleStick = Object.assign(new CandleStick(), json);
        entity.time = this.dateUtils
            .convertDateTimeFromServer(json.time);
        return entity;
    }

    /**
     * Convert a CandleStick to a JSON which can be sent to the server.
     */
    private convert(candleStick: CandleStick): CandleStick {
        const copy: CandleStick = Object.assign({}, candleStick);

        copy.time = this.dateUtils.toDate(candleStick.time);
        return copy;
    }
}
