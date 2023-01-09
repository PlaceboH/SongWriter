import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { MusicWork } from '../models/MusicWork';
import { Observable } from 'rxjs';

const MUSIC_WORK_API = 'http://localhost:8080/api/music-work/';

@Injectable({
    providedIn: 'root',
})
export class MusicWorkService {
    constructor(private http: HttpClient) {}

    createMusicWork(musicWork: MusicWork): Observable<any> {
        return this.http.post(MUSIC_WORK_API + 'create', musicWork);
    }

    getAllMusicWorks(): Observable<any> {
        return this.http.get(MUSIC_WORK_API + 'all');
    }

    getMusicWorksForCurrentUser(): Observable<any> {
        return this.http.get(MUSIC_WORK_API + 'user/musicWorks');
    }

    getMusicWorksForUser(userId: number): Observable<any> {
        return this.http.get(MUSIC_WORK_API + userId + '/musicWorks');
    }

    deleteMusicWork(id: number): Observable<any> {
        return this.http.post(MUSIC_WORK_API + id + '/deleteWork', null);
    }
}
