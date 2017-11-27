import { BaseEntity } from './../../shared';

export class CandleStick implements BaseEntity {
    constructor(
        public id?: number,
        public time?: any,
        public volume?: number,
        public complete?: boolean,
        public mid?: BaseEntity,
        public granularity?: BaseEntity,
        public instrument?: BaseEntity,
    ) {
        this.complete = false;
    }
}
