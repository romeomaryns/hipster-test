import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { OandaAccountComponent } from './oanda-account.component';
import { OandaAccountDetailComponent } from './oanda-account-detail.component';
import { OandaAccountPopupComponent } from './oanda-account-dialog.component';
import { OandaAccountDeletePopupComponent } from './oanda-account-delete-dialog.component';

export const oandaAccountRoute: Routes = [
    {
        path: 'oanda-account',
        component: OandaAccountComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OandaAccounts'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'oanda-account/:id',
        component: OandaAccountDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OandaAccounts'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const oandaAccountPopupRoute: Routes = [
    {
        path: 'oanda-account-new',
        component: OandaAccountPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OandaAccounts'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'oanda-account/:id/edit',
        component: OandaAccountPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OandaAccounts'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'oanda-account/:id/delete',
        component: OandaAccountDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OandaAccounts'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
