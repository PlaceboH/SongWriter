import { Route } from "@angular/router";
import { AuthGuardService } from "../healper/auth-guard.service";
import { HomePageComponent } from "./home-page/home-page.component";
import { ManageMusicWorkComponent } from "./home-page/music-works-section/manage-music-work/manage-music-work.component";
import { LayoutComponent } from "./layout.component";
import { MusicWorksPageComponent } from "./music-works-page/music-works-page.component";
import { PostsPageComponent } from "./posts-page/posts-page.component";
import { UserDetailsComponent } from "./users-page/user-details/user-details.component";
import { UsersPageComponent } from "./users-page/users-page.component";

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
                path: 'users', 
                component: UsersPageComponent,
                canActivate: [AuthGuardService],
            },
            {
                path: 'user-details/:id',
                component: UserDetailsComponent,
                canActivate: [AuthGuardService],
            },
            { 
                path: 'music-works', 
                component: MusicWorksPageComponent,
                canActivate: [AuthGuardService],
            },
            { 
                path: 'posts', 
                component: PostsPageComponent,
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