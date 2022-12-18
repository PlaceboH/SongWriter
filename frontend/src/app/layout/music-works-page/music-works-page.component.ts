import { Component, OnInit } from '@angular/core';
import { SharedModule } from 'src/app/shared/shared.module';
import { MaterialModule } from 'src/app/material.module';
import { MusicWork } from 'src/app/shared/models/MusicWork';
import { MusicWorkService } from 'src/app/shared/services/music-work.service';
import { ImageUploadService } from 'src/app/shared/services/image.service';
import { MarkService } from 'src/app/shared/services/mark.service';
import { Router } from '@angular/router';
import { NotificationService } from 'src/app/shared/services/notification.service';
import { UserService } from 'src/app/shared/services/user.service';
import { SearchMusicWorkPipe } from 'src/app/shared/custom-pipes/search-music-work.pipe';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { GradeMusicWorkPopupComponent } from 'src/app/shared/layout-components/grade-music-work-popup/grade-music-work-popup.component';

@Component({
    selector: 'app-music-works-page',
    standalone: true,
    imports: [SharedModule, MaterialModule, SearchMusicWorkPipe],
    templateUrl: './music-works-page.component.html',
    styleUrls: ['./music-works-page.component.scss'],
})
export class MusicWorksPageComponent implements OnInit {
    searchValue: string;
    isUserMusicWorksLoaded = false;
    musicWorks!: MusicWork[];
    constructor(
        private musicWorkService: MusicWorkService,
        private imageService: ImageUploadService,
        private markService: MarkService,
        private userService: UserService,
        private dialog: MatDialog,
        private router: Router,
        private notificationService: NotificationService
    ) {}

    ngOnInit(): void {
        this.musicWorkService.getAllMusicWorks().subscribe((data) => {
            console.log('Music Works: ', data);
            this.musicWorks = data;
            this.getUsersToMusicWorks(this.musicWorks);
            this.getImagesToMusicWorks(this.musicWorks);
            this.getMarksToMusicWork(this.musicWorks);
            this.isUserMusicWorksLoaded = true;
        });
    }

    getImagesToMusicWorks(musicWorks: MusicWork[]): void {
        musicWorks.forEach((m) => {
            this.imageService
                .getImageToMusicWork(m.id as number)
                .subscribe((data) => {
                    m.image = data ? data.imageBytes : null;
                });
        });
    }

    getMarksToMusicWork(musicWorks: MusicWork[]): void {
        musicWorks.forEach((m) => {
            this.markService
                .getMarksToMusicWork(m.id as number)
                .subscribe((data) => {
                    m.marks = data;
                });
        });
    }

    getUsersToMusicWorks(musciWorks: MusicWork[]): void {
        musciWorks.map((m) => {
            this.userService.getUserByUsername(m.username).subscribe((user) => {
                m.user = user;
            });
        });
    }

    openGradeDialog(musicWork: MusicWork): void {
        const dialogGradeMusicWork = new MatDialogConfig();
        dialogGradeMusicWork.width = '400px';
        dialogGradeMusicWork.data = {
            musicWork: musicWork,
        };
        this.dialog.open(GradeMusicWorkPopupComponent, dialogGradeMusicWork);
    }

    formatImage(img: any): any {
        if (img == null) {
            return null;
        }
        return 'data:image/jpeg;base64,' + img;
    }
}
