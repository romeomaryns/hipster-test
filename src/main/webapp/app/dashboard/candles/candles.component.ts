import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { CandlesService } from './candles.service';
import { CandleStickGranularity } from '../../entities/candle-stick/index';

@Component({
    selector: 'jhi-candles',
    templateUrl: './candles.component.html'
})
export class CandlesComponent implements OnInit {
    metrics: any = {};
    cachesStats: any = {};
    servicesStats: any = {};
    granularities: Set<any>;
    granularityStats: any = {};
    updatingMetrics = true;
    JCACHE_KEY: string;

    constructor(
        private modalService: NgbModal,
        private candlesService: CandlesService
    ) {
        this.JCACHE_KEY = 'jcache.statistics';
      //  console.log(this.granularities);
    }

    ngOnInit() {
        this.refresh();
    }

    refresh() {
        this.updatingMetrics = true;
        this.candlesService.getMetrics().subscribe((metrics) => {
            this.metrics = metrics;
            this.updatingMetrics = false;
            this.servicesStats = {};
            this.cachesStats = {};
            Object.keys(metrics.timers).forEach((key) => {
                const value = metrics.timers[key];
                if (key.indexOf('web.rest') !== -1 || key.indexOf('service') !== -1) {
                    this.servicesStats[key] = value;
                }
            });
            Object.keys(metrics.gauges).forEach((key) => {
                if (key.indexOf('jcache.statistics') !== -1) {
                    const value = metrics.gauges[key].value;
                    // remove gets or puts
                    const index = key.lastIndexOf('.');
                    const newKey = key.substr(0, index);

                    // Keep the name of the domain
                    this.cachesStats[newKey] = {
                        'name': this.JCACHE_KEY.length,
                        'value': value
                    };
                }
            });
        });
        this.granularities = this.candlesService.getGranularities();
        this.granularityStats = this.candlesService.getGranularityStats();
        console.log(this.granularityStats);
    }

    refreshThreadDumpData() {
        this.candlesService.threadDump().subscribe((data) => {
            const modalRef  = this.modalService.open(CandlesComponent, { size: 'lg'});
            modalRef.componentInstance.threadDump = data;
            modalRef.result.then((result) => {
                // Left blank intentionally, nothing to do here
            }, (reason) => {
                // Left blank intentionally, nothing to do here
            });
        });
    }

    filterNaN(input) {
        if (isNaN(input)) {
            return 0;
        }
        return input;
    }

}
