import { ChangeDetectionStrategy, Component, Input } from '@angular/core';
import { SharedModule } from '../../shared.module';

@Component({
    standalone: true,
    imports: [SharedModule],
    selector: 'sw-navigation-page',
    templateUrl: './sw-navigation-page.component.html',
    styleUrls: ['./sw-navigation-page.component.scss'],
    changeDetection: ChangeDetectionStrategy.OnPush,
})
export class NavigationPageComponent {
    @Input() items!: { key: string; value: string }[];
    isOpen: boolean = false;
}
