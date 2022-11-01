import { ChangeDetectionStrategy, Component, Input } from "@angular/core";

@Component({
    standalone: true,
    selector: 'sw-popup',
    templateUrl: './sw-popup.component.html',
    styleUrls: ['./sw-popup.component.scss'],
    changeDetection: ChangeDetectionStrategy.OnPush,
  })
  export class SwPopupComponent {
    @Input() title!: string;
    @Input() subtitle!: string;
    @Input() description!: string;

    constructor() {}
  }