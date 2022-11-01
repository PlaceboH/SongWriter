import { Component } from "@angular/core";
import { FooterSectionComponent } from "./footer-section/footer-section.component";
import { HeaderSectionComponent } from "./header-section/header-section.component";
import { MainInfoSectionComponent } from "./main-info-section/main-info-section.component";
import { NavigationPageComponent } from "./navigation-page/navigation-page.component";

@Component({
    standalone: true,
    imports: [NavigationPageComponent, HeaderSectionComponent, MainInfoSectionComponent, FooterSectionComponent],
    selector: 'welcome-page',
    templateUrl: './welcome-page.component.html',
    styleUrls: ['./welcome-page.component.scss']
  })
  export class WelcomePageComponent {
  
  }