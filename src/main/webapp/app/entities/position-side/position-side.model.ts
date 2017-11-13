import { BaseEntity } from './../../shared';

export class PositionSide implements BaseEntity {
    constructor(
        public id?: number,
        public units?: number,
        public averagePrice?: number,
        public pl?: number,
        public unrealizedPL?: number,
        public resettablePL?: number,
    ) {
    }
}
