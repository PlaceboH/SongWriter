import { NgModule } from '@angular/core';
import { Route, RouterModule } from '@angular/router';
import { WelcomePageComponent } from './walcome-page/welcome-page.component';

const routes: Route[] = [
  {
    path: '',
    component: WelcomePageComponent,
  },
  // {
  //   path: 'about',
  //   // component: AboutComponent,
  //   loadComponent: () =>
  //     import('./about/about.component').then((mod) => mod.AboutComponent),
  // },
  // {
  //   path: 'dashboard',
  //   loadChildren: () =>
  //     import('./dashboard/routes').then(
  //       (mod) => mod.DASHBOARD_ROUTES
  //     ),
  // },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}

