import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MusicWorkDetailsPageComponent } from './music-work-details-page.component';

describe('MusicWorkDetailsPageComponent', () => {
    let component: MusicWorkDetailsPageComponent;
    let fixture: ComponentFixture<MusicWorkDetailsPageComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            imports: [MusicWorkDetailsPageComponent],
        }).compileComponents();

        fixture = TestBed.createComponent(MusicWorkDetailsPageComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
