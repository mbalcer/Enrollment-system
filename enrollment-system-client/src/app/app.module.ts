import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {LoginComponent} from './login/login.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
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
import {UsersComponent} from './dashboard/components/users/users.component';
import {RequestsComponent} from './dashboard/components/requests/requests.component';
import {SettingsComponent} from './dashboard/components/settings/settings.component';
import {MyGroupsComponent} from './dashboard/components/my-groups/my-groups.component';
import {SubjectDetailsComponent} from './dashboard/components/subjects/subject-details/subject-details.component';
import {GroupDetailsComponent} from './dashboard/components/groups/group-details/group-details.component';
import {AddGroupComponent} from './dashboard/components/groups/add-group/add-group.component';
import {JWT_OPTIONS, JwtHelperService} from '@auth0/angular-jwt';
import {FieldOfStudyPipe} from './pipe/field-of-study.pipe';
import {ListFieldsOfStudyPipe} from './pipe/list-fields-of-study.pipe';
import {TimeTablePipe} from './pipe/time-table.pipe';
import {NgxMatDatetimePickerModule, NgxMatNativeDateModule, NgxMatTimepickerModule} from '@angular-material-components/datetime-picker';
import {AppointmentPipe} from './pipe/appointment.pipe';
import {AddNewsComponent} from './dashboard/components/home/add-news/add-news.component';

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
    GroupsComponent,
    UsersComponent,
    RequestsComponent,
    SettingsComponent,
    MyGroupsComponent,
    SubjectDetailsComponent,
    GroupDetailsComponent,
    AddGroupComponent,
    AddNewsComponent,
    FieldOfStudyPipe,
    ListFieldsOfStudyPipe,
    TimeTablePipe,
    AppointmentPipe
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MaterialModule,
    OverlayModule,
    ReactiveFormsModule,
    NgxMatNativeDateModule,
    NgxMatDatetimePickerModule,
    NgxMatTimepickerModule
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
    },
    { provide: JWT_OPTIONS, useValue: JWT_OPTIONS },
    JwtHelperService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
