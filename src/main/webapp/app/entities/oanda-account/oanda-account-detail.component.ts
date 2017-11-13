import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { OandaAccount } from './oanda-account.model';
import { OandaAccountService } from './oanda-account.service';

@Component({
    selector: 'jhi-oanda-account-detail',
    templateUrl: './oanda-account-detail.component.html'
})
export class OandaAccountDetailComponent implements OnInit, OnDestroy {

    oandaAccount: OandaAccount;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private oandaAccountService: OandaAccountService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInOandaAccounts();
    }

    load(id) {
        this.oandaAccountService.find(id).subscribe((oandaAccount) => {
            this.oandaAccount = oandaAccount;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInOandaAccounts() {
        this.eventSubscriber = this.eventManager.subscribe(
            'oandaAccountListModification',
            (response) => this.load(this.oandaAccount.id)
        );
    }
}
