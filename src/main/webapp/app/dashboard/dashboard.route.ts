import { Routes } from '@angular/router';

import { candlesRoute,
        instrumentsRoute } from './';

const DASHBOARD_ROUTES = [
    candlesRoute,
    instrumentsRoute
];

export const dashboardState: Routes = [{
    path: '',
    children: DASHBOARD_ROUTES
}];
