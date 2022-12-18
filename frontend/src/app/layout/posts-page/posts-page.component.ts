import { Component, OnInit } from '@angular/core';
import { SharedModule } from 'src/app/shared/shared.module';
import { MaterialModule } from 'src/app/material.module';
import { SearchPostPipe } from 'src/app/shared/custom-pipes/search-post.pipe';
import { Post } from 'src/app/shared/models/Post';
import { PostService } from 'src/app/shared/services/post.service';
import { ImageUploadService } from 'src/app/shared/services/image.service';
import { CommentService } from 'src/app/shared/services/comment-service';
import { UserService } from 'src/app/shared/services/user.service';
import { NotificationService } from 'src/app/shared/services/notification.service';
import { User } from 'src/app/shared/models/User';

@Component({
    selector: 'app-posts-page',
    standalone: true,
    imports: [SharedModule, MaterialModule, SearchPostPipe],
    templateUrl: './posts-page.component.html',
    styleUrls: ['./posts-page.component.scss'],
})
export class PostsPageComponent implements OnInit {
    currentUser: User;
    posts!: Post[];
    searchValue: string;
    isPostsLoaded: boolean = false;
    isUserLoaded: boolean = false;

    constructor(
        private postService: PostService,
        private imageService: ImageUploadService,
        private commentService: CommentService,
        private notificationService: NotificationService,
        public userService: UserService
    ) {}

    ngOnInit(): void {
        this.userService.getCurrentUser().subscribe((data) => {
            console.log('User: ', data);
            this.currentUser = data;
            this.isUserLoaded = true;
        });

        this.postService.getAllPosts().subscribe((data) => {
            this.posts = data;
            console.log('Posts: ', this.posts);
            this.getUsersToPost(this.posts);
            this.getImagesToPosts(this.posts);
            this.getCommentsToPost(this.posts);
            this.isPostsLoaded = true;
        });
    }

    getImagesToPosts(posts: Post[]): void {
        posts.forEach((post) => {
            this.imageService
                .getImageToPost(post.id as number)
                .subscribe((data) => {
                    post.image = data ? data.imageBytes : null;
                });
        });
    }

    getCommentsToPost(posts: Post[]): void {
        posts.forEach((post) => {
            this.commentService.getCommentsToPost(post.id).subscribe((data) => {
                post.comments = data;
            });
        });
    }

    getUsersToPost(posts: Post[]): void {
        posts.map((post) => {
            this.userService
                .getUserByUsername(post.username)
                .subscribe((user) => {
                    post.user = user;
                });
        });
    }

    likePost(postId: number, postIndex: number): void {
        const post = this.posts[postIndex];

        if (!post.usersLiked.includes(this.currentUser.username)) {
            this.postService
                .likePost(postId, this.currentUser.username)
                .subscribe(() => {
                    post.usersLiked.push(this.currentUser.username);
                    this.notificationService.showSuccessSnackBar('liked post!');
                });
        } else {
            this.postService
                .likePost(postId, this.currentUser.username)
                .subscribe(() => {
                    const index = post.usersLiked.indexOf(
                        this.currentUser.username,
                        0
                    );
                    if (index > -1) {
                        post.usersLiked.splice(index, 1);
                    }
                });
        }
    }

    postComment(event: EventTarget, postId: number, postIndex: number): void {
        const post = this.posts[postIndex];
        const message = (event as HTMLInputElement).value;

        console.log('Message: ', message);
        console.log('Post: ', post);
        this.commentService
            .addToCommentToPost(postId, message)
            .subscribe((data) => {
                console.log(data);
                post.comments.push(data);
            });
    }

    deleteComment(
        commentId: number,
        postIndex: number,
        commentIndex: number
    ): void {
        const post = this.posts[postIndex];

        this.commentService.deleteComment(commentId).subscribe(() => {
            this.notificationService.showSuccessSnackBar('Comment removed');
            post.comments.splice(commentIndex, 1);
        });
    }

    formatImage(img: any): any {
        if (img == null) {
            return null;
        }
        return 'data:image/jpeg;base64,' + img;
    }
}
