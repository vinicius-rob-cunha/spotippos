import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { RequestOptions  } from '@angular/http';
import { Observable, BehaviorSubject } from 'rxjs';
import { Injectable, Inject } from '@angular/core';
import { TokenStorage } from './token-storage.service';
import { Router } from '@angular/router';
import { Config } from '../constants';

export interface UserData {
  id: number;
  username: string;
  email: string;
}

/**
 * Essential service for authentication
 * @export
 * @interface UserService
 */
@Injectable()
export class UserService {

  private loggedUser: BehaviorSubject<UserData> = new BehaviorSubject<UserData>(null);
  private user: Observable<UserData> = this.loggedUser.asObservable();
    
  constructor() {
    this.loadUser();
  }

  private loadUser() {
    let userData = JSON.parse(localStorage.getItem("user_data"));
    this.loggedUser.next(userData);
  }

  public getUser(): Observable<UserData> {
    return this.user;
  }

  /**
   * Logout
   */
  public clear(): void {
    localStorage.removeItem('user_data');
    this.loggedUser.next(null);
  }

  /**
   * Save access data in the storage
   *
   * @private
   * @param {AccessData} data
   */
  public setUser(user: UserData) {
    localStorage.setItem("user_data", JSON.stringify(user));
    this.loggedUser.next(user);
  }

}
