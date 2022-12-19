import { Component } from '@angular/core';
import { NavigationPageComponent } from '../shared/custom-components/sw-navigation-page/sw-navigation-page.component';
import { FooterSectionComponent } from './footer-section/footer-section.component';
import { HeaderSectionComponent } from './header-section/header-section.component';
import { MainInfoSectionComponent } from './main-info-section/main-info-section.component';

@Component({
    standalone: true,
    imports: [
        NavigationPageComponent,
        HeaderSectionComponent,
        MainInfoSectionComponent,
        FooterSectionComponent,
    ],
    selector: 'welcome-page',
    templateUrl: './welcome-page.component.html',
    styleUrls: ['./welcome-page.component.scss'],
})
export class WelcomePageComponent {
    navigationItems: { key: string; value: string }[] = [
        { value: '/auth/login', key: 'songwriter.login' },
        { value: '/auth/register', key: 'welcome.navigation.createAccount' },
        { value: '/', key: 'welcome.navigation.aboutSW' },
        { value: '/', key: 'welcome.navigation.aboutMe' },
        { value: '/', key: 'welcome.navigation.git' },
        { value: '/', key: 'welcome.navigation.support' },
    ];
}
