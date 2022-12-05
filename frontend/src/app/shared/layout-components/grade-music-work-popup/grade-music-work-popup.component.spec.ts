import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GradeMusicWorkPopupComponent } from './grade-music-work-popup.component';

describe('GradeMusicWorkPopupComponent', () => {
  let component: GradeMusicWorkPopupComponent;
  let fixture: ComponentFixture<GradeMusicWorkPopupComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ GradeMusicWorkPopupComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GradeMusicWorkPopupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
