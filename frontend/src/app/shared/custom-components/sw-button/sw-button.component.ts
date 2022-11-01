import { ChangeDetectionStrategy, Component, Input } from "@angular/core";

@Component({
    standalone: true,
    selector: 'sw-button',
    templateUrl: './sw-button.component.html',
    styleUrls: ['./sw-button.component.scss'],
    changeDetection: ChangeDetectionStrategy.OnPush,
  })
  export class SwButtonComponent {
    @Input() title!: string;

    constructor() {}
  }