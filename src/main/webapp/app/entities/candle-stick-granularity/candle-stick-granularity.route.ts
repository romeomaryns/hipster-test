import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { CandleStickGranularityComponent } from './candle-stick-granularity.component';
import { CandleStickGranularityDetailComponent } from './candle-stick-granularity-detail.component';
import { CandleStickGranularityPopupComponent } from './candle-stick-granularity-dialog.component';
import { CandleStickGranularityDeletePopupComponent } from './candle-stick-granularity-delete-dialog.component';

export const candleStickGranularityRoute: Routes = [
    {
        path: 'candle-stick-granularity',
        component: CandleStickGranularityComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CandleStickGranularities'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'candle-stick-granularity/:id',
        component: CandleStickGranularityDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CandleStickGranularities'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const candleStickGranularityPopupRoute: Routes = [
    {
        path: 'candle-stick-granularity-new',
        component: CandleStickGranularityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CandleStickGranularities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'candle-stick-granularity/:id/edit',
        component: CandleStickGranularityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CandleStickGranularities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'candle-stick-granularity/:id/delete',
        component: CandleStickGranularityDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CandleStickGranularities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
