import { BaseEntity } from './../../shared';

export class Position implements BaseEntity {
    constructor(
        public id?: number,
        public instrument?: string,
        public pl?: number,
        public unrealizedPL?: number,
        public resettablePL?: number,
        public commission?: number,
        public oandaAccount?: BaseEntity,
        public longValue?: BaseEntity,
        public shortValue?: BaseEntity,
    ) {
    }
}
