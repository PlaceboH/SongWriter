import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManageMusicWorkComponent } from './manage-music-work.component';

describe('ManageMusicWorkComponent', () => {
  let component: ManageMusicWorkComponent;
  let fixture: ComponentFixture<ManageMusicWorkComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ ManageMusicWorkComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ManageMusicWorkComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
