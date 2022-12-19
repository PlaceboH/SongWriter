import { Component } from '@angular/core';
import { SwCardComponent } from 'src/app/shared/custom-components/sw-card/sw-card.component';
import { SharedModule } from 'src/app/shared/shared.module';

@Component({
    standalone: true,
    imports: [SwCardComponent, SharedModule],
    selector: 'main-info-section',
    templateUrl: './main-info-section.component.html',
    styleUrls: ['./main-info-section.component.scss'],
})
export class MainInfoSectionComponent {}
