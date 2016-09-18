import { NgModule } from '@angular/core';
import { HttpModule } from '@angular/http';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { DrunkrService, UserService } from './API/api.service';

import { routing } from './app.routing';
import { HomeComponent } from './components/home.component';
import { LoginComponent } from './components/login.component';
import { SignupComponent } from './components/signup.component';

@NgModule({
  imports: [BrowserModule, HttpModule, routing, FormsModule],
  declarations: [AppComponent, HomeComponent, LoginComponent, SignupComponent],
  bootstrap: [AppComponent],
  providers: [DrunkrService, UserService]
})
export class AppModule { }