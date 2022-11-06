import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { RouterModule } from "@angular/router";
import { TranslateModule } from '@ngx-translate/core';

@NgModule({
    imports: [
        TranslateModule,
    ],
    exports: [
        CommonModule,
        RouterModule,
        TranslateModule,
    ],
  })
  
  export class SharedModule {
  }