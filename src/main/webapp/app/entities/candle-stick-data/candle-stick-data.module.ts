import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HipsterSharedModule } from '../../shared';
import {
    CandleStickDataService,
    CandleStickDataPopupService,
    CandleStickDataComponent,
    CandleStickDataDetailComponent,
    CandleStickDataDialogComponent,
    CandleStickDataPopupComponent,
    CandleStickDataDeletePopupComponent,
    CandleStickDataDeleteDialogComponent,
    candleStickDataRoute,
    candleStickDataPopupRoute,
} from './';

const ENTITY_STATES = [
    ...candleStickDataRoute,
    ...candleStickDataPopupRoute,
];

@NgModule({
    imports: [
        HipsterSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CandleStickDataComponent,
        CandleStickDataDetailComponent,
        CandleStickDataDialogComponent,
        CandleStickDataDeleteDialogComponent,
        CandleStickDataPopupComponent,
        CandleStickDataDeletePopupComponent,
    ],
    entryComponents: [
        CandleStickDataComponent,
        CandleStickDataDialogComponent,
        CandleStickDataPopupComponent,
        CandleStickDataDeleteDialogComponent,
        CandleStickDataDeletePopupComponent,
    ],
    providers: [
        CandleStickDataService,
        CandleStickDataPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HipsterCandleStickDataModule {}
