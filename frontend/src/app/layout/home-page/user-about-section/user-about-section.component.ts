import { Component, Input, OnInit } from "@angular/core";
import { MatDialog, MatDialogConfig } from "@angular/material/dialog";
import { MaterialModule } from "src/app/material.module";
import { SharedModule } from "src/app/shared/shared.module";
import { User } from "src/app/shared/models/User";
import { NavigationComponent } from "../../navigation/navigation.component";
import { EditUserComponent } from "./edit-user/edit-user.component";
import { UserService } from "src/app/shared/services/user.service";
import { NotificationService } from "src/app/shared/services/notification.service";
import { Observable, zip } from "rxjs";
import { UsersFollowPopupComponent } from "./users-follow-popup/users-follow-popup.component";

@Component({
    standalone: true,
    imports: [SharedModule, MaterialModule, NavigationComponent],
    selector: 'app-user-about-section',
    templateUrl: './user-about-section.component.html',
    styleUrls: ['./user-about-section.component.scss']
  })
  export class UserAboutComponent implements OnInit {
    @Input() userData!: User;
    currentUserId: number;
    subscriptions$ = new Observable<any>;

    constructor(private dialog: MatDialog,
                private notificationService: NotificationService,
                public userService: UserService ) {}

    ngOnInit(): void {
         this.subscriptions$ = zip(
              this.userService.getUserFollowers(this.userData.id), 
              this.userService.getFollowingUsers(this.userData.id));
    }

    openEditDialog(): void {
        const dialogUserEditConfig = new MatDialogConfig();
        dialogUserEditConfig.width = '400px';
        dialogUserEditConfig.data = {
            user: this.userData
        };
        this.dialog.open(EditUserComponent, dialogUserEditConfig);
      }

    followUser(userId: number): void {
      this.userService.followUser(userId).subscribe(data => {
        this.notificationService.showSuccessSnackBar("Successfully following user");
        this.subscriptions$ = zip(
          this.userService.getUserFollowers(this.userData.id), 
          this.userService.getFollowingUsers(this.userData.id));
      });
    }
  
    unfollowUser(userId: number) {
      this.userService.unfollowUser(userId).subscribe(data =>{
        this.notificationService.showSuccessSnackBar("Successfully unfollowing user");
        this.subscriptions$ = zip(
          this.userService.getUserFollowers(this.userData.id), 
          this.userService.getFollowingUsers(this.userData.id));
      });
    }

    openUsersPopup(users: string[]) {
      const dialogShowUsers = new MatDialogConfig();
      dialogShowUsers.width = '400px';
      dialogShowUsers.data = {
          users: users
      };
      this.dialog.open(UsersFollowPopupComponent, dialogShowUsers);
    }
    
  }