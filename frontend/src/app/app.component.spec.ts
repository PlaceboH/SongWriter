import { DebugElement } from '@angular/core';
import { async, ComponentFixture, fakeAsync, flush, TestBed, tick, waitForAsync } from '@angular/core/testing';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';
import { TranslateService } from '@ngx-translate/core';
import { AppComponent } from './app.component';

describe('AppComponent', () => {

    let component: AppComponent;
    let fixture: ComponentFixture<AppComponent>;
    let el: DebugElement;
    let translateService: TranslateService;

    beforeEach(waitForAsync(() => {

        const translateServiceSpy = jasmine
            .createSpyObj('TranslateService', ['addLangs', 'setDefaultLang']);

        TestBed.configureTestingModule({
            imports: [RouterTestingModule, AppComponent, NoopAnimationsModule],
            providers: [{provide: TranslateService, useValue: translateServiceSpy}]
        }).compileComponents()
            .then(() => {
                fixture = TestBed.createComponent(AppComponent);
                component = fixture.componentInstance;
                el = fixture.debugElement;
                translateService = TestBed.get(TranslateService);
            });
    }));

    it('should create the app', () => {
        expect(component).toBeTruthy();
    });

});
