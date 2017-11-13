import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HipsterSharedModule } from '../../shared';
import {
    PositionSideService,
    PositionSidePopupService,
    PositionSideComponent,
    PositionSideDetailComponent,
    PositionSideDialogComponent,
    PositionSidePopupComponent,
    PositionSideDeletePopupComponent,
    PositionSideDeleteDialogComponent,
    positionSideRoute,
    positionSidePopupRoute,
} from './';

const ENTITY_STATES = [
    ...positionSideRoute,
    ...positionSidePopupRoute,
];

@NgModule({
    imports: [
        HipsterSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PositionSideComponent,
        PositionSideDetailComponent,
        PositionSideDialogComponent,
        PositionSideDeleteDialogComponent,
        PositionSidePopupComponent,
        PositionSideDeletePopupComponent,
    ],
    entryComponents: [
        PositionSideComponent,
        PositionSideDialogComponent,
        PositionSidePopupComponent,
        PositionSideDeleteDialogComponent,
        PositionSideDeletePopupComponent,
    ],
    providers: [
        PositionSideService,
        PositionSidePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HipsterPositionSideModule {}
