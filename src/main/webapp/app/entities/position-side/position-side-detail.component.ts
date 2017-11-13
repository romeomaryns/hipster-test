import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { PositionSide } from './position-side.model';
import { PositionSideService } from './position-side.service';

@Component({
    selector: 'jhi-position-side-detail',
    templateUrl: './position-side-detail.component.html'
})
export class PositionSideDetailComponent implements OnInit, OnDestroy {

    positionSide: PositionSide;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private positionSideService: PositionSideService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPositionSides();
    }

    load(id) {
        this.positionSideService.find(id).subscribe((positionSide) => {
            this.positionSide = positionSide;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPositionSides() {
        this.eventSubscriber = this.eventManager.subscribe(
            'positionSideListModification',
            (response) => this.load(this.positionSide.id)
        );
    }
}
