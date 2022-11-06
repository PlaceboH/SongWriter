import { CommonModule } from "@angular/common";
import { Component, Input } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { RouterModule } from "@angular/router";
import { delay } from "rxjs/operators";

@Component({
    standalone: true,
    imports: [CommonModule, RouterModule, FormsModule],
    selector: 'sw-navigation-page',
    templateUrl: './sw-navigation-page.component.html',
    styleUrls: ['./sw-navigation-page.component.scss']
  })
  export class NavigationPageComponent {
    @Input() items!: {key: string, value: string}[];
    isOpen: boolean = false;
  }