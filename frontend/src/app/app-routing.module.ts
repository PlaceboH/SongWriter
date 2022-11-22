import { NgModule } from '@angular/core';
import { Route, RouterModule } from '@angular/router';
import { WelcomePageComponent } from './welcome-page/welcome-page.component';

const routes: Route[] = [
  {
    path: '',
    component: WelcomePageComponent,
  },
  {
    path: 'auth',
    loadChildren: () =>
      import('./auth-page/routes').then((mod) => mod.AUTH_ROUTES),
  },
  {
    path: 'home',
    loadChildren: () =>
      import('./layout/routes').then((mod) => mod.HOME_ROUTES),
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}

