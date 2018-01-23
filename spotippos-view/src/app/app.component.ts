import { Component } from '@angular/core';
import { UserService } from './auth';
import { UserData } from './auth';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {

  title = 'app';
  user: UserData;

  constructor(
    private userService: UserService
  ) { 
    this.userService.getUser().subscribe(user => {
      this.user = user;
    });
  }

}
