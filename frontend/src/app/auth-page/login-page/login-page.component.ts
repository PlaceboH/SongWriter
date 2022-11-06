import { CommonModule } from "@angular/common";
import { Component, OnInit } from "@angular/core";
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from "@angular/forms";
import { Router, RouterModule } from "@angular/router";
import { MaterialModule } from "src/app/material.module";
import { NotificationService } from "src/app/shared/services/notification.service";
import { AuthService } from "../auth.service";
import { TokenStorageService } from "../token-storage.service";

@Component({
    standalone: true,
    imports: [CommonModule, RouterModule, FormsModule, ReactiveFormsModule, MaterialModule],
    selector: 'login-page',
    templateUrl: './login-page.component.html',
    styleUrls: ['./login-page.component.scss']
  })
  export class LoginPageComponent implements OnInit {
    loginForm!: FormGroup;

    constructor(
      private authService: AuthService,
      private tokenService: TokenStorageService,
      private notificationService: NotificationService,
      private router: Router,
      private fb: FormBuilder,
    ) {       
      if (this.tokenService.getUser().token) {
        this.router.navigate(['home']);
      }
    }

    ngOnInit(): void {
      this.loginForm = this.createLoginForm();
    }


    public submit(): void {
      this.authService.login({
        username: this.loginForm.value.username,
        password: this.loginForm.value.password,
      }).subscribe( data => { 
        console.log("login data: ", data);
        this.tokenService.saveToken(data.token);
        this.tokenService.saveUser(data);

        this.notificationService.showSuccessSnackBar('Successfully logged in');
        this.router.navigate(['home']);
      }, error => {
        console.log("login error: ", error);
        this.notificationService.showErrorSnackBar("Login Error! Bad password or username");
      });
    }


    private createLoginForm(): FormGroup {
      return this.fb.group({
        username: ['', Validators.compose([Validators.required, Validators.minLength(5), Validators.maxLength(50)])],
        password: ['', Validators.compose([Validators.required, Validators.minLength(8)])],
      });
    }

}