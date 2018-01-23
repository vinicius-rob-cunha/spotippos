import { NgModule } from '@angular/core';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import {
  AUTH_SERVICE,
  PROTECTED_FALLBACK_PAGE_URI
 } from './tokens';

import { TokenStorage } from './token-storage.service';
import { AuthService } from './auth.service';
import { UserData, UserService } from './user.service';
import { AuthInterceptor } from './auth.interceptor';
import { AuthGuard } from './auth.guard';
import { Config } from '../constants';

export function factory(authenticationService: AuthService) {
  return authenticationService;
}

@NgModule({
    providers: [
      TokenStorage,
      AuthService,
      UserService,
      { provide: PROTECTED_FALLBACK_PAGE_URI, useValue: '/' },
      {
        provide: AUTH_SERVICE,
        deps: [ AuthService ],
        useFactory: factory
      },
      AuthGuard,
      AuthInterceptor,
      {
        provide: HTTP_INTERCEPTORS,
        useClass: AuthInterceptor,
        multi: true,
      }
    ]
})
export class AuthenticationModule {

}
