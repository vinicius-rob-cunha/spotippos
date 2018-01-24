import { Component } from '@angular/core';
import { UserService, UserData, AuthService } from './auth';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {

  title = 'app';
  user: UserData;

  constructor(
    private userService: UserService,
    private authService: AuthService
  ) { 
    this.userService.getUser().subscribe(user => {
      this.user = user;
    });
  }

  logout(){
    //this.authService.logout()
    this.authService
        .refreshToken()
        .subscribe(
          () => {
            console.log("SUCCESS");
          },
          () => {
            console.log("FAILURE");
          }
        );
  }

}
