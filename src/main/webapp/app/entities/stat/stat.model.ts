import { BaseEntity } from './../../shared';

export const enum CandleStickGranularity {
    'S5',
    'S10',
    'S15',
    'S30',
    'M1',
    'M2',
    'M4',
    'M5',
    'M10',
    'M15',
    'M30',
    'H1',
    'H2',
    'H3',
    'H4',
    'H6',
    'H8',
    'H12',
    'D',
    'W',
    'M'
}

export class Stat implements BaseEntity {
    constructor(
        public id?: number,
        public lastUpdated?: any,
        public granularity?: CandleStickGranularity,
        public numberOfCandles?: number,
        public first?: any,
        public last?: any,
        public instrument?: BaseEntity,
    ) {
    }
}
