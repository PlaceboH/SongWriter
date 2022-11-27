import { AuthService } from "../auth.service";
import { CommonModule } from "@angular/common";
import { Component } from "@angular/core";
import { CustomValidators } from "src/app/shared/custom-validators/custom-validators";
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from "@angular/forms";
import { MaterialModule } from "src/app/material.module";
import { NotificationService } from "src/app/shared/services/notification.service";
import { Router, RouterModule } from "@angular/router";
import { TokenStorageService } from "../token-storage.service";

@Component({
    standalone: true,
    imports: [CommonModule, RouterModule, FormsModule, ReactiveFormsModule, MaterialModule],
    selector: 'register-page',
    templateUrl: './register-page.component.html',
    styleUrls: ['./register-page.component.scss']
  })
  export class RegisterPageComponent {

    registerForm!: FormGroup;

    constructor(
      private authService: AuthService,
      private tokenService: TokenStorageService,
      private notificationService: NotificationService,
      private router: Router,
      private fb: FormBuilder,
    ) {
      if (this.tokenService.getUser().token) {
        this.router.navigate(['home/profile']);
      }
    }

    ngOnInit(): void {
      this.registerForm = this.createregisterForm();
    }


    public submit(): void {
      this.authService.register({
        email: this.registerForm.value.email,
        username: this.registerForm.value.username,
        firstname: this.registerForm.value.firstname,
        lastname: this.registerForm.value.lastname,
        password: this.registerForm.value.password,
        confirmPassword: this.registerForm.value.confirmPassword,
      }).subscribe(data => {
        console.log(data);
        this.notificationService.showSuccessSnackBar('Successfully Registered!');
        this.router.navigate(['auth/login']);
      }, error => {
        if (this.registerForm.controls['password'].invalid) {
          this.notificationService.showErrorSnackBar('Something went wrong during registration... Password must contain at least one small and capital character, spetial sign and number');
        } else {
          this.notificationService.showErrorSnackBar('Something went wrong during registration...');
        }
      });
    }

    private createregisterForm(): FormGroup {
      return this.fb.group({
        username: ['', Validators.compose([Validators.required, Validators.minLength(5), Validators.maxLength(50)])],
        email: ['', [Validators.required, Validators.email]],
        firstname: ['', Validators.compose([Validators.required, Validators.minLength(1), Validators.maxLength(50)])],
        lastname: ['', Validators.compose([Validators.required, Validators.minLength(1), Validators.maxLength(50)])],
        password: ['', Validators.compose([Validators.required,
          CustomValidators.patternValidator(/\d/, {
            hasNumber: true
          }),
          CustomValidators.patternValidator(/[A-Z]/, {
            hasCapitalCase: true
          }),
          CustomValidators.patternValidator(/[a-z]/, {
            hasSmallCase: true
          }),
          CustomValidators.patternValidator(
            /[ !@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]/,
            {
              hasSpecialCharacters: true
            }
          ),
          Validators.minLength(8)])],
        confirmPassword: ['', Validators.compose([Validators.required, Validators.minLength(8)])],
      },{validator: CustomValidators.passwordMatchValidator});
    }
  }