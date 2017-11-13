/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { HipsterTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CandleStickDetailComponent } from '../../../../../../main/webapp/app/entities/candle-stick/candle-stick-detail.component';
import { CandleStickService } from '../../../../../../main/webapp/app/entities/candle-stick/candle-stick.service';
import { CandleStick } from '../../../../../../main/webapp/app/entities/candle-stick/candle-stick.model';

describe('Component Tests', () => {

    describe('CandleStick Management Detail Component', () => {
        let comp: CandleStickDetailComponent;
        let fixture: ComponentFixture<CandleStickDetailComponent>;
        let service: CandleStickService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HipsterTestModule],
                declarations: [CandleStickDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CandleStickService,
                    JhiEventManager
                ]
            }).overrideTemplate(CandleStickDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CandleStickDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CandleStickService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new CandleStick(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.candleStick).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
