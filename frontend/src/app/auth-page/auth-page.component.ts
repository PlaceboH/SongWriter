import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { LoginPageComponent } from './login-page/login-page.component';
import { NavigationPageComponent } from '../shared/custom-components/sw-navigation-page/sw-navigation-page.component';
import { RegisterPageComponent } from './register-page/register-page.component';
import { RouterModule } from '@angular/router';

@Component({
  standalone: true,
  imports: [CommonModule, RouterModule, NavigationPageComponent, LoginPageComponent, RegisterPageComponent],
  selector: 'auth-page',
  templateUrl: './auth-page.component.html',
})
export class AuthPageComponent {
  navigationItems : {key: string, value: string }[] = 
        [ {value: 'login', key: 'Login'},
          {value: 'register', key: 'Register'},
          {value: '/', key: 'Welcome Page'},
        ];

  constructor(){}
}