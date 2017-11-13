import { BaseEntity } from './../../shared';

export class CandleStickData implements BaseEntity {
    constructor(
        public id?: number,
        public o?: number,
        public h?: number,
        public l?: number,
        public c?: number,
    ) {
    }
}
