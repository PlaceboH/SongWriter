import { Component, OnInit } from "@angular/core";
import { MaterialModule } from "src/app/material.module";
import { Router, RouterModule } from "@angular/router";
import { SharedModule } from "src/app/shared/shared.module";
import { TokenStorageService } from "src/app/auth-page/token-storage.service";
import { TranslateService } from "@ngx-translate/core";
import { User } from "src/app/shared/models/User";
import { UserService } from "src/app/shared/services/user.service";

@Component({
    standalone: true,
    imports: [SharedModule, MaterialModule],
    selector: 'app-navigation',
    templateUrl: './navigation.component.html',
    styleUrls: ['./navigation.component.scss']
  })
  export class NavigationComponent implements OnInit {
  
    isLoggedIn = false;
    isDataLoaded = false;
    user!: User;
  
    constructor(private tokenService: TokenStorageService,
                private userService: UserService,
                private router: Router,
                public translate: TranslateService) {}
  
    ngOnInit(): void {
      this.isLoggedIn = !!this.tokenService.getToken();
  
      if(this.isLoggedIn) {
        this.userService.getCurrentUser()
          .subscribe(data => {
            this.user = data;
            this.isDataLoaded = true;
          })
      }
    }
  
    logout(): void {
      this.tokenService.logOut();
      this.router.navigate(['/auth/login']);
    }
  
  }
  