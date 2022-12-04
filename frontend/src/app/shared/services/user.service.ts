import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { User } from "../models/User";

const USER_API = 'http://localhost:8080/api/user/';

@Injectable({
    providedIn: 'root'
})
export class UserService {
    currentUserId: number;

    constructor(private http: HttpClient) {
        this.getCurrentUser().subscribe(user => this.currentUserId = user.id);
    }

    getUserById(id: number): Observable<any> {
        return this.http.get(USER_API + id);
    }

    getUserByUsername(username: string): Observable<any> {
        return this.http.get(USER_API + username + '/user');
    }

    getCurrentUser(): Observable<any> {
        return this.http.get(USER_API);
    }

    getAllUsers(): Observable<any> {
        return this.http.get(USER_API + 'all');
    }

    updateUser(user: User): Observable<any> {
        return this.http.post(USER_API + 'update', user);
    }

    followUser(userId: number): Observable<any> {
        return this.http.post(USER_API + userId + '/follow', null);
    }

    unfollowUser(userId: number): Observable<any> {
        return this.http.post(USER_API + userId + '/unfollow', null);
    }

    getFollowingUsers(userId: number): Observable<any> {
        return this.http.get(USER_API + userId + '/following');
    }

    getUserFollowers(userId: number): Observable<any> {
        return this.http.get(USER_API + userId + '/followers');
    }
    
 }