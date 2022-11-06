import { Route } from "@angular/router";
import { HomePageComponent } from "./home-page.component";

export const HOME_ROUTES: Route[] = [
    {
        path: '',
        component: HomePageComponent,
    },
    { 
        path: '**', 
        component: HomePageComponent,
    },
];