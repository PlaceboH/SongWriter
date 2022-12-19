import { Pipe, PipeTransform } from '@angular/core';
import { Post } from '../models/Post';

@Pipe({
    standalone: true,
    name: 'searchPost',
})
export class SearchPostPipe implements PipeTransform {
    transform(data: Post[], itemData: string): Post[] {
        if (itemData) {
            return data.filter((item) => {
                return (
                    item.title.includes(itemData) ||
                    item.username.includes(itemData) ||
                    item.caption.includes(itemData)
                );
            });
        }
        return data;
    }
}
