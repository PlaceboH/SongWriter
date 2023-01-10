 import { autoSpy } from '../../../../../auto-spy';
import { EMPTY } from 'rxjs';
import { FormBuilder } from '@angular/forms';
import { GradeMusicWorkPopupComponent } from './grade-music-work-popup.component';
import { MarkService } from '../../services/mark.service';
import { MatDialogRef } from '@angular/material/dialog';
import { NotificationService } from '../../services/notification.service';

fdescribe('GradeMusicWorkPopupComponent', () => {
    it('when ngOnInit is called it should', () => {
        // arrange
        const { build } = setup().default();
        const g = build();
        // act
        g.ngOnInit();
        // assert
        // expect(g).toEqual
    });
    it('when createGradeForm is called it should', () => {
        // arrange
        const { build } = setup().default();
        const g = build();
        // act
        g.createGradeForm();
        // assert
        // expect(g).toEqual
    });
    it('when onRatingChanged is called it should', () => {
        // arrange
        const { build } = setup().default();
        const g = build();
        // act
       // g.onRatingChanged();
        // assert
        // expect(g).toEqual
    });
    it('when gradeMusicWork is called it should', () => {
        // arrange
        const { build } = setup().default();
        const g = build();
        // act
        g.gradeMusicWork();
        // assert
        // expect(g).toEqual
    });
    it('when closeDialog is called it should', () => {
        // arrange
        const { build } = setup().default();
        const g = build();
        // act
        g.closeDialog();
        // assert
        // expect(g).toEqual
    });

});

function setup() {
    const dialogRef = autoSpy(MatDialogRef<GradeMusicWorkPopupComponent>);
    const markService = autoSpy(MarkService);
    const fb = autoSpy(FormBuilder);
    const notificationService = autoSpy(NotificationService);
    const data = autoSpy(MarkService);
    const builder = {
        dialogRef,
        markService,
        fb,
        notificationService,
        data,
        default() {
            return builder;
        },
        build() {
            return new GradeMusicWorkPopupComponent(dialogRef, markService, fb, notificationService, data);
        }
    };

    return builder;
}
