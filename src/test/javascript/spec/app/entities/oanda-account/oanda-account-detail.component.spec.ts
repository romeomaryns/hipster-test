/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { HipsterTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { OandaAccountDetailComponent } from '../../../../../../main/webapp/app/entities/oanda-account/oanda-account-detail.component';
import { OandaAccountService } from '../../../../../../main/webapp/app/entities/oanda-account/oanda-account.service';
import { OandaAccount } from '../../../../../../main/webapp/app/entities/oanda-account/oanda-account.model';

describe('Component Tests', () => {

    describe('OandaAccount Management Detail Component', () => {
        let comp: OandaAccountDetailComponent;
        let fixture: ComponentFixture<OandaAccountDetailComponent>;
        let service: OandaAccountService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HipsterTestModule],
                declarations: [OandaAccountDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    OandaAccountService,
                    JhiEventManager
                ]
            }).overrideTemplate(OandaAccountDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OandaAccountDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OandaAccountService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new OandaAccount(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.oandaAccount).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
