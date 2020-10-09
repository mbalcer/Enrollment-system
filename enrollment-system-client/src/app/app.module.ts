import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {LoginComponent} from './login/login.component';
import {FormsModule} from '@angular/forms';
import {AppRoutingModule} from './app-routing.module';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {HttpInterceptorService} from './http-interceptor.service';
import {RegisterComponent} from './register/register.component';
import {DashboardComponent} from './dashboard/dashboard.component';
import {MenuComponent} from './dashboard/menu/menu.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MaterialModule} from './material.module';
import {CleanUrlSerializer} from './clean-url-serializer';
import {UrlSerializer} from '@angular/router';
import {TopBarComponent} from './dashboard/top-bar/top-bar.component';
import {HomeComponent} from './dashboard/home/home.component';
import {ProfileComponent} from './dashboard/profile/profile.component';
import {SubjectsComponent} from './dashboard/subjects/subjects.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    DashboardComponent,
    MenuComponent,
    TopBarComponent,
    HomeComponent,
    ProfileComponent,
    SubjectsComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MaterialModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpInterceptorService,
      multi: true
    },
    {
      provide: UrlSerializer,
      useClass: CleanUrlSerializer
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
