import { Route } from '@angular/router';
import { AuthPageComponent } from './auth-page.component';
import { LoginPageComponent } from './login-page/login-page.component';
import { RegisterPageComponent } from './register-page/register-page.component';

export const AUTH_ROUTES: Route[] = [
    {
        path: '',
        component: AuthPageComponent,
        children: [
            {
                path: 'login',
                component: LoginPageComponent,
            },
            {
                path: 'register',
                component: RegisterPageComponent,
            },
        ],
    },
    {
        path: 'home',
        loadChildren: () =>
            import('../layout/routes').then((mod) => mod.HOME_ROUTES),
    },
    {
        path: '**',
        component: AuthPageComponent,
    },
];
