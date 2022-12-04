import { Pipe, PipeTransform } from '@angular/core';
import { MusicWork } from '../models/MusicWork';


@Pipe({
    standalone: true,
    name: 'searchMusicWork'
})
export class SearchMusicWorkPipe implements PipeTransform {
     transform(data: MusicWork[], itemData: string): MusicWork[] {
        if (itemData) {
          return data.filter(item => {
            return item.title.includes(itemData) || item.username.includes(itemData);  
          })
        }
        return data;
      }
 }