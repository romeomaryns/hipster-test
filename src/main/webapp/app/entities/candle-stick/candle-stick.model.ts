import { BaseEntity } from './../../shared';

export class CandleStick implements BaseEntity {
    constructor(
        public id?: number,
        public time?: any,
        public volume?: number,
        public complete?: boolean,
        public bid?: BaseEntity,
        public ask?: BaseEntity,
    ) {
        this.complete = false;
    }
}
