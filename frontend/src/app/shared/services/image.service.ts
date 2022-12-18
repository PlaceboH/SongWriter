import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { EMPTY, Observable } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

const IMAGE_API = 'http://localhost:8080/api/image/';

@Injectable({
    providedIn: 'root',
})
export class ImageUploadService {
    constructor(private http: HttpClient) {}

    uploadImageToUser(file: File): Observable<any> {
        const uploadData = new FormData();
        uploadData.append('imageFile', file);

        return this.http.post(IMAGE_API + 'upload', uploadData);
    }

    getProfileImage(): Observable<any> {
        return this.http.get(IMAGE_API + 'profileImage');
    }

    getImageToUser(userId: number): Observable<any> {
        return this.http.get(IMAGE_API + userId + '/userImage');
    }

    uploadImageToPost(file: File, postId: number): Observable<any> {
        const uploadData = new FormData();
        uploadData.append('imageFile', file);

        return this.http.post(IMAGE_API + postId + '/postUpload', uploadData);
    }

    getImageToPost(postId: number): Observable<any> {
        return this.http.get(IMAGE_API + postId + '/postImage');
    }

    uploadImageToMusicWork(file: File, musicWorkId: number): Observable<any> {
        const uploadData = new FormData();
        uploadData.append('imageFile', file);

        return this.http.post(
            IMAGE_API + musicWorkId + '/musicWorkUpload',
            uploadData
        );
    }

    getImageToMusicWork(musicWorkId: number): Observable<any> {
        return this.http.get(IMAGE_API + musicWorkId + '/musicWorkImage').pipe(
            catchError((err, caught) => {
                return EMPTY;
            })
        );
    }
}
