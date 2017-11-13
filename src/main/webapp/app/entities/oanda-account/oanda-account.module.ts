import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HipsterSharedModule } from '../../shared';
import { HipsterAdminModule } from '../../admin/admin.module';
import {
    OandaAccountService,
    OandaAccountPopupService,
    OandaAccountComponent,
    OandaAccountDetailComponent,
    OandaAccountDialogComponent,
    OandaAccountPopupComponent,
    OandaAccountDeletePopupComponent,
    OandaAccountDeleteDialogComponent,
    oandaAccountRoute,
    oandaAccountPopupRoute,
} from './';

const ENTITY_STATES = [
    ...oandaAccountRoute,
    ...oandaAccountPopupRoute,
];

@NgModule({
    imports: [
        HipsterSharedModule,
        HipsterAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        OandaAccountComponent,
        OandaAccountDetailComponent,
        OandaAccountDialogComponent,
        OandaAccountDeleteDialogComponent,
        OandaAccountPopupComponent,
        OandaAccountDeletePopupComponent,
    ],
    entryComponents: [
        OandaAccountComponent,
        OandaAccountDialogComponent,
        OandaAccountPopupComponent,
        OandaAccountDeleteDialogComponent,
        OandaAccountDeletePopupComponent,
    ],
    providers: [
        OandaAccountService,
        OandaAccountPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HipsterOandaAccountModule {}
