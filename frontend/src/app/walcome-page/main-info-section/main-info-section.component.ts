import { Component, OnInit } from "@angular/core";
import { SwCardComponent } from "src/app/shared/custom-components/sw-card/sw-card.component";

@Component({
    standalone: true,
    imports: [SwCardComponent],
    selector: 'main-info-section',
    templateUrl: './main-info-section.component.html',
    styleUrls: ['./main-info-section.component.scss']
  })
  export class MainInfoSectionComponent implements OnInit{
    ngOnInit(): void {
        
    }
    
  }