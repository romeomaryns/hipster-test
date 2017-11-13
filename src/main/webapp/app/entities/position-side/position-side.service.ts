import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { PositionSide } from './position-side.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class PositionSideService {

    private resourceUrl = SERVER_API_URL + 'api/position-sides';

    constructor(private http: Http) { }

    create(positionSide: PositionSide): Observable<PositionSide> {
        const copy = this.convert(positionSide);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(positionSide: PositionSide): Observable<PositionSide> {
        const copy = this.convert(positionSide);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<PositionSide> {
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
     * Convert a returned JSON object to PositionSide.
     */
    private convertItemFromServer(json: any): PositionSide {
        const entity: PositionSide = Object.assign(new PositionSide(), json);
        return entity;
    }

    /**
     * Convert a PositionSide to a JSON which can be sent to the server.
     */
    private convert(positionSide: PositionSide): PositionSide {
        const copy: PositionSide = Object.assign({}, positionSide);
        return copy;
    }
}
