import { Component } from '@angular/core';
import { SharedModule } from '../shared/shared.module';
import { NavigationComponent } from './navigation/navigation.component';
import { HomePageComponent } from './home-page/home-page.component';

@Component({
    standalone: true,
    selector: 'layout-pages',
    templateUrl: './layout.component.html',
    imports: [SharedModule, NavigationComponent, HomePageComponent],
})
export class LayoutComponent {}
