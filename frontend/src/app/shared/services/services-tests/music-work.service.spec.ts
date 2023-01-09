import { TestBed } from '@angular/core/testing';
import {HttpClientTestingModule, HttpTestingController} from '@angular/common/http/testing';
import { MusicWorkService } from '../music-work.service';
import { COUNT_MUSIC_WORKS, MUSIC_WORKS } from 'src/tesst-data/music-works-test-data';
import { MusicWork } from '../../models/MusicWork';

describe('AppComponent', () => {

    const MUSIC_WORK_API = 'http://localhost:8080/api/music-work/';

    let musicWorkService: MusicWorkService, 
        httpTestingController: HttpTestingController;

    beforeEach(() => {
        TestBed.configureTestingModule({
            imports: [HttpClientTestingModule],
            providers: [MusicWorkService]
        });

        musicWorkService = TestBed.get(MusicWorkService),
        httpTestingController = TestBed.get(HttpTestingController);
    });

    it('should create new music work', () => {

        const newWork: MusicWork = {
            title: "test work",
            description: "some desc",
            lyrics: "My own summer",
        };

        musicWorkService.createMusicWork(newWork).subscribe();

        const req = httpTestingController.expectOne(MUSIC_WORK_API + 'create');

        expect(req.request.method).toEqual("POST");
    });

    it('should return all music works', () => {

        musicWorkService.getAllMusicWorks().subscribe(musicWorks => {
            expect(musicWorks).withContext('No musicWorks returned').toBeTruthy();
            expect(musicWorks.length).withContext('incorrect number of musicWorks').toBe(COUNT_MUSIC_WORKS);
            
            const musicWork = musicWorks.find((m: { id: number; }) => m.id === 10);
            expect(musicWork.title).toBe("Test work");
        });

        const req = httpTestingController.expectOne(MUSIC_WORK_API + 'all');

        expect(req.request.method).toEqual("GET");

        req.flush(Object.values(MUSIC_WORKS));
    });


    it('should return music work for current user', () => {

        musicWorkService.getMusicWorksForCurrentUser().subscribe(musicWorks => {
            expect(musicWorks).withContext('No musicWorks returned').toBeTruthy();
            expect(musicWorks.length).withContext('incorrect number of musicWorks').toBe(4);
            
        });

        const req = httpTestingController.expectOne(MUSIC_WORK_API + 'user/musicWorks');

        expect(req.request.method).toEqual("GET");

        req.flush((Object.values(MUSIC_WORKS) as MusicWork[] ).filter(m => m.username === "OBwell"));
    });

    
    it('should return music work for user by userId', () => {

        musicWorkService.getMusicWorksForUser(7).subscribe(musicWorks => {
            expect(musicWorks).withContext('No musicWorks returned').toBeTruthy();
            expect(musicWorks.length).withContext('incorrect number of musicWorks').toBe(4);
            
        });

        const req = httpTestingController.expectOne(MUSIC_WORK_API + 7 + '/musicWorks');

        expect(req.request.method).toEqual("GET");

        req.flush((Object.values(MUSIC_WORKS) as MusicWork[] ).filter(m => m.username === "OBwell"));
    });

    it('should delete music work by musicWorkId', () => {

        musicWorkService.deleteMusicWork(10).subscribe(musicWorks => {
            expect(musicWorks.length).withContext('incorrect number of musicWorks').toBe(COUNT_MUSIC_WORKS - 1);
        });

        const req = httpTestingController.expectOne(MUSIC_WORK_API + 10 + '/deleteWork');

        expect(req.request.method).toEqual("POST");

        req.flush((Object.values(MUSIC_WORKS) as MusicWork[] ).filter(m => m.id != 10));
    });

});