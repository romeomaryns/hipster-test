import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { Stat } from './stat.model';
import { StatService } from './stat.service';

@Injectable()
export class StatPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private statService: StatService

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
                this.statService.find(id).subscribe((stat) => {
                    stat.lastUpdated = this.datePipe
                        .transform(stat.lastUpdated, 'yyyy-MM-ddTHH:mm:ss');
                    stat.first = this.datePipe
                        .transform(stat.first, 'yyyy-MM-ddTHH:mm:ss');
                    stat.last = this.datePipe
                        .transform(stat.last, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.statModalRef(component, stat);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.statModalRef(component, new Stat());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    statModalRef(component: Component, stat: Stat): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.stat = stat;
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
