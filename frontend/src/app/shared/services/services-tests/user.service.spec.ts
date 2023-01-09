import { TestBed } from '@angular/core/testing';
import { UserService } from '../user.service';
import {HttpClientTestingModule, HttpTestingController} from '@angular/common/http/testing';
import { USERS } from 'src/tesst-data/users-test-data';
import { User } from '../../models/User';

describe('AppComponent', () => {

    const USER_API = 'http://localhost:8080/api/user/';

    let userService: UserService, 
        httpTestingController: HttpTestingController;

    beforeEach(() => {
        TestBed.configureTestingModule({
            imports: [HttpClientTestingModule],
            providers: [UserService]
        });

        userService = TestBed.get(UserService),
        httpTestingController = TestBed.get(HttpTestingController);
    });

    it('should return user by id', () => {

        userService.getUserById(1).subscribe(user => {
            expect(user).toBeTruthy();
            expect(user.id).toBe(1);
        });

        const req = httpTestingController.expectOne(USER_API + 1);

        expect(req.request.method).toEqual("GET");

        req.flush(USERS[1]);
    });

    it('should return user by username', () => {

        userService.getUserByUsername('Marek007').subscribe(user => {
            expect(user).toBeTruthy();
            expect(user.id).toBe(10);
        });

        const req = httpTestingController.expectOne(USER_API + 'Marek007' + '/user');

        expect(req.request.method).toEqual("GET");

        req.flush(USERS[10]);
    });

    it('should return current user', () => {

        userService.getCurrentUser().subscribe(user => {
            expect(user).toBeTruthy();
            expect(user.id).toBe(10);
            expect(user.username).toBe('Marek007');
        });

        const req = httpTestingController.match(USER_API);

        expect(req[0].request.method).toEqual("GET");
        expect(req.length).toBe(2);
        expect(req[0].request.method).toBe(req[1].request.method);

        req[0].flush(USERS[10]);
    });

    it('should return all users', () => {

        userService.getAllUsers().subscribe(users => {
            expect(users).withContext('No users returned').toBeTruthy();
            expect(users.length).withContext('incorrect number of users').toBe(8);
            const user = users.find((user: { id: number; }) => user.id == 10);
            expect(user.firstname).toBe("Marek");
        });

        const req = httpTestingController.expectOne(USER_API + 'all');

        expect(req.request.method).toEqual("GET");

        req.flush(Object.values(USERS));
    });

    it('should update user data', () => {

        const changes: Partial<User> = {firstname: "Antek"};

        userService.updateUser(USERS[10]).subscribe(user => {
            expect(user).toBeTruthy();
            expect(user.id).toBe(10);
            expect(user.username).toBe('Marek007');
            expect(user.firstname).toBe('Antek');
        });

        const req = httpTestingController.expectOne(USER_API + 'update');

        expect(req.request.method).toEqual("POST");

        req.flush({
            ...USERS[10],
            ...changes,
            });
    });

    it('should follow user', () => {

        userService.followUser(1).subscribe(user => {
            expect(user).toBeTruthy();
            expect(user.id).toBe(1);
        });

        const req = httpTestingController.expectOne(USER_API + 1 + '/follow');

        expect(req.request.method).toEqual("POST");

        req.flush(USERS[1]);
    });

    it('should unfollow user', () => {

        userService.unfollowUser(1).subscribe(user => {
            expect(user).toBeTruthy();
            expect(user.id).toBe(1);
        });

        const req = httpTestingController.expectOne(USER_API + 1 + '/unfollow');

        expect(req.request.method).toEqual("POST");

        req.flush(USERS[1]);
    });

    it('should return following usernames', () => {

        userService.getFollowingUsers(7).subscribe(user => {
            expect(user).toBeTruthy();
            const newFollowingUser: string = user.following.find((username: string) => username === "NewUser");
            expect(newFollowingUser).toBeTruthy();
        });

        const req = httpTestingController.expectOne(USER_API + 7 + '/following');

        expect(req.request.method).toEqual("GET");

        req.flush({...USERS[7], ...USERS[7].following.push("NewUser")});
    });

    it('should return followers usernames', () => {

        userService.getUserFollowers(7).subscribe(user => {
            expect(user).toBeTruthy();
            const newFollowingUser: string = user.following.find((username: string) => username === "NewUser");
            expect(newFollowingUser).toBeTruthy();
        });

        const req = httpTestingController.expectOne(USER_API + 7 + '/followers');

        expect(req.request.method).toEqual("GET");

        req.flush({...USERS[7], ...USERS[7].followers.push("NewUser")});
    });

});