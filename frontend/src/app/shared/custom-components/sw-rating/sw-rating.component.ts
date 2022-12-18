import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { SharedModule } from '../../shared.module';
import { MaterialModule } from 'src/app/material.module';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
    standalone: true,
    imports: [SharedModule, MaterialModule],
    selector: 'sw-rating',
    templateUrl: './sw-rating.component.html',
    styleUrls: ['./sw-rating.component.scss'],
})
export class SwRatingComponent implements OnInit {
    @Input('rating') rating: number = 3;
    @Input('starCount') starCount: number = 5;
    @Output() private ratingUpdated = new EventEmitter();
    ratingArr: any = [];

    private snackBarDuration: number = 1000;

    constructor(private snackBar: MatSnackBar) {}

    ngOnInit(): void {
        for (let index = 0; index < this.starCount; index++) {
            this.ratingArr.push(index);
        }
    }

    onClick(rating: number) {
        this.snackBar.open('You rated ' + rating + ' / ' + this.starCount, '', {
            duration: this.snackBarDuration,
        });
        this.ratingUpdated.emit(rating);
        return false;
    }

    showIcon(index: number) {
        if (this.rating >= index + 1) {
            return 'star';
        } else {
            return 'star_border';
        }
    }
}
