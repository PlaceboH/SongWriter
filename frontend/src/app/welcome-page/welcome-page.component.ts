import { Component } from "@angular/core";
import { NavigationPageComponent } from "../shared/custom-components/sw-navigation-page/sw-navigation-page.component";
import { FooterSectionComponent } from "./footer-section/footer-section.component";
import { HeaderSectionComponent } from "./header-section/header-section.component";
import { MainInfoSectionComponent } from "./main-info-section/main-info-section.component";

@Component({
    standalone: true,
    imports: [NavigationPageComponent, HeaderSectionComponent, MainInfoSectionComponent, FooterSectionComponent],
    selector: 'welcome-page',
    templateUrl: './welcome-page.component.html',
    styleUrls: ['./welcome-page.component.scss']
  })
  export class WelcomePageComponent {
    navigationItems : {key: string, value: string }[] = 
                      [ {value: '/auth/login', key: 'Login'},
                        {value: '/auth/register', key: 'Create account'},
                        {value: '/', key: 'About SongWriter'},
                        {value: '/', key: 'About me'},
                        {value: '/', key: 'Link to Github'},
                        {value: '/', key: 'Support'},
                      ];
  }