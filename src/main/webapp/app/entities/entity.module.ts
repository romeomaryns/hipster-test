import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { HipsterOandaAccountModule } from './oanda-account/oanda-account.module';
import { HipsterPositionModule } from './position/position.module';
import { HipsterPositionSideModule } from './position-side/position-side.module';
import { HipsterInstrumentModule } from './instrument/instrument.module';
import { HipsterCandleStickModule } from './candle-stick/candle-stick.module';
import { HipsterCandleStickDataModule } from './candle-stick-data/candle-stick-data.module';
import { HipsterStatModule } from './stat/stat.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        HipsterOandaAccountModule,
        HipsterPositionModule,
        HipsterPositionSideModule,
        HipsterInstrumentModule,
        HipsterCandleStickModule,
        HipsterCandleStickDataModule,
        HipsterStatModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HipsterEntityModule {}
