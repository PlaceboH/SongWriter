import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MarkService } from '../../services/mark.service';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MaterialModule } from 'src/app/material.module';
import { MusicWork } from '../../models/MusicWork';
import { NotificationService } from '../../services/notification.service';
import { SharedModule } from '../../shared.module';
import { SwRatingComponent } from '../../custom-components/sw-rating/sw-rating.component';

@Component({
    standalone: true,
    selector: 'app-grade-music-work-popup',
    templateUrl: './grade-music-work-popup.component.html',
    styleUrls: ['./grade-music-work-popup.component.scss'],
    imports: [SharedModule, MaterialModule, SwRatingComponent],
})
export class GradeMusicWorkPopupComponent implements OnInit {
    musicWork: MusicWork;
    form: FormGroup;

    constructor(
        private dialogRef: MatDialogRef<GradeMusicWorkPopupComponent>,
        private markService: MarkService,
        private fb: FormBuilder,
        private notificationService: NotificationService,
        @Inject(MAT_DIALOG_DATA) public data: any
    ) {}

    ngOnInit(): void {
        this.musicWork = this.data.musicWork;
        this.form = this.createGradeForm();
    }

    createGradeForm(): FormGroup {
        return this.fb.group({
            stars: ['', Validators.compose([Validators.required])],
            comment: ['', Validators.compose([Validators.required])],
        });
    }

    onRatingChanged(rating: number) {
        this.form.patchValue({ stars: rating });
    }

    gradeMusicWork(): void {
        if (!this.form.valid || this.form.value.stars > 5) {
            return null;
        }

        const message = this.form.value.comment;
        const stars = this.form.value.stars;

        this.markService
            .addMarkToMusicWork(this.musicWork.id, message, stars)
            .subscribe((data) => {
                this.musicWork.marks.push(data);
                this.notificationService.showSuccessSnackBar(
                    'Successfully graded music work'
                );
                this.dialogRef.close();
            });
    }

    closeDialog() {
        this.dialogRef.close();
    }
}
