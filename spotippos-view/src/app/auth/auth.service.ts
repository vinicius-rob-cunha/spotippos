import {
  HttpClient,
  HttpEvent,
  HttpRequest,
  HttpHeaders,
  HttpErrorResponse
} from '@angular/common/http';
import { RequestOptions  } from '@angular/http';
import { Observable, BehaviorSubject } from 'rxjs';
import { Injectable, Inject } from '@angular/core';
import { TokenStorage } from './token-storage.service';
import { UserData, UserService } from './user.service';
import { Router } from '@angular/router';
import { Config } from '../constants';

interface AccessData {
  access_token: string;
  refresh_token: string;
  user_data: UserData;
}

/**
 * Essential service for authentication
 * @export
 * @interface AuthService
 */
@Injectable()
export class AuthService {
    
  constructor(
    private http: HttpClient,
    private router: Router,
    private tokenStorage: TokenStorage,
    private userService: UserService
  ) { }

  /**
   * Check, if user already authorized.
   * @description Should return Observable with true or false values
   * @returns {Observable<boolean>}
   * @memberOf AuthService
   */
  public isAuthorized(): Observable < boolean > {
    return this.getAccessToken()
               .map(token => !!token);
  }

  public authorize(){
    var state = this.newState();
          
    var authorizeUrl = `${Config.authServerUrl}authorize` +
      `?client_id=${Config.client_id}` +
      "&response_type=code" +
      `&redirect_uri=${Config.fallbackUrl}` +
      `&state=${state}`;

    window.location.href = authorizeUrl;
  }

  public handleAuth(authCode: String, authState: String) {
    if(localStorage.getItem('authState') != authState){
      return false;
    }

    localStorage.removeItem('authState');

    var tokenUrl = `${Config.authServerUrl}token` +
      `?grant_type=authorization_code` +
      `&redirect_uri=${Config.fallbackUrl}` +
      `&code=${authCode}` +
      `&scope=read write`;

    let headers = 
    new HttpHeaders()
      .append("Authorization", "Basic " + btoa(`${Config.client_id}:${Config.client_secret}`))
      .append("Content-Type", "application/x-www-form-urlencoded");

    this.http.post<AccessData>(tokenUrl, null, {headers: headers})
            .subscribe(res => {
              this.saveAccessData(res);
              this.saveUserData(res.user_data);

              this.router.navigateByUrl("/");
            });
  }

  /**
   * Get access token
   * @description Should return access token in Observable from e.g.
   * localStorage
   * @returns {Observable<string>}
   */
  public getAccessToken(): Observable < string > {
    return this.tokenStorage.getAccessToken();
  }

  /**
   * Function, that should perform refresh token verifyTokenRequest
   * @description Should be successfully completed so interceptor
   * can execute pending requests or retry original one
   * @returns {Observable<any>}
   */
  public refreshToken(): Observable < AccessData > {
    return this.tokenStorage
      .getRefreshToken()
      .switchMap((refreshToken: string) => {
        return this.doRefreshToken(refreshToken);
      })
      .do(this.saveAccessData.bind(this))
      .catch((err) => {
        this.logout();

        return Observable.throw(err);
      });
  }

  doRefreshToken(refreshToken: string) : Observable<any> {
    var tokenUrl = `${Config.authServerUrl}token` +
      `?grant_type=refresh_token` +
      `&refresh_token=${refreshToken}` +
      `&scope=read write`;

    let headers = new HttpHeaders()
        .append("Authorization", "Basic " + btoa(`${Config.client_id}:${Config.client_secret}`));

    return this.http.post(tokenUrl, null, {headers: headers});
  }

  /**
   * Function, checks response of failed request to determine,
   * whether token be refreshed or not.
   * @description Essentialy checks status
   * @param {Response} response
   * @returns {boolean}
   */
  public refreshShouldHappen(response: HttpErrorResponse): boolean {
    return response.status === 401
  }

  /**
   * Verify that outgoing request is refresh-token,
   * so interceptor won't intercept this request
   * @param {string} url
   * @returns {boolean}
   */
  public verifyTokenRequest(url: string): boolean {
    return url.endsWith('/refresh');
  }

  private newState(): String {
    var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    
    var state = "";
    for (var i = 0; i < 6; i++) {
      state += possible.charAt(Math.floor(Math.random() * possible.length));
    }

    localStorage.setItem('authState', state);
  
    return state;
  }

  /**
   * Logout
   */
  public logout(): void {
    this.tokenStorage.clear();
    this.userService.clear();

    let state = this.newState();
    let logoutUrl = `${Config.logoutUrl}` +
      `?client_id=${Config.client_id}` +
      "&response_type=code" +
      `&redirect_uri=${Config.fallbackUrl}` +
      `&state=${state}`;

    window.location.href = logoutUrl;
  }

  /**
   * Save access data in the storage
   *
   * @private
   * @param {AccessData} data
   */
  private saveAccessData({ access_token, refresh_token }: AccessData) {
    this.tokenStorage.setAccessToken(access_token)
                     .setRefreshToken(refresh_token);
  }

  /**
   * Save access data in the storage
   *
   * @private
   * @param {AccessData} data
   */
  private saveUserData(user: UserData) {
    this.userService.setUser(user);
  }

}
