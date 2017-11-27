import { BaseEntity } from './../../shared';

export class CandleStickGranularity implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
    ) {
    }
}
