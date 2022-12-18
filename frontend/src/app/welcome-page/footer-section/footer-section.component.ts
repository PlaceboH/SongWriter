import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';

@Component({
    standalone: true,
    imports: [CommonModule],
    selector: 'footer-section',
    templateUrl: './footer-section.component.html',
    styleUrls: ['./footer-section.component.scss'],
})
export class FooterSectionComponent {
    items: string[] = [
        'Company',
        'Contact us',
        'Carrers',
        'Privacy policy',
        'Terms',
    ];
}
