import { Component, OnInit } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { ImageUploadService } from "src/app/shared/services/image.service";
import { MatDialogRef } from "@angular/material/dialog";
import { MaterialModule } from "src/app/material.module";
import { NotificationService } from "src/app/shared/services/notification.service";
import { Post } from "src/app/shared/models/Post";
import { PostService } from "src/app/shared/services/post.service";
import { SharedModule } from "src/app/shared/shared.module";

@Component({
    standalone: true,
    imports: [SharedModule, MaterialModule],
    selector: 'app-create-post',
    templateUrl: './create-post.component.html',
    styleUrls: ['./create-post.component.scss']
  })

  export class CreatePostComponent implements OnInit {
  
    postForm!: FormGroup;
    selectedFile!: File;
    isPostCreated = false;
    createdPost!: Post;
    previewImgURL: any;
  
    constructor(private dialogRef: MatDialogRef<CreatePostComponent>,
                private postService: PostService,
                private imageUploadService: ImageUploadService,
                private notificationService: NotificationService,
                private fb: FormBuilder) {}
  
    ngOnInit(): void {
        this.postForm = this.createPostForm();
    }
  
    createPostForm(): FormGroup {
        return this.fb.group({
            title: ['', Validators.compose([Validators.required])],
            caption: ['', Validators.compose([Validators.required])],
        });
    }
  
    submit(): void {
        this.postService.createPost({
            title: this.postForm.value.title,
            caption: this.postForm.value.caption,
        }).subscribe(data => {
            this.createdPost = data;
            console.log("Create Post: ", data);
            if (this.createdPost.id != null) {
                this.imageUploadService.uploadImageToPost(this.selectedFile, this.createdPost.id)
                .subscribe(() => {
                    this.notificationService.showSuccessSnackBar('Post created successfully');
                    this.isPostCreated = true;
                    this.dialogRef.close();
                });
            }
        });
    }

    closeDialog() {
        this.dialogRef.close();
    }
  
    onFileSelected(event: any): void {
        this.selectedFile = event.target.files[0];

        const reader = new FileReader();
        reader.readAsDataURL(this.selectedFile);
        reader.onload = (e) => {
            this.previewImgURL = reader.result;
        };
    }
}