import { Route } from '@angular/router';

import { CandlesComponent } from './candles.component';

export const candlesRoute: Route = {
    path: 'candles',
    component: CandlesComponent,
    data: {
        pageTitle: 'Candles'
    }
};
