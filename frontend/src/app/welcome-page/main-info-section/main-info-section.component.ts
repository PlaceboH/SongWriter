import { Component, OnInit } from "@angular/core";
import { RouterModule } from "@angular/router";
import { SwCardComponent } from "src/app/shared/custom-components/sw-card/sw-card.component";

@Component({
    standalone: true,
    imports: [SwCardComponent, RouterModule],
    selector: 'main-info-section',
    templateUrl: './main-info-section.component.html',
    styleUrls: ['./main-info-section.component.scss']
  })
  export class MainInfoSectionComponent implements OnInit{
    ngOnInit(): void {
        
    }
    
  }