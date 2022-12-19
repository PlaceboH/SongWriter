import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { SharedModule } from 'src/app/shared/shared.module';
import { MaterialModule } from 'src/app/material.module';

@Component({
    selector: 'app-users-follow-popup',
    standalone: true,
    imports: [SharedModule, MaterialModule],
    templateUrl: './users-follow-popup.component.html',
    styleUrls: ['./users-follow-popup.component.scss'],
})
export class UsersFollowPopupComponent implements OnInit {
    constructor(@Inject(MAT_DIALOG_DATA) public data: any) {}

    ngOnInit(): void {
        console.log(this.data.users);
    }
}
