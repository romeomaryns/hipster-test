import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { OandaAccount } from './oanda-account.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class OandaAccountService {

    private resourceUrl = SERVER_API_URL + 'api/oanda-accounts';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(oandaAccount: OandaAccount): Observable<OandaAccount> {
        const copy = this.convert(oandaAccount);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(oandaAccount: OandaAccount): Observable<OandaAccount> {
        const copy = this.convert(oandaAccount);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<OandaAccount> {
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
     * Convert a returned JSON object to OandaAccount.
     */
    private convertItemFromServer(json: any): OandaAccount {
        const entity: OandaAccount = Object.assign(new OandaAccount(), json);
        entity.createdTime = this.dateUtils
            .convertDateTimeFromServer(json.createdTime);
        entity.resettabledPLTime = this.dateUtils
            .convertDateTimeFromServer(json.resettabledPLTime);
        entity.marginCallEnterTime = this.dateUtils
            .convertDateTimeFromServer(json.marginCallEnterTime);
        entity.lastMarginCallExtensionTime = this.dateUtils
            .convertDateTimeFromServer(json.lastMarginCallExtensionTime);
        return entity;
    }

    /**
     * Convert a OandaAccount to a JSON which can be sent to the server.
     */
    private convert(oandaAccount: OandaAccount): OandaAccount {
        const copy: OandaAccount = Object.assign({}, oandaAccount);

        copy.createdTime = this.dateUtils.toDate(oandaAccount.createdTime);

        copy.resettabledPLTime = this.dateUtils.toDate(oandaAccount.resettabledPLTime);

        copy.marginCallEnterTime = this.dateUtils.toDate(oandaAccount.marginCallEnterTime);

        copy.lastMarginCallExtensionTime = this.dateUtils.toDate(oandaAccount.lastMarginCallExtensionTime);
        return copy;
    }
}
