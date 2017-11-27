import { BaseEntity } from './../../shared';

export class Stat implements BaseEntity {
    constructor(
        public id?: number,
        public lastUpdated?: any,
        public numberOfCandles?: number,
        public first?: any,
        public last?: any,
        public instrument?: BaseEntity,
        public granularity?: BaseEntity,
    ) {
    }
}
