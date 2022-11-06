import { Component, OnInit } from "@angular/core";
import { SwButtonComponent } from "src/app/shared/custom-components/sw-button/sw-button.component";
import { TranslateService } from '@ngx-translate/core';
import { SharedModule } from "src/app/shared/shared.module";

@Component({
    standalone: true,
    imports: [SwButtonComponent, SharedModule],
    selector: 'header-section',
    templateUrl: './header-section.component.html',
    styleUrls: ['./header-section.component.scss']
  })
  export class HeaderSectionComponent implements OnInit{
    constructor(public translate: TranslateService) {
    }

    ngOnInit(): void {
    }
  }