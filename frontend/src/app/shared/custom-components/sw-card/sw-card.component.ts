import { ChangeDetectionStrategy, Component, Input } from '@angular/core';

@Component({
    standalone: true,
    selector: 'sw-card',
    templateUrl: './sw-card.component.html',
    styleUrls: ['./sw-card.component.scss'],
    changeDetection: ChangeDetectionStrategy.OnPush,
})
export class SwCardComponent {
    @Input() title!: string;
    @Input() description!: string;

    constructor() {}
}
