import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { CandleStickGranularity } from './candle-stick-granularity.model';
import { CandleStickGranularityService } from './candle-stick-granularity.service';

@Injectable()
export class CandleStickGranularityPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private candleStickGranularityService: CandleStickGranularityService

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
                this.candleStickGranularityService.find(id).subscribe((candleStickGranularity) => {
                    this.ngbModalRef = this.candleStickGranularityModalRef(component, candleStickGranularity);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.candleStickGranularityModalRef(component, new CandleStickGranularity());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    candleStickGranularityModalRef(component: Component, candleStickGranularity: CandleStickGranularity): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.candleStickGranularity = candleStickGranularity;
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
