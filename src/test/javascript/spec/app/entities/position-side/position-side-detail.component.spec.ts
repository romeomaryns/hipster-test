/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { HipsterTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PositionSideDetailComponent } from '../../../../../../main/webapp/app/entities/position-side/position-side-detail.component';
import { PositionSideService } from '../../../../../../main/webapp/app/entities/position-side/position-side.service';
import { PositionSide } from '../../../../../../main/webapp/app/entities/position-side/position-side.model';

describe('Component Tests', () => {

    describe('PositionSide Management Detail Component', () => {
        let comp: PositionSideDetailComponent;
        let fixture: ComponentFixture<PositionSideDetailComponent>;
        let service: PositionSideService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HipsterTestModule],
                declarations: [PositionSideDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PositionSideService,
                    JhiEventManager
                ]
            }).overrideTemplate(PositionSideDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PositionSideDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PositionSideService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new PositionSide(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.positionSide).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
