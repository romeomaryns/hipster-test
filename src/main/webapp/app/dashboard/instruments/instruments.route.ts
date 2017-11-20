import { Route } from '@angular/router';

import { InstrumentsComponent } from './instruments.component';

export const instrumentsRoute: Route = {
    path: 'instruments',
    component: InstrumentsComponent,
    data: {
        pageTitle: 'Instruments'
    }
};
