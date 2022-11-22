import { Component, OnInit } from "@angular/core";
import { MaterialModule } from "src/app/material.module";
import { SharedModule } from "src/app/shared/shared.module";



@Component({
    standalone: true,
    imports: [SharedModule, MaterialModule],
    selector: 'home-page',
    templateUrl: './edit-user.component.html',
    styleUrls: ['./edit-user.component.scss']
  })
export class EditUserComponent implements OnInit {
    ngOnInit(): void {
        throw new Error("Method not implemented.");
    }


}