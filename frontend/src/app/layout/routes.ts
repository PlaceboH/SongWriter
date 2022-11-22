import { Route } from "@angular/router";
import { AuthGuardService } from "../healper/auth-guard.service";
import { HomePageComponent } from "./home-page/home-page.component";

export const HOME_ROUTES: Route[] = [
    {
        path: '',
        component: HomePageComponent,
        canActivate: [AuthGuardService]
    },

    { 
        path: '**', 
        component: HomePageComponent,
        canActivate: [AuthGuardService]
    },
    {
        path: 'login',
        loadChildren: () =>
          import('../auth-page/routes').then((mod) => mod.AUTH_ROUTES),
    },
];