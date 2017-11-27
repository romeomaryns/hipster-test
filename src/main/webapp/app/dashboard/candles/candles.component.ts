import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Subscription } from 'rxjs/Rx';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';

import { CandlesService } from './candles.service';
import { Stat } from '../../entities/stat/stat.model';
import { Instrument } from '../../entities/instrument/instrument.model';
import { StatService } from '../../entities/stat/stat.service';
import { forEach } from '@angular/router/src/utils/collection';
import { InstrumentService } from '../../entities/instrument/index';
import { CandleStickGranularity } from '../../entities/candle-stick-granularity/candle-stick-granularity.model';
import { CandleStickGranularityService } from '../../entities/candle-stick-granularity/index';

@Component({
    selector: 'jhi-candles',
    templateUrl: './candles.component.html'
})
export class CandlesComponent implements OnInit {
    metrics: any = {};
    cachesStats: any = {};
    servicesStats: any = {};
    granularities: any = {};
    granularityStats: any = {};
    updatingMetrics = true;
    JCACHE_KEY: string;

    predicate: any;
    reverse: true;
    stats: Map<string, Array<Stat>>;
    allStats: Array<Stat>;
    itemsPerPage: number;
    page: any;

    from: any;
    to: any;
    selectedInstrument: Instrument;
    selectedGranularity: CandleStickGranularity;
    instruments: any = {};

    chartEntries: any = {};

    constructor(
        private modalService: NgbModal,
        private candlesService: CandlesService,
        private statService: StatService,
        private instrumentService: InstrumentService,
        private candleStickGranularityService: CandleStickGranularityService) {
            this.predicate = 'id';
            this.reverse = true;
            this.stats = new Map<string, Array<Stat>>();
            this.allStats = new Array<Stat>();
            this.itemsPerPage = 50000;
            this.JCACHE_KEY = 'jcache.statistics';
            this.granularities = this.getGranularities();
            this.chartEntries = new Array<StockEntry>();
       // console.log(this.granularities);
    }

    ngOnInit() {
        this.refresh();
    }

    generateChart() {
        this.chartEntries = this.candlesService.fetchChartEntries(this.from, this.to, this.selectedInstrument, this.selectedGranularity);
    }

    refresh() {
        this.stats.clear();
        this.allStats = new Array<Stat>();
        this.loadAll();
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
    //    console.log(this.selectedInstrument.displayName);
    //    console.log(this.selectedGranularity);
    }
    reduceMap(stats): Array<any> {
        console.log(stats.size);
        const array: StatEntry[] = new Array();
        const keys: string[] = new Array();
        const statsArray: Array<Stat>[] = new Array();
        const tempArr = stats;
        // console.log((<Map<String, Array<Stat>>>tempArr));
        (<Map<String, Array<Stat>>>tempArr).forEach((value, key) => keys.push(key.valueOf()));
         (<Map<String, Array<Stat>>>tempArr).forEach((value, key) => statsArray.push(value));
         for (const key of keys) {
            console.log(key);
            let code = '';
            let count = 0;
            let timespan = '';
            let first = new Date();
            let last = new Date();
            let instrument = key;
            for (const statsAr of statsArray) {
                // console.log(statsAr.length);
                for (const stat of statsAr) {
                    if ((<Instrument>stat.instrument).displayName === key) {
                        count += stat.numberOfCandles.valueOf();
                        if (count > 0) {
                            console.log(count);
                            instrument = (<Instrument>stat.instrument).displayName;
                            console.log(instrument);
                            code += (<CandleStickGranularity>stat.granularity).name + ', ';
                            console.log(code);
                            if (null != first && stat.first < first.getDate()) {
                                first = stat.first;
                            }
                            if (null != last && stat.last > last.getDate()) {
                                last = stat.last;
                            }
                            timespan = first + ' - ' + last;
                            console.log(timespan);
                        }
                    }
                }
             }
             if (count >= 0) {
                array.push(new StatEntry(new Date(), code, count, timespan, instrument));
             }
         }
         array.sort();
       return array;
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

    getGranularityStats(): Map<string, Array<Stat>> {
        //  this.stats.clear();
        //  this.loadAll();
          return this.stats;
      }

      loadAll() {
          this.statService.query({
              page: this.page,
              size: this.itemsPerPage,
              sort: this.sort()
          }).subscribe(
              (res: ResponseWrapper) => this.onSuccess(res.json, res.headers),
              (res: ResponseWrapper) => this.onError(res.json)
          );
          this.instrumentService.query({
            page: this.page,
            size: this.itemsPerPage,
            sort: this.sort()
        }).subscribe(
            (res: ResponseWrapper) => this.onSuccessInstrument(res.json, res.headers),
            (res: ResponseWrapper) => this.onError(res.json)
        );
        this.candleStickGranularityService.query({
            page: this.page,
            size: this.itemsPerPage,
            sort: this.sort()
        }).subscribe(
            (res: ResponseWrapper) => this.onSuccessGranularity(res.json, res.headers),
            (res: ResponseWrapper) => this.onError(res.json)
        );

      }

      sort() {
          const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
          if (this.predicate !== 'id') {
              result.push('id');
          }
          return result;
      }

      private onSuccess(data, headers) {
        /*     this.links = this.parseLinks.parse(headers.get('link'));
             this.totalItems = headers.get('X-Total-Count');*/
             for (let i = 0; i < data.length; i++) {
                 const value = data[i];
                 const key = value.instrument.displayName;
                 this.allStats.push(value);
             }
             this.repopulateMap();
             console.log(this.stats);
           //  console.log(this.allStats);
           this.granularityStats = this.reduceMap(this.getGranularityStats());
           console.log(this.granularityStats);
         }

         private onSuccessInstrument(data, headers) {
           this.instruments = data;
               console.log(this.instruments);
             }
             private onSuccessGranularity(data, headers) {
                this.granularities = data;
                    console.log(this.granularities);
                  }

         private repopulateMap() {
             for ( let i = 0; i < this.allStats.length ; i++) {
                const value = this.allStats[i];
                const key = (<Instrument>(this.allStats[i].instrument)).displayName;
             //   console.log(key);
                const tempSet = this.stats.get(key);
                const set = new Array<Stat>();
                set.push(value);
                if (tempSet != null) {
                 for ( let j = 0; j < tempSet.length ; j++) {
                     set.push(tempSet[j]);
                 }
                }
                this.stats.set( key, set);
             }
         }

         private onError(error) {
           //  this.jhiAlertService.error(error.message, null, null);
         }

         getGranularities(): Array<CandleStickGranularity> {
            return this.granularities;
        }
}
export class StatEntry {
            private lastUpdateTime: Date;
            private code: string;
            private count: number;
            private timespan: string;
            private instrument: string;

            constructor(lastUpdateTime: Date,
                 code: string,
                 count: number,
                 timespan: string,
                 instrument: string
            ) {
                this.lastUpdateTime = lastUpdateTime;
                this.code = code;
                this.count = count;
                this.timespan = timespan;
                this.instrument = instrument;
            }
        }

export class StockEntry {
    private time: Date;
    private value: number;

    constructor(time: Date,
                value: number
    ) {
        this.time = time;
        this.value = value;
    }
}
