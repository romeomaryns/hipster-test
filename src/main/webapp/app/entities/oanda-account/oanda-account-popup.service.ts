import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { OandaAccount } from './oanda-account.model';
import { OandaAccountService } from './oanda-account.service';

@Injectable()
export class OandaAccountPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private oandaAccountService: OandaAccountService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.oandaAccountService.find(id).subscribe((oandaAccount) => {
                    oandaAccount.createdTime = this.datePipe
                        .transform(oandaAccount.createdTime, 'yyyy-MM-ddTHH:mm:ss');
                    oandaAccount.resettabledPLTime = this.datePipe
                        .transform(oandaAccount.resettabledPLTime, 'yyyy-MM-ddTHH:mm:ss');
                    oandaAccount.marginCallEnterTime = this.datePipe
                        .transform(oandaAccount.marginCallEnterTime, 'yyyy-MM-ddTHH:mm:ss');
                    oandaAccount.lastMarginCallExtensionTime = this.datePipe
                        .transform(oandaAccount.lastMarginCallExtensionTime, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.oandaAccountModalRef(component, oandaAccount);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.oandaAccountModalRef(component, new OandaAccount());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    oandaAccountModalRef(component: Component, oandaAccount: OandaAccount): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.oandaAccount = oandaAccount;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
