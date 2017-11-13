import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { PositionSideComponent } from './position-side.component';
import { PositionSideDetailComponent } from './position-side-detail.component';
import { PositionSidePopupComponent } from './position-side-dialog.component';
import { PositionSideDeletePopupComponent } from './position-side-delete-dialog.component';

export const positionSideRoute: Routes = [
    {
        path: 'position-side',
        component: PositionSideComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PositionSides'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'position-side/:id',
        component: PositionSideDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PositionSides'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const positionSidePopupRoute: Routes = [
    {
        path: 'position-side-new',
        component: PositionSidePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PositionSides'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'position-side/:id/edit',
        component: PositionSidePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PositionSides'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'position-side/:id/delete',
        component: PositionSideDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PositionSides'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
