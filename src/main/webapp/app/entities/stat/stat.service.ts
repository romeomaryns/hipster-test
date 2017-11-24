import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Stat } from './stat.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class StatService {

    private resourceUrl = SERVER_API_URL + 'api/stats';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(stat: Stat): Observable<Stat> {
        const copy = this.convert(stat);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(stat: Stat): Observable<Stat> {
        const copy = this.convert(stat);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Stat> {
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
     * Convert a returned JSON object to Stat.
     */
    private convertItemFromServer(json: any): Stat {
        const entity: Stat = Object.assign(new Stat(), json);
        entity.lastUpdated = this.dateUtils
            .convertDateTimeFromServer(json.lastUpdated);
        entity.first = this.dateUtils
            .convertDateTimeFromServer(json.first);
        entity.last = this.dateUtils
            .convertDateTimeFromServer(json.last);
        return entity;
    }

    /**
     * Convert a Stat to a JSON which can be sent to the server.
     */
    private convert(stat: Stat): Stat {
        const copy: Stat = Object.assign({}, stat);

        copy.lastUpdated = this.dateUtils.toDate(stat.lastUpdated);

        copy.first = this.dateUtils.toDate(stat.first);

        copy.last = this.dateUtils.toDate(stat.last);
        return copy;
    }
}
