import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HipsterSharedModule } from '../../shared';
import {
    StatService,
    StatPopupService,
    StatComponent,
    StatDetailComponent,
    StatDialogComponent,
    StatPopupComponent,
    StatDeletePopupComponent,
    StatDeleteDialogComponent,
    statRoute,
    statPopupRoute,
} from './';

const ENTITY_STATES = [
    ...statRoute,
    ...statPopupRoute,
];

@NgModule({
    imports: [
        HipsterSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        StatComponent,
        StatDetailComponent,
        StatDialogComponent,
        StatDeleteDialogComponent,
        StatPopupComponent,
        StatDeletePopupComponent,
    ],
    entryComponents: [
        StatComponent,
        StatDialogComponent,
        StatPopupComponent,
        StatDeleteDialogComponent,
        StatDeletePopupComponent,
    ],
    providers: [
        StatService,
        StatPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HipsterStatModule {}
