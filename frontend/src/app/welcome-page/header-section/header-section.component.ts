import { Component } from '@angular/core';
import { SwButtonComponent } from 'src/app/shared/custom-components/sw-button/sw-button.component';
import { SharedModule } from 'src/app/shared/shared.module';

@Component({
    standalone: true,
    imports: [SwButtonComponent, SharedModule],
    selector: 'header-section',
    templateUrl: './header-section.component.html',
    styleUrls: ['./header-section.component.scss'],
})
export class HeaderSectionComponent {}
