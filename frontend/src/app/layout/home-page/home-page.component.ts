import { Component, OnInit } from "@angular/core";
import { EditUserComponent } from "./edit-user/edit-user.component";
import { ImageUploadService } from "src/app/shared/services/image.service";
import { MatDialog, MatDialogConfig } from "@angular/material/dialog";
import { MaterialModule } from "src/app/material.module";
import { NavigationComponent } from "../navigation/navigation.component";
import { NotificationService } from "src/app/shared/services/notification.service";
import { PostService } from "src/app/shared/services/post.service";
import { SharedModule } from "src/app/shared/shared.module";
import { TokenStorageService } from "src/app/auth-page/token-storage.service";
import { User } from "src/app/shared/models/User";
import { UserService } from "src/app/shared/services/user.service";

@Component({
    standalone: true,
    imports: [SharedModule, MaterialModule, NavigationComponent],
    selector: 'home-page',
    templateUrl: './home-page.component.html',
    styleUrls: ['./home-page.component.scss']
  })
  export class HomePageComponent implements OnInit {


    isUserDataLoaded = false;
    user!: User;
    selectedFile!: File;
    userProfileImage!: File;
    previewImgURL: any;
  
    constructor(private tokenService: TokenStorageService,
                private postService: PostService,
                private dialog: MatDialog,
                private notificationService: NotificationService,
                private imageService: ImageUploadService,
                private userService: UserService) {
    }
  
    ngOnInit(): void {
      this.userService.getCurrentUser()
        .subscribe(data => {
          this.user = data;
          this.isUserDataLoaded = true;
        });
  
      this.imageService.getProfileImage()
        .subscribe(data => {

          console.log("imege", data);
          this.userProfileImage = data.imageBytes;
        });
    }
  
    onFileSelected(event: any ): void {
      this.selectedFile = event.target.files[0];
  
      const reader = new FileReader();
      reader.readAsDataURL(this.selectedFile);
      reader.onload = () => {
        this.previewImgURL = reader.result;
      };
    }
  
    openEditDialog(): void {
      const dialogUserEditConfig = new MatDialogConfig();
      dialogUserEditConfig.width = '400px';
      dialogUserEditConfig.data = {
        user: this.user
      };
      this.dialog.open(EditUserComponent, dialogUserEditConfig);
    }
  
    formatImage(img: any): any {
      if (img == null) {
        return null;
      }
      return 'data:image/jpeg;base64,' + img;
    }
  
    onUpload(): void {
      if (this.selectedFile != null) {
        this.imageService.uploadImageToUser(this.selectedFile)
          .subscribe(() => {
            this.notificationService.showSuccessSnackBar('Profile Image updated successfully');
          });
      }
    }
}
