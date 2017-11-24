import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { StatComponent } from './stat.component';
import { StatDetailComponent } from './stat-detail.component';
import { StatPopupComponent } from './stat-dialog.component';
import { StatDeletePopupComponent } from './stat-delete-dialog.component';

export const statRoute: Routes = [
    {
        path: 'stat',
        component: StatComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Stats'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'stat/:id',
        component: StatDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Stats'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const statPopupRoute: Routes = [
    {
        path: 'stat-new',
        component: StatPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Stats'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'stat/:id/edit',
        component: StatPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Stats'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'stat/:id/delete',
        component: StatDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Stats'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
