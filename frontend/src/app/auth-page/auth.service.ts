import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";


export type UserLogInData = {
    username: string;
    password: string;
};

export type UserRegisterData = {
    username: string;
    email: string;
    firstname: string;
    lastname: string;
    password: string;
    confirmPassword: string;
};

const AUTH_API = 'http://localhost:8080/api/auth/';

@Injectable({
    providedIn: 'root'
})
export class AuthService { 

    constructor(private http: HttpClient) {}

    public login(user: UserLogInData): Observable<any> {
        console.log("USER AUTH LOGIN DATA: ", user);
        return this.http.post(AUTH_API + 'signin', { ...user });
    }

    public register(user: UserRegisterData): Observable<any> {
        console.log("USER AUTH REGISTER DATA: ", user);
        return this.http.post(AUTH_API + 'signup', { ...user });
    }
}