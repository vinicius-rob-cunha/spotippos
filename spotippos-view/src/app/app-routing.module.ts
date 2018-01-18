import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ProvinceComponent } from './province/province.component';
import { LoginComponent } from './login/login.component';
import { AuthGuard } from './auth';

const routes: Routes = [
  { path: 'auth', component: LoginComponent },
  { path: '', component: ProvinceComponent, canActivate: [AuthGuard] },
  { path: '**', component: ProvinceComponent, canActivate: [AuthGuard] }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
