import { Route } from "@angular/router";
import { AuthGuardService } from "../healper/auth-guard.service";
import { HomePageComponent } from "./home-page/home-page.component";
import { ManageMusicWorkComponent } from "./home-page/music-works-section/manage-music-work/manage-music-work.component";
import { LayoutComponent } from "./layout.component";

export const HOME_ROUTES: Route[] = [
    { 
        path: '', 
        component: LayoutComponent,
        children: [
            { 
                path: 'profile', 
                component: HomePageComponent,
                canActivate: [AuthGuardService],
            },
            { 
                path: 'managework/:id', 
                component: ManageMusicWorkComponent,
                canActivate: [AuthGuardService],
            },
        ],
        canActivate: [AuthGuardService],
    },
    {
        path: 'login',
        loadChildren: () =>
          import('../auth-page/routes').then((mod) => mod.AUTH_ROUTES),
    },
    { 
        path: '**', 
        component: LayoutComponent,
        canActivate: [AuthGuardService]
    },
];