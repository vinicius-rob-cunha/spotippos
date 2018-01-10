import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ProvinceComponent } from './province/province.component';
import { ProtectedGuard } from './auth';

const routes: Routes = [
  { path: '', component: ProvinceComponent, canActivate: [ProtectedGuard] },
  { path: '**', component: ProvinceComponent, canActivate: [ProtectedGuard] }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
