import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ImageUploadService } from 'src/app/shared/services/image.service';
import { MatDialogRef } from '@angular/material/dialog';
import { MaterialModule } from 'src/app/material.module';
import { NotificationService } from 'src/app/shared/services/notification.service';
import { SharedModule } from 'src/app/shared/shared.module';
import { MusicWork } from 'src/app/shared/models/MusicWork';
import { MusicWorkService } from 'src/app/shared/services/music-work.service';

@Component({
    standalone: true,
    imports: [SharedModule, MaterialModule],
    selector: 'app-create-musci-work',
    templateUrl: './create-musci-work.component.html',
    styleUrls: ['./create-musci-work.component.scss'],
})
export class CreateMusicWorkComponent implements OnInit {
    musicWorkForm!: FormGroup;
    selectedFile!: File;
    isMusciWorkCreated = false;
    createdMusicWork!: MusicWork;
    previewImgURL: any;

    constructor(
        private dialogRef: MatDialogRef<CreateMusicWorkComponent>,
        private musicWorkService: MusicWorkService,
        private imageUploadService: ImageUploadService,
        private notificationService: NotificationService,
        private fb: FormBuilder
    ) {}

    ngOnInit(): void {
        this.musicWorkForm = this.createMusicWorkForm();
    }

    createMusicWorkForm(): FormGroup {
        return this.fb.group({
            title: ['', Validators.compose([Validators.required])],
            description: ['', Validators.compose([Validators.required])],
            caption: ['', Validators.compose([Validators.required])],
        });
    }

    submit(): void {
        this.musicWorkService
            .createMusicWork({
                ...this.musicWorkForm.value,
            })
            .subscribe((data) => {
                this.createdMusicWork = data;
                console.log('Create Music Work: ', data);
                if (this.createdMusicWork.id != null) {
                    this.imageUploadService
                        .uploadImageToMusicWork(
                            this.selectedFile,
                            this.createdMusicWork.id
                        )
                        .subscribe(() => {
                            this.notificationService.showSuccessSnackBar(
                                'Music work created successfully'
                            );
                            this.isMusciWorkCreated = true;
                            this.dialogRef.close();
                        });
                }
            });
    }

    closeDialog() {
        this.dialogRef.close();
    }

    onFileSelected(event: any): void {
        this.selectedFile = event.target.files[0];

        const reader = new FileReader();
        reader.readAsDataURL(this.selectedFile);
        reader.onload = (e) => {
            this.previewImgURL = reader.result;
        };
    }
}
