import { CreateMusicWorkComponent } from './createMusicWork/create-musci-work.component';
import { ImageUploadService } from 'src/app/shared/services/image.service';
import { MarkService } from 'src/app/shared/services/mark.service';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { MaterialModule } from 'src/app/material.module';
import { MusicWork } from 'src/app/shared/models/MusicWork';
import { MusicWorkService } from 'src/app/shared/services/music-work.service';
import { NotificationService } from 'src/app/shared/services/notification.service';
import { SharedModule } from 'src/app/shared/shared.module';
import {Component, Input, OnInit} from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from 'src/app/shared/services/user.service';
import { User } from 'src/app/shared/models/User';


@Component({
    standalone: true,
    imports: [SharedModule, MaterialModule],
    selector: 'app-user-music-works-section',
    templateUrl: './music-works-section.component.html',
    styleUrls: ['./music-works-section.component.scss']
})
export class UserMusicWorkComponent implements OnInit {

  @Input() userData!: User;
  isUserMusicWorksLoaded = false;
  musicWorks!: MusicWork[];

  constructor(private musicWorkService: MusicWorkService,
              private imageService: ImageUploadService,
              private markService: MarkService,
              private router: Router,
              private notificationService: NotificationService,
              private dialog: MatDialog,
              public userService: UserService,) {
  }

  ngOnInit(): void {
    this.musicWorkService.getMusicWorksForUser(this.userData.id)
      .subscribe(data => {
        console.log("Music Works: ", data);
        this.musicWorks = data;
        this.getImagesToMusicWorks(this.musicWorks);
        this.getMarksToMusicWork(this.musicWorks);
        this.isUserMusicWorksLoaded = true;
      });
  }

  openEditDialog(): void {
    const createMusicWorkDialog = new MatDialogConfig();
    this.dialog.open(CreateMusicWorkComponent, createMusicWorkDialog);
  }

  getImagesToMusicWorks(musicWorks: MusicWork[]): void {
    musicWorks.forEach(p => {
      this.imageService.getImageToMusicWork(p.id as number)
        .subscribe(data => {
          p.image = data ? data.imageBytes : null;
        });
    });
  }


  getMarksToMusicWork(musicWorks: MusicWork[]): void {
    musicWorks.forEach(m => {
      this.markService.getMarksToMusicWork(m.id as number)
        .subscribe(data => {
          m.marks = data;
        });
    });
  }

  removeMusicWork(musciWork: MusicWork, index: number): void {
    const result = confirm('Do you really want to delete your work?');
    if (result) {
      this.musicWorkService.deleteMusicWork(musciWork.id as number)
        .subscribe(() => {
          this.musicWorks.splice(index, 1);
          this.notificationService.showSuccessSnackBar('MusciWork deleted');
        });
    }
  }

  formatImage(img: any): any {
    if (img == null) {
      return null;
    }
    return 'data:image/jpeg;base64,' + img;
  }


  goToManagePage(musicWorkId: number): void {
    this.router.navigate(['home/managework', musicWorkId]);
  }

}