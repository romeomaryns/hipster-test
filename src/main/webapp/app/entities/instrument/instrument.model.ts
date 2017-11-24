import { BaseEntity } from './../../shared';

export class Instrument implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public type?: string,
        public displayName?: string,
        public pipLocation?: number,
        public displayPrecision?: number,
        public tradeUnitsPrecision?: number,
        public minimumTradeSize?: number,
        public maximumTrailingStopDistance?: number,
        public minimumTrailingStopDistance?: number,
        public maximumPositionSize?: number,
        public maximumOrderUnits?: number,
        public marginRate?: number,
        public commission?: number,
        public stats?: BaseEntity[],
    ) {
    }
}
