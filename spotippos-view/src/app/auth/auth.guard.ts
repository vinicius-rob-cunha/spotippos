import { Injectable, Inject } from '@angular/core';
import {
  Router,
  CanActivate,
  CanActivateChild,
  ActivatedRouteSnapshot,
  RouterStateSnapshot
} from '@angular/router';
import { Observable } from 'rxjs/Observable';

import { map } from '../rxjs.util';

import { AuthService } from './auth.service';
import { AUTH_SERVICE } from './tokens';

/**
 * Guard, checks access token availability and allows or disallows access to page,
 * and redirects out
 *
 * usage: { path: 'test', component: TestComponent, canActivate: [ AuthGuard ] }
 *
 * @export
 *
 * @class ProtectedGuard
 *
 * @implements {CanActivate}
 * @implements {CanActivateChild}
 */
@Injectable()
export class AuthGuard implements CanActivate, CanActivateChild {

  constructor(
    @Inject(AUTH_SERVICE) private authService: AuthService,
    private router: Router
  ) {}

  /**
   * CanActivate handler
   *
   * @param {ActivatedRouteSnapshot} _route
   * @param {RouterStateSnapshot} state
   *
   * @returns {Observable<boolean>}
   */
  public canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {    
    if(!this.authService.isAuthorized()){
        this.authService.authorize();
        return false;
    }

    return true;
  }

  /**
   * CanActivateChild handler
   *
   * @param {ActivatedRouteSnapshot} route
   * @param {RouterStateSnapshot} state
   *
   * @returns {Observable<boolean>}
   */
  public canActivateChild(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): boolean {
    return this.canActivate(route, state);
  }

  /**
   * Navigate away from the app / path
   *
   * @private
   * @param {string} url
   */
  private navigate(url: string): void {
    if (url.startsWith('http')) {
      window.location.href = url;
    } else {
      this.router.navigateByUrl(url);
    }
  }

}
