import { Component, OnInit } from '@angular/core';
import { NotificationService } from 'src/app/shared/services/notification.service';
import { UserService } from 'src/app/shared/services/user.service';
import { SharedModule } from 'src/app/shared/shared.module';
import { MaterialModule } from 'src/app/material.module';
import { User } from 'src/app/shared/models/User';
import { zip } from 'rxjs';
import { ImageUploadService } from 'src/app/shared/services/image.service';

@Component({
  selector: 'app-users-page',
  standalone: true,
  imports: [SharedModule, MaterialModule],
  templateUrl: './users-page.component.html',
  styleUrls: ['./users-page.component.scss']
})
export class UsersPageComponent implements OnInit {
  
  isUsersDataLoaded = false;
  users: User[];

  constructor(
      private imageService: ImageUploadService,
      private notificationService: NotificationService,
      private userService: UserService
    ) { }

  ngOnInit(): void {
    this.userService.getAllUsers()
      .subscribe(usersData => {
        this.users = usersData;
        console.log("USER DATA: ", this.users);
        this.getImagesToUsers(this.users);
        this.isUsersDataLoaded = true;
    });
  }

  getImagesToUsers(users: User[]): void {
    users.forEach(user => {
      this.imageService.getImageToUser(user.id as number)
        .subscribe({ 
          next: data => {
              user.image = data ? data.imageBytes : null;
          }
        });
    });
  }

  formatImage(img: any): any {
    if (img == null) {
      return null;
    }
    return 'data:image/jpeg;base64,' + img;
  }
}
