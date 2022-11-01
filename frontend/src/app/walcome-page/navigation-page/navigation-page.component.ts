import { CommonModule } from "@angular/common";
import { Component, OnInit } from "@angular/core";

@Component({
    standalone: true,
    imports: [CommonModule],
    selector: 'navigation-page',
    templateUrl: './navigation-page.component.html',
    styleUrls: ['./navigation-page.component.scss']
  })
  export class NavigationPageComponent implements OnInit{
    items: string[] = ['Login', 'Create account', 'About SongWriter', 'About me', 'Link to Github', 'Support'];

    ngOnInit(): void {
        
    }

    
  }