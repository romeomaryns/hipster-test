import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Stat } from './stat.model';
import { StatPopupService } from './stat-popup.service';
import { StatService } from './stat.service';

@Component({
    selector: 'jhi-stat-delete-dialog',
    templateUrl: './stat-delete-dialog.component.html'
})
export class StatDeleteDialogComponent {

    stat: Stat;

    constructor(
        private statService: StatService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.statService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'statListModification',
                content: 'Deleted an stat'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-stat-delete-popup',
    template: ''
})
export class StatDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private statPopupService: StatPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.statPopupService
                .open(StatDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
