/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { HipsterTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CandleStickGranularityDetailComponent } from '../../../../../../main/webapp/app/entities/candle-stick-granularity/candle-stick-granularity-detail.component';
import { CandleStickGranularityService } from '../../../../../../main/webapp/app/entities/candle-stick-granularity/candle-stick-granularity.service';
import { CandleStickGranularity } from '../../../../../../main/webapp/app/entities/candle-stick-granularity/candle-stick-granularity.model';

describe('Component Tests', () => {

    describe('CandleStickGranularity Management Detail Component', () => {
        let comp: CandleStickGranularityDetailComponent;
        let fixture: ComponentFixture<CandleStickGranularityDetailComponent>;
        let service: CandleStickGranularityService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HipsterTestModule],
                declarations: [CandleStickGranularityDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CandleStickGranularityService,
                    JhiEventManager
                ]
            }).overrideTemplate(CandleStickGranularityDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CandleStickGranularityDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CandleStickGranularityService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new CandleStickGranularity(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.candleStickGranularity).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
