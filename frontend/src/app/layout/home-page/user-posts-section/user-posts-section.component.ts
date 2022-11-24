import {Component, OnInit} from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { MaterialModule } from 'src/app/material.module';
import { Post } from 'src/app/shared/models/Post';
import { CommentService } from 'src/app/shared/services/comment-service';
import { ImageUploadService } from 'src/app/shared/services/image.service';
import { NotificationService } from 'src/app/shared/services/notification.service';
import { PostService } from 'src/app/shared/services/post.service';
import { SharedModule } from 'src/app/shared/shared.module';
import { CreatePostComponent } from './create-post/create-post.component';


@Component({
    standalone: true,
    imports: [SharedModule, MaterialModule],
    selector: 'app-user-posts-section',
    templateUrl: './user-posts-section.component.html',
    styleUrls: ['./user-posts-section.component.scss']
})
export class UserPostsComponent implements OnInit {

  isUserPostsLoaded = false;
  posts!: Post [];

  constructor(private postService: PostService,
              private imageService: ImageUploadService,
              private commentService: CommentService,
              private notificationService: NotificationService,
              private dialog: MatDialog) {
  }

  ngOnInit(): void {
    this.postService.getPostForCurrentUser()
      .subscribe(data => {
        console.log(data);
        this.posts = data;
        this.getImagesToPosts(this.posts);
        this.getCommentsToPosts(this.posts);
        this.isUserPostsLoaded = true;
      });
  }

  openEditDialog(): void {
    const createPostDialog = new MatDialogConfig();;
    this.dialog.open(CreatePostComponent, createPostDialog);
  }

  getImagesToPosts(posts: Post[]): void {
    posts.forEach(p => {
      this.imageService.getImageToPost(p.id as number)
        .subscribe( (data: any) => {
          p.image = data.imageBytes;
        });
    });
  }


  getCommentsToPosts(posts: Post[]): void {
    posts.forEach(p => {
      this.commentService.getCommentsToPost(p.id as number)
        .subscribe(data => {
          p.comments = data;
        });
    });
  }

  removePost(post: Post, index: number): void {
    console.log(post);
    const result = confirm('Do you really want to delete this post?');
    if (result) {
      this.postService.deletePost(post.id as number)
        .subscribe(() => {
          this.posts.splice(index, 1);
          this.notificationService.showSuccessSnackBar('Post deleted');
        });
    }
  }

  formatImage(img: any): any {
    if (img == null) {
      return null;
    }
    return 'data:image/jpeg;base64,' + img;
  }

  deleteComment(commentId: number, postIndex: number, commentIndex: number): void {
    const post = this.posts[postIndex];

    this.commentService.deleteComment(commentId)
      .subscribe(() => {
        this.notificationService.showSuccessSnackBar('Comment removed');
        post.comments.splice(commentIndex, 1);
      });
  }

}