import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HipsterSharedModule } from '../../shared';
import {
    InstrumentService,
    InstrumentPopupService,
    InstrumentComponent,
    InstrumentDetailComponent,
    InstrumentDialogComponent,
    InstrumentPopupComponent,
    InstrumentDeletePopupComponent,
    InstrumentDeleteDialogComponent,
    instrumentRoute,
    instrumentPopupRoute,
} from './';

const ENTITY_STATES = [
    ...instrumentRoute,
    ...instrumentPopupRoute,
];

@NgModule({
    imports: [
        HipsterSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        InstrumentComponent,
        InstrumentDetailComponent,
        InstrumentDialogComponent,
        InstrumentDeleteDialogComponent,
        InstrumentPopupComponent,
        InstrumentDeletePopupComponent,
    ],
    entryComponents: [
        InstrumentComponent,
        InstrumentDialogComponent,
        InstrumentPopupComponent,
        InstrumentDeleteDialogComponent,
        InstrumentDeletePopupComponent,
    ],
    providers: [
        InstrumentService,
        InstrumentPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HipsterInstrumentModule {}
