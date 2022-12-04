import { Component, OnInit } from '@angular/core';
import { ImageUploadService } from 'src/app/shared/services/image.service';
import { map } from 'rxjs/operators';
import { MaterialModule } from 'src/app/material.module';
import { NotificationService } from 'src/app/shared/services/notification.service';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';
import { SearchUserPipe } from 'src/app/shared/custom-pipes/search-user.pipe';
import { SharedModule } from 'src/app/shared/shared.module';
import { User } from 'src/app/shared/models/User';
import { UserService } from 'src/app/shared/services/user.service';

@Component({
  selector: 'app-users-page',
  standalone: true,
  imports: [SharedModule, MaterialModule, SearchUserPipe],
  templateUrl: './users-page.component.html',
  styleUrls: ['./users-page.component.scss']
})
export class UsersPageComponent implements OnInit {

  isUsersDataLoaded = false;
  myUser: User;
  users: User[];
  followingUsers$ = new Observable<any>;
  searchValue: string;
  constructor(
      private imageService: ImageUploadService,
      private router: Router,
      private notificationService: NotificationService,
      private userService: UserService
    ) { }

  ngOnInit(): void {
    this.userService.getCurrentUser().subscribe(myUser => {
      this.myUser = myUser;
      this.followingUsers$ = this.userService.getFollowingUsers(this.myUser.id);
      this.userService.getAllUsers()
        .pipe(map(data => data.filter((user: { id: number; }) => user.id != this.myUser.id )))
            .subscribe(usersData => {
              console.log("all users: ", usersData);
              this.users = usersData;
              this.getImagesToUsers(this.users);
              this.isUsersDataLoaded = true;
          });
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
      this.followingUsers$ = this.userService.getFollowingUsers(this.myUser.id);
    });
  }

  unfollowUser(userId: number) {
    this.userService.unfollowUser(userId).subscribe(data =>{
      this.notificationService.showSuccessSnackBar("Successfully unfollowing user");
      this.followingUsers$ = this.userService.getFollowingUsers(this.myUser.id);
    });
  }
  
  goToUserDetails(userId: number) {
    this.router.navigate(['home/user-details', userId]);
  }

  private getImagesToUsers(users: User[]): void {
    users.forEach(user => {
      this.imageService.getImageToUser(user.id as number)
        .subscribe({ 
          next: data => {
              user.image = data ? data.imageBytes : null;
          }
        });
    });
  }
}
