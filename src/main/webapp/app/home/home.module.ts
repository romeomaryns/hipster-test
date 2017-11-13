import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HipsterSharedModule } from '../shared';

import { HOME_ROUTE, HomeComponent } from './';

import { ChartModule } from 'angular2-highcharts';

@NgModule({
    imports: [
        HipsterSharedModule,
        ChartModule.forRoot(require('highcharts/highstock')),
        RouterModule.forRoot([ HOME_ROUTE ], { useHash: true })
    ],
    declarations: [
        HomeComponent,
    ],
    entryComponents: [
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HipsterHomeModule {}
