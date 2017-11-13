import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HipsterSharedModule } from '../../shared';
import {
    CandleStickService,
    CandleStickPopupService,
    CandleStickComponent,
    CandleStickDetailComponent,
    CandleStickDialogComponent,
    CandleStickPopupComponent,
    CandleStickDeletePopupComponent,
    CandleStickDeleteDialogComponent,
    candleStickRoute,
    candleStickPopupRoute,
} from './';

const ENTITY_STATES = [
    ...candleStickRoute,
    ...candleStickPopupRoute,
];

@NgModule({
    imports: [
        HipsterSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CandleStickComponent,
        CandleStickDetailComponent,
        CandleStickDialogComponent,
        CandleStickDeleteDialogComponent,
        CandleStickPopupComponent,
        CandleStickDeletePopupComponent,
    ],
    entryComponents: [
        CandleStickComponent,
        CandleStickDialogComponent,
        CandleStickPopupComponent,
        CandleStickDeleteDialogComponent,
        CandleStickDeletePopupComponent,
    ],
    providers: [
        CandleStickService,
        CandleStickPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HipsterCandleStickModule {}
