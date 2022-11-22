import { Component, Inject, OnInit } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { MatDialogRef, MAT_DIALOG_DATA } from "@angular/material/dialog";
import { MaterialModule } from "src/app/material.module";
import { User } from "src/app/shared/models/User";
import { NotificationService } from "src/app/shared/services/notification.service";
import { UserService } from "src/app/shared/services/user.service";
import { SharedModule } from "src/app/shared/shared.module";

@Component({
    standalone: true,
    imports: [SharedModule, MaterialModule],
    selector: 'home-page',
    templateUrl: './edit-user.component.html',
    styleUrls: ['./edit-user.component.scss']
  })
export class EditUserComponent implements OnInit {

    profileEditForm!: FormGroup;

    constructor(private dialogRef: MatDialogRef<EditUserComponent>,
                private fb: FormBuilder,
                private notificationService: NotificationService,
                private userService: UserService,
                @Inject(MAT_DIALOG_DATA) public data: any) { }
  
    ngOnInit(): void {
      this.profileEditForm = this.createProfileForm();
    }
  
    createProfileForm(): FormGroup {
      return this.fb.group({
        firstName: [
          this.data.user.firstname,
          Validators.compose([Validators.required])
        ],
        lastName: [
          this.data.user.lastname,
          Validators.compose([Validators.required])
        ],
        bio: [
          this.data.user.bio,
          Validators.compose([Validators.required])
        ]
      });
    }
  
    submit(): void {
      this.userService.updateUser(this.updateUser())
        .subscribe(() => {
          this.notificationService.showSuccessSnackBar('User updated successfully');
          this.dialogRef.close();
        });
    }
  
    private updateUser(): User {
      this.data.user.firstname = this.profileEditForm.value.firstName;
      this.data.user.lastname = this.profileEditForm.value.lastName;
      this.data.user.bio = this.profileEditForm.value.bio;
      return this.data.user;
    }
  
    closeDialog() {
      this.dialogRef.close();
    }
}