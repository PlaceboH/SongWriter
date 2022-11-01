import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HTTP_INTERCEPTORS } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, throwError } from "rxjs";
import { catchError } from "rxjs/operators";
import { TokenStorageService } from "../auth-page/token-storage.service";
import { NotificationService } from "../shared/services/notification.service";

@Injectable({
    providedIn: 'root'
})
export class AuthErrorInterceptorService implements HttpInterceptor {

    constructor(private tokenService: TokenStorageService,
                private notificationService: NotificationService) {}

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        return next.handle(req).pipe(catchError(err => {
            if (err.satus === 401) {
                this.tokenService.logOut();
                window.location.reload();
            }

            const error = err.error.message || err.statusText;
            this.notificationService.showSnackBar(error);

            return throwError(() => new Error(error));
        }))
    }
    
}

export const authErrorInterceptorProviders = [
    {provide: HTTP_INTERCEPTORS, useClass: AuthErrorInterceptorService, multi: true}
];
