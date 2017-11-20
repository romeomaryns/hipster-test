import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HipsterSharedModule } from '../shared';

import {
    CandlesComponent,
    CandlesService,
    InstrumentsComponent,
    InstrumentsService,
    dashboardState
} from './';
@NgModule({
    imports: [
        HipsterSharedModule,
        RouterModule.forRoot(dashboardState, { useHash: true, enableTracing : true })
    ],
    declarations: [
        CandlesComponent,
        InstrumentsComponent
    ],
    providers: [
        CandlesService,
        InstrumentsService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HipsterDashboardModule {}
