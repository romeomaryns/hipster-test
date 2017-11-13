import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { CandleStickComponent } from './candle-stick.component';
import { CandleStickDetailComponent } from './candle-stick-detail.component';
import { CandleStickPopupComponent } from './candle-stick-dialog.component';
import { CandleStickDeletePopupComponent } from './candle-stick-delete-dialog.component';

export const candleStickRoute: Routes = [
    {
        path: 'candle-stick',
        component: CandleStickComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CandleSticks'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'candle-stick/:id',
        component: CandleStickDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CandleSticks'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const candleStickPopupRoute: Routes = [
    {
        path: 'candle-stick-new',
        component: CandleStickPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CandleSticks'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'candle-stick/:id/edit',
        component: CandleStickPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CandleSticks'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'candle-stick/:id/delete',
        component: CandleStickDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CandleSticks'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
