/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { HipsterTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CandleStickDataDetailComponent } from '../../../../../../main/webapp/app/entities/candle-stick-data/candle-stick-data-detail.component';
import { CandleStickDataService } from '../../../../../../main/webapp/app/entities/candle-stick-data/candle-stick-data.service';
import { CandleStickData } from '../../../../../../main/webapp/app/entities/candle-stick-data/candle-stick-data.model';

describe('Component Tests', () => {

    describe('CandleStickData Management Detail Component', () => {
        let comp: CandleStickDataDetailComponent;
        let fixture: ComponentFixture<CandleStickDataDetailComponent>;
        let service: CandleStickDataService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HipsterTestModule],
                declarations: [CandleStickDataDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CandleStickDataService,
                    JhiEventManager
                ]
            }).overrideTemplate(CandleStickDataDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CandleStickDataDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CandleStickDataService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new CandleStickData(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.candleStickData).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
