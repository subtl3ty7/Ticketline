import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {GuestHomeComponent} from './components/guest-home/guest-home.component';
import {LoginComponent} from './components/login/login.component';
import {LoggedInGuard} from './guards/logged-in.guard';
import {MessageComponent} from './components/message/message.component';
import {NotLoggedInGuard} from './guards/not-logged-in.guard';
import {AdminHomeComponent} from './components/admin-home/root/admin-home.component';
import {AdminGuard} from './guards/admin.guard';
import {CustomerHomeComponent} from './components/customer-home/customer-home.component';
import {UserDetailsContainerComponent} from './components/admin-home/user-details-container/root/user-details-container.component';
import {CreateUserContainerComponent} from './components/admin-home/create-user-container/root/create-user-container.component';
import {MyProfileContainerComponent} from './components/my-profile/root/my-profile-container.component';

const routes: Routes = [
  {path: '', canActivate: [NotLoggedInGuard], component: GuestHomeComponent},
  {path: 'home', canActivate: [LoggedInGuard], component: CustomerHomeComponent},
  {path: 'login', canActivate: [NotLoggedInGuard], component: LoginComponent},
  {path: 'message', canActivate: [LoggedInGuard], component: MessageComponent},
  {path: 'administration', canActivate: [AdminGuard], component: AdminHomeComponent},
  {path: 'administration/:tabId', canActivate: [AdminGuard], component: AdminHomeComponent},
  {path: 'user-details/:uc', canActivate: [AdminGuard], component: UserDetailsContainerComponent},
  {path: 'user-details/:uc/reset-password', canActivate: [AdminGuard], component: UserDetailsContainerComponent},
  {path: 'create-user', canActivate: [AdminGuard], component: CreateUserContainerComponent},
  {path: 'my-profile', canActivate: [LoggedInGuard], component: MyProfileContainerComponent},
  {path: 'my-profile/:tabId', canActivate: [LoggedInGuard], component: MyProfileContainerComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
