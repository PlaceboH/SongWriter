import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MusicWorksPageComponent } from './music-works-page.component';

describe('MusicWorksPageComponent', () => {
    let component: MusicWorksPageComponent;
    let fixture: ComponentFixture<MusicWorksPageComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            imports: [MusicWorksPageComponent],
        }).compileComponents();

        fixture = TestBed.createComponent(MusicWorksPageComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
