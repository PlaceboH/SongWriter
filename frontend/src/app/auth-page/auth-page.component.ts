import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { NavigationPageComponent } from '../shared/custom-components/sw-navigation-page/sw-navigation-page.component';
import { LoginPageComponent } from './login-page/login-page.component';
import { RegisterPageComponent } from './register-page/register-page.component';

@Component({
  standalone: true,
  imports: [CommonModule, RouterModule, NavigationPageComponent, LoginPageComponent, RegisterPageComponent],
  selector: 'auth-page',
  templateUrl: './auth-page.component.html',
})
export class AuthPageComponent implements OnInit {
  navigationItems : {key: string, value: string }[] = 
        [ {value: 'login', key: 'Login'},
          {value: 'register', key: 'Register'},
          {value: '/', key: 'Welcome Page'},
        ];
  isLogin = false;

  constructor(private route: Router){}

  ngOnInit(): void { }

  ngOnChanges(): void {
    this.isLogin = this.route.url.endsWith("login");

    console.log(this.isLogin);
  }
}