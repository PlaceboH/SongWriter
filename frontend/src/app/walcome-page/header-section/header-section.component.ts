import { Component, OnInit } from "@angular/core";
import { SwButtonComponent } from "src/app/shared/custom-components/sw-button/sw-button.component";

@Component({
    standalone: true,
    imports: [SwButtonComponent],
    selector: 'header-section',
    templateUrl: './header-section.component.html',
    styleUrls: ['./header-section.component.scss']
  })
  export class HeaderSectionComponent implements OnInit{
    ngOnInit(): void {
        
    }

  }