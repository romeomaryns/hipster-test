import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HipsterSharedModule } from '../../shared';
import {
    CandleStickGranularityService,
    CandleStickGranularityPopupService,
    CandleStickGranularityComponent,
    CandleStickGranularityDetailComponent,
    CandleStickGranularityDialogComponent,
    CandleStickGranularityPopupComponent,
    CandleStickGranularityDeletePopupComponent,
    CandleStickGranularityDeleteDialogComponent,
    candleStickGranularityRoute,
    candleStickGranularityPopupRoute,
} from './';

const ENTITY_STATES = [
    ...candleStickGranularityRoute,
    ...candleStickGranularityPopupRoute,
];

@NgModule({
    imports: [
        HipsterSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CandleStickGranularityComponent,
        CandleStickGranularityDetailComponent,
        CandleStickGranularityDialogComponent,
        CandleStickGranularityDeleteDialogComponent,
        CandleStickGranularityPopupComponent,
        CandleStickGranularityDeletePopupComponent,
    ],
    entryComponents: [
        CandleStickGranularityComponent,
        CandleStickGranularityDialogComponent,
        CandleStickGranularityPopupComponent,
        CandleStickGranularityDeleteDialogComponent,
        CandleStickGranularityDeletePopupComponent,
    ],
    providers: [
        CandleStickGranularityService,
        CandleStickGranularityPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HipsterCandleStickGranularityModule {}
