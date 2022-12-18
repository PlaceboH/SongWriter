import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';

@Injectable({
    providedIn: 'root',
})
export class NotificationService {
    constructor(private snackbar: MatSnackBar) {}

    public showSuccessSnackBar(message: string): void {
        this.snackbar.open(message, '', {
            duration: 3000,
            panelClass: ['succeed-snackbar', 'login-snackbar'],
        });
    }

    public showErrorSnackBar(message: string): void {
        this.snackbar.open(message, '', {
            duration: 3000,
            panelClass: ['unsucceed-snackbar', 'login-snackbar'],
        });
    }
}
