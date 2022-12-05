import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SwRatingComponent } from './sw-rating.component';

describe('SwRatingComponent', () => {
  let component: SwRatingComponent;
  let fixture: ComponentFixture<SwRatingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ SwRatingComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SwRatingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
