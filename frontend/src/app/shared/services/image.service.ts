import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

const IMAGE_API = 'http://localhost:8080/api/image/';

@Injectable({
  providedIn: 'root'
})
export class ImageUploadService {

  constructor(private http: HttpClient) { }

  uploadImageToUser(file: File): Observable<any> {
    const uploadData = new FormData();
    uploadData.append('imageFile', file);

    return this.http.post(IMAGE_API + 'upload', uploadData);
  }

  getProfileImage(): Observable<any> {
    return this.http.get(IMAGE_API + 'profileImage');
  }

  uploadImageToPost(file: File, postId: number): Observable<any> {
    const uploadData = new FormData();
    uploadData.append('imageFile', file);

    return this.http.post(IMAGE_API + postId + '/postUpload', uploadData);
  }

  getImageToPost(postId: number): any {
    return this.http.get(IMAGE_API + postId + '/postImage');
  }

  uploadImageToMusicWork(file: File, musicWorkId: number): Observable<any> {
    const uploadData = new FormData();
    uploadData.append('imageFile', file);

    return this.http.post(IMAGE_API + musicWorkId + '/musicWorkUpload', uploadData);
  }

  getImageToMusicWork(musicWorkId: number): any {
    return this.http.get(IMAGE_API + musicWorkId + '/musicWorkImage');
  }
}