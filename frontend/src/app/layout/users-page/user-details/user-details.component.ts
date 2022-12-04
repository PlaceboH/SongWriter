import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/shared/models/User';
import { UserService } from 'src/app/shared/services/user.service';
import { ActivatedRoute } from '@angular/router';
import { SharedModule } from 'src/app/shared/shared.module';
import { MaterialModule } from 'src/app/material.module';
import { ImageUploadService } from 'src/app/shared/services/image.service';
import { UserAboutComponent } from "../../home-page/user-about-section/user-about-section.component";
import { UserMussicWorkComponent } from "../../home-page/music-works-section/music-works-section.component";
import { UserPostsComponent } from "../../home-page/user-posts-section/user-posts-section.component";

@Component({
    selector: 'app-user-details',
    standalone: true,
    templateUrl: './user-details.component.html',
    styleUrls: ['./user-details.component.scss'],
    imports: [SharedModule, MaterialModule, UserAboutComponent, UserMussicWorkComponent, UserPostsComponent]
})
export class UserDetailsComponent implements OnInit {

  isUserDataLoaded = false;
  user!: User;
  userProfileImage!: File;

  constructor(
      private userService: UserService,
      private imageService: ImageUploadService,
      private route: ActivatedRoute,
      ) {}

  ngOnInit(): void {
    this.userService.getUserById(+this.route.snapshot.paramMap.get('id'))
      .subscribe(data => {
        this.user = data;
        this.isUserDataLoaded = true;
        console.log("USER DATA: ", this.user);
      });
    this.getImageToUser();
  }

  private getImageToUser(): void {
    this.imageService.getImageToUser(this.user.id as number)
        .subscribe(data => this.userProfileImage = data.imageBytes);
  }

  formatImage(img: any): any {
    if (img == null) {
      return null;
    }
    return 'data:image/jpeg;base64,' + img;
  }

}
