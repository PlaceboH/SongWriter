import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Mark } from '../models/Mark';

const MARK_API = 'http://localhost:8080/api/mark/';

@Injectable({
    providedIn: 'root',
})
export class MarkService {
    constructor(private http: HttpClient) {}

    addMarkToMusicWork(
        musicWorkId: number,
        message: string,
        stars: number
    ): Observable<any> {
        return this.http.post(MARK_API + musicWorkId + '/create', {
            message: message,
            stars: stars,
        });
    }

    getMarksToMusicWork(musicWorkId: number): Observable<any> {
        return this.http.get(MARK_API + musicWorkId + '/all');
    }

    deleteMark(musicWorkId: number): Observable<any> {
        return this.http.post(MARK_API + musicWorkId + '/delete', null);
    }
}
