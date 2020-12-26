import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from './login/login.component';
import {RegisterComponent} from './register/register.component';
import {DashboardComponent} from './dashboard/dashboard.component';
import {AuthGuard} from './user/auth/auth.guard';
import {RegistrationComponent} from './dashboard/components/registration/registration.component';
import {SubjectsComponent} from './dashboard/components/subjects/subjects.component';
import {ProfileComponent} from './dashboard/components/profile/profile.component';
import {HomeComponent} from './dashboard/components/home/home.component';
import {UniversityComponent} from './dashboard/components/university/university.component';
import {GroupsComponent} from './dashboard/components/groups/groups.component';
import {UsersComponent} from './dashboard/components/users/users.component';
import {RequestsComponent} from './dashboard/components/requests/requests.component';
import {SettingsComponent} from './dashboard/components/settings/settings.component';
import {MyGroupsComponent} from './dashboard/components/my-groups/my-groups.component';
import {SubjectDetailsComponent} from './dashboard/components/subjects/subject-details/subject-details.component';
import {GroupDetailsComponent} from './dashboard/components/groups/group-details/group-details.component';
import {AddGroupComponent} from './dashboard/components/groups/add-group/add-group.component';
import {AddNewsComponent} from './dashboard/components/home/add-news/add-news.component';
import {EditProfileComponent} from './dashboard/components/profile/edit-profile/edit-profile.component';
import {ChangeRoleComponent} from './dashboard/components/users/change-role/change-role.component';
import {AdminGuard} from './user/auth/admin.guard';
import {StudentGuard} from './user/auth/student.guard';
import {TeacherGuard} from './user/auth/teacher.guard';
import {TeacherAdminGuard} from './user/auth/teacher-admin.guard';
import {AddSubjectComponent} from './dashboard/components/subjects/add-subject/add-subject.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: '', component: LoginComponent },
  { path: 'logout', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'dashboard', component: DashboardComponent, canActivate: [AuthGuard], children: [
      { path: '', component: HomeComponent, outlet: 'panel', pathMatch: 'full' },
      { path: 'news/add', component: AddNewsComponent, outlet: 'panel', canActivate: [AdminGuard] },
      { path: 'news/:id/edit', component: AddNewsComponent, outlet: 'panel', canActivate: [AdminGuard] },
      { path: 'registration', component: RegistrationComponent, outlet: 'panel', canActivate: [StudentGuard] },
      { path: 'subjects', component: SubjectsComponent, outlet: 'panel' },
      { path: 'subjects/add', component: AddSubjectComponent, outlet: 'panel', canActivate: [AdminGuard] },
      { path: 'subjects/:id', component: SubjectDetailsComponent, outlet: 'panel'},
      { path: 'my-groups', component: MyGroupsComponent, outlet: 'panel', canActivate: [TeacherGuard] },
      { path: 'profile', component: ProfileComponent, outlet: 'panel' },
      { path: 'profile/edit', component: EditProfileComponent, outlet: 'panel' },
      { path: 'university', component: UniversityComponent, outlet: 'panel', canActivate: [AdminGuard] },
      { path: 'groups', component: GroupsComponent, outlet: 'panel' },
      { path: 'groups/add', component: AddGroupComponent, outlet: 'panel', canActivate: [TeacherAdminGuard]},
      { path: 'groups/:id', component: GroupDetailsComponent, outlet: 'panel' },
      { path: 'groups/:id/edit', component: AddGroupComponent, outlet: 'panel', canActivate: [TeacherAdminGuard]},
      { path: 'users', component: UsersComponent, outlet: 'panel', canActivate: [AdminGuard] },
      { path: 'users/:username/role', component: ChangeRoleComponent, outlet: 'panel', canActivate: [AdminGuard] },
      { path: 'requests', component: RequestsComponent, outlet: 'panel', canActivate: [AdminGuard] },
      { path: 'settings', component: SettingsComponent, outlet: 'panel', canActivate: [AdminGuard] }
  ]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule { }
