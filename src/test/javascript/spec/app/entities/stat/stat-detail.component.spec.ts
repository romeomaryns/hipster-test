/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { HipsterTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { StatDetailComponent } from '../../../../../../main/webapp/app/entities/stat/stat-detail.component';
import { StatService } from '../../../../../../main/webapp/app/entities/stat/stat.service';
import { Stat } from '../../../../../../main/webapp/app/entities/stat/stat.model';

describe('Component Tests', () => {

    describe('Stat Management Detail Component', () => {
        let comp: StatDetailComponent;
        let fixture: ComponentFixture<StatDetailComponent>;
        let service: StatService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HipsterTestModule],
                declarations: [StatDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    StatService,
                    JhiEventManager
                ]
            }).overrideTemplate(StatDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(StatDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StatService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Stat(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.stat).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
