import { Component, Input } from "@angular/core";
import { MatDialog, MatDialogConfig } from "@angular/material/dialog";
import { MaterialModule } from "src/app/material.module";
import { SharedModule } from "src/app/shared/shared.module";
import { User } from "src/app/shared/models/User";
import { NavigationComponent } from "../../navigation/navigation.component";
import { EditUserComponent } from "../edit-user/edit-user.component";

@Component({
    standalone: true,
    imports: [SharedModule, MaterialModule, NavigationComponent],
    selector: 'user-about-section',
    templateUrl: './user-about-section.component.html',
    styleUrls: ['./user-about-section.component.scss']
  })
  export class UserAboutComponent {
    @Input() userData!: User;
    constructor( private dialog: MatDialog ) {}

    openEditDialog(): void {
        const dialogUserEditConfig = new MatDialogConfig();
        dialogUserEditConfig.width = '400px';
        dialogUserEditConfig.data = {
            user: this.userData
        };
        this.dialog.open(EditUserComponent, dialogUserEditConfig);
      }
  }