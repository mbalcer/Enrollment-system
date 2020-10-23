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
import {HomeComponent} from './dashboard/components/home/home.component';
import {ProfileComponent} from './dashboard/components/profile/profile.component';
import {SubjectsComponent} from './dashboard/components/subjects/subjects.component';
import {RegistrationComponent} from './dashboard/components/registration/registration.component';
import {OverlayModule} from '@angular/cdk/overlay';
import {UniversityComponent} from './dashboard/components/university/university.component';
import {GroupsComponent} from './dashboard/components/groups/groups.component';

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
    SubjectsComponent,
    RegistrationComponent,
    UniversityComponent,
    GroupsComponent
  ],
    imports: [
        BrowserModule,
        FormsModule,
        AppRoutingModule,
        HttpClientModule,
        BrowserAnimationsModule,
        MaterialModule,
        OverlayModule
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
