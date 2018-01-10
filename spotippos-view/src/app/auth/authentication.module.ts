import { NgModule } from '@angular/core';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import {
  AUTH_SERVICE,
  PUBLIC_FALLBACK_PAGE_URI,
  PROTECTED_FALLBACK_PAGE_URI
 } from './tokens';

import { TokenStorage } from './token-storage.service';
import { AuthService } from './auth.service';
import { AuthInterceptor } from './auth.interceptor';
import { ProtectedGuard } from './protected.guard';

export function factory(authenticationService: AuthService) {
  return authenticationService;
}

@NgModule({
    providers: [
      TokenStorage,
      AuthService,
      { provide: PROTECTED_FALLBACK_PAGE_URI, useValue: '/' },
      { provide: PUBLIC_FALLBACK_PAGE_URI, useValue: '/login' },
      {
        provide: AUTH_SERVICE,
        deps: [ AuthService ],
        useFactory: factory
      },
      ProtectedGuard,
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
