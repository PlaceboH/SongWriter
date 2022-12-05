import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-music-work-details-page',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './music-work-details-page.component.html',
  styleUrls: ['./music-work-details-page.component.scss']
})
export class MusicWorkDetailsPageComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

}
