import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { CandleStickDataComponent } from './candle-stick-data.component';
import { CandleStickDataDetailComponent } from './candle-stick-data-detail.component';
import { CandleStickDataPopupComponent } from './candle-stick-data-dialog.component';
import { CandleStickDataDeletePopupComponent } from './candle-stick-data-delete-dialog.component';

export const candleStickDataRoute: Routes = [
    {
        path: 'candle-stick-data',
        component: CandleStickDataComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CandleStickData'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'candle-stick-data/:id',
        component: CandleStickDataDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CandleStickData'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const candleStickDataPopupRoute: Routes = [
    {
        path: 'candle-stick-data-new',
        component: CandleStickDataPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CandleStickData'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'candle-stick-data/:id/edit',
        component: CandleStickDataPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CandleStickData'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'candle-stick-data/:id/delete',
        component: CandleStickDataDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CandleStickData'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
