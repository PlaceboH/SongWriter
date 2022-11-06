import { HttpClient, HttpClientModule } from '@angular/common/http';
import { enableProdMode, importProvidersFrom } from '@angular/core';
import { bootstrapApplication } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app/app-routing.module';
import { AppComponent } from './app/app.component';
import { TranslateLoader, TranslateModule } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { authInterceptorProviders } from './app/healper/auth-interceptor.service';
import { authErrorInterceptorProviders } from './app/healper/error-interceptor.service';
import { MaterialModule } from './app/material.module';
import { environment } from './environments/environment';

if (environment.production) {
  enableProdMode();
}

bootstrapApplication(AppComponent, {
  providers: [
    authInterceptorProviders,
    authErrorInterceptorProviders,
    importProvidersFrom(
      AppRoutingModule, 
      BrowserAnimationsModule,
      HttpClientModule,
      MaterialModule,
      TranslateModule.forRoot({
        loader: {
          provide: TranslateLoader,
          useFactory: httpTranslateLoader,
          deps: [HttpClient]
        }
      })
      )
   ],
});


export function httpTranslateLoader(http: HttpClient) {
  return new TranslateHttpLoader(http);
}