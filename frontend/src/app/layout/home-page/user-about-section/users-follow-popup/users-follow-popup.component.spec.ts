import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UsersFollowPopupComponent } from './users-follow-popup.component';

describe('UsersFollowPopupComponent', () => {
    let component: UsersFollowPopupComponent;
    let fixture: ComponentFixture<UsersFollowPopupComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            imports: [UsersFollowPopupComponent],
        }).compileComponents();

        fixture = TestBed.createComponent(UsersFollowPopupComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
