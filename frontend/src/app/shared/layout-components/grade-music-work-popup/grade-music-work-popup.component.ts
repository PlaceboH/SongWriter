import { Component, Inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { NotificationService } from '../../services/notification.service';
import { MusicWork } from '../../models/MusicWork';
import { SharedModule } from '../../shared.module';
import { MaterialModule } from 'src/app/material.module';
import { MarkService } from '../../services/mark.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  standalone: true,
  imports: [SharedModule, MaterialModule],
  selector: 'app-grade-music-work-popup',
  templateUrl: './grade-music-work-popup.component.html',
  styleUrls: ['./grade-music-work-popup.component.scss']
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
  ) { }

  ngOnInit(): void {
    this.musicWork = this.data.musicWork;
    this.form = this.CreateGradeForm();
  }

  CreateGradeForm(): FormGroup {
    return this.fb.group({
      stars: ['', Validators.compose([Validators.required])],
      comment: ['', Validators.compose([Validators.required])]
    });
  }

  gradeMusicWork(): void {
    console.log(this.form.value);

    if (!this.form.valid){
      this.notificationService.showErrorSnackBar('Please set all data');
    }

    const message = this.form.value.comment;
    const stars = this.form.value.stars;

    this.markService.addMarkToMusicWork(this.musicWork.id, message, stars)
      .subscribe(data => {
        this.musicWork.marks.push(data);
        this.notificationService.showSuccessSnackBar('Successfully graded music work');
        this.dialogRef.close();
    });
  }

  closeDialog() {
    this.dialogRef.close();
  }

}
