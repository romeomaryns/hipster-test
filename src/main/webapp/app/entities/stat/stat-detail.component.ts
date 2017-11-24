import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Stat } from './stat.model';
import { StatService } from './stat.service';

@Component({
    selector: 'jhi-stat-detail',
    templateUrl: './stat-detail.component.html'
})
export class StatDetailComponent implements OnInit, OnDestroy {

    stat: Stat;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private statService: StatService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInStats();
    }

    load(id) {
        this.statService.find(id).subscribe((stat) => {
            this.stat = stat;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInStats() {
        this.eventSubscriber = this.eventManager.subscribe(
            'statListModification',
            (response) => this.load(this.stat.id)
        );
    }
}
