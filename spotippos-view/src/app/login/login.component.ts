import { Component, OnInit } from '@angular/core';;
import { ActivatedRoute } from '@angular/router';
import { AuthService } from '../auth';
import 'rxjs/add/operator/filter';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  constructor(
    private route: ActivatedRoute,
    private authService: AuthService
  ) { }

  ngOnInit() {
    this.route.queryParams
      .subscribe(params => {
        this.authService.handleAuth(params.code, params.state);
      });
  }

}
