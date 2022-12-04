import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/shared/models/User';
import { UserService } from 'src/app/shared/services/user.service';
import { ActivatedRoute } from '@angular/router';
import { SharedModule } from 'src/app/shared/shared.module';
import { MaterialModule } from 'src/app/material.module';
import { ImageUploadService } from 'src/app/shared/services/image.service';
import { UserAboutComponent } from "../../home-page/user-about-section/user-about-section.component";
import { UserPostsComponent } from "../../home-page/user-posts-section/user-posts-section.component";
import { NotificationService } from 'src/app/shared/services/notification.service';
import { Observable } from 'rxjs';
import { UserMusicWorkComponent } from '../../home-page/music-works-section/music-works-section.component';

@Component({
    selector: 'app-user-details',
    standalone: true,
    templateUrl: './user-details.component.html',
    styleUrls: ['./user-details.component.scss'],
    imports: [SharedModule, MaterialModule, UserAboutComponent, UserMusicWorkComponent, UserPostsComponent]
})
export class UserDetailsComponent implements OnInit {

  isUserDataLoaded = false;
  user!: User;
  userProfileImage!: File;
  followingUsers$ = new Observable<any>;

  constructor(
      private imageService: ImageUploadService,
      private notificationService: NotificationService,
      private route: ActivatedRoute,
      private userService: UserService,
      ) {}

  ngOnInit(): void {
    this.userService.getUserById(+this.route.snapshot.paramMap.get('id'))
      .subscribe(data => {
        this.user = data;
        this.followingUsers$ = this.userService.getFollowingUsers(this.userService.currentUserId);
        this.getImageToUser(this.user.id);
        this.isUserDataLoaded = true;
        console.log("USER DATA: ", this.user);
      });
  }

  formatImage(img: any): any {
    if (img == null) {
      return null;
    }
    return 'data:image/jpeg;base64,' + img;
  }

  followUser(userId: number): void {
    this.userService.followUser(userId).subscribe(data => {
      this.notificationService.showSuccessSnackBar("Successfully following user");
      this.followingUsers$ = this.userService.getFollowingUsers(this.userService.currentUserId);
    });
  }

  unfollowUser(userId: number) {
    this.userService.unfollowUser(userId).subscribe(data =>{
      this.notificationService.showSuccessSnackBar("Successfully unfollowing user");
      this.followingUsers$ = this.userService.getFollowingUsers(this.userService.currentUserId);
    });
  }

  private getImageToUser(userId: number): void {
    this.imageService.getImageToUser(userId)
        .subscribe(data => this.userProfileImage = data ? data.imageBytes : null );
  }
}
