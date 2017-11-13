import { BaseEntity, User } from './../../shared';

export class OandaAccount implements BaseEntity {
    constructor(
        public id?: number,
        public alias?: string,
        public balance?: number,
        public currency?: string,
        public createdByUserID?: number,
        public createdTime?: any,
        public pl?: number,
        public resettablePL?: number,
        public resettabledPLTime?: any,
        public commission?: number,
        public marginRate?: number,
        public marginCallEnterTime?: any,
        public marginCallExtensionCount?: number,
        public lastMarginCallExtensionTime?: any,
        public openTradeCount?: number,
        public openPositionCount?: number,
        public pendingOrderCount?: number,
        public hedgingEnabled?: boolean,
        public unrealizedPL?: number,
        public nAV?: number,
        public marginUsed?: number,
        public marginAvailable?: number,
        public positionValue?: number,
        public marginCloseoutUnrealizedPL?: number,
        public marginCloseoutNAV?: number,
        public marginCloseoutMarginUsed?: number,
        public marginCloseoutPercent?: number,
        public marginCloseoutPositionValue?: number,
        public withdrawalLimit?: number,
        public marginCallMarginUsed?: number,
        public marginCallPercent?: number,
        public lastTransactionID?: string,
        public positions?: BaseEntity[],
        public user?: User,
    ) {
        this.hedgingEnabled = false;
    }
}
