import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { MusicWork } from '../models/MusicWork';
import { Observable } from 'rxjs';

const POST_API = 'http://localhost:8080/api/music-work/';

@Injectable({
  providedIn: 'root'
})
export class MusicWorkService {

  constructor(private http: HttpClient) {
  }

  createMusicWork(musicWork: MusicWork): Observable<any> {
    return this.http.post(POST_API + 'create', musicWork);
  }

  getAllMusicWorks(): Observable<any> {
    return this.http.get(POST_API + 'all');
  }

  getMusicWorksForCurrentUser(): Observable<any> {
    return this.http.get(POST_API + 'user/posts');
  }

  deleteMusicWork(id: number): Observable<any> {
    return this.http.post(POST_API + id + '/delete', null);
  }

//   likeMusicWork(id: number, username: string): Observable<any> {
//     return this.http.post(POST_API + id + '/' + username + '/like', null);
//   }
}
