import {Component, OnInit} from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { MaterialModule } from 'src/app/material.module';
import { MusicWork } from 'src/app/shared/models/MusicWork';
import { Post } from 'src/app/shared/models/Post';
import { CommentService } from 'src/app/shared/services/comment-service';
import { ImageUploadService } from 'src/app/shared/services/image.service';
import { MusicWorkService } from 'src/app/shared/services/music-work.service';
import { NotificationService } from 'src/app/shared/services/notification.service';
import { SharedModule } from 'src/app/shared/shared.module';


@Component({
    standalone: true,
    imports: [SharedModule, MaterialModule],
    selector: 'app-user-music-work-section',
    templateUrl: './music-works-section.component.html',
    styleUrls: ['./music-works-section.component.scss']
})
export class UserMussicWorkComponent implements OnInit {

  isUserMusicWorksLoaded = false;
  musicWorks!: MusicWork [];

  constructor(private musicWorkService: MusicWorkService,
              private imageService: ImageUploadService,
              private markService: MarkService,
              private notificationService: NotificationService,
              private dialog: MatDialog) {
  }

  ngOnInit(): void {
    this.musicWorkService.getMusicWorksForCurrentUser()
      .subscribe(data => {
        console.log(data);
        this.musicWorks = data;
        // this.getImagesToPosts(this.musicWorks);
        // this.getCommentsToPosts(this.musicWorks);
        this.isUserMusicWorksLoaded = true;
      });
  }

  openEditDialog(): void {
    const createPostDialog = new MatDialogConfig();;
    // this.dialog.open(CreatePostComponent, createPostDialog);
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
      this.MusicWorkService.deletePost(post.id as number)
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