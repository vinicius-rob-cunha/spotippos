import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { AuthenticationModule } from './auth/authentication.module';
import { AppRoutingModule } from './app-routing.module';

import { AppComponent } from './app.component';

import {
  MatIconModule,
  MatSidenavModule,
  MatToolbarModule
} from '@angular/material';
import { ProvinceComponent } from './province/province.component';

@NgModule({
  exports: [MatIconModule, MatSidenavModule, MatToolbarModule],
  declarations: [ProvinceComponent]
})
export class SpotipposMaterialModule { }

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    AuthenticationModule,
    SpotipposMaterialModule,
    AppRoutingModule
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
