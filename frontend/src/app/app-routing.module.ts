import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from './components/login/login.component';
import {LoggedInGuard} from './guards/logged-in.guard';
import {MessageComponent} from './components/message/message.component';
import {NotLoggedInGuard} from './guards/not-logged-in.guard';
import {AdminHomeComponent} from './components/admin-home/root/admin-home.component';
import {AdminGuard} from './guards/admin.guard';
// tslint:disable-next-line:max-line-length
import {UserDetailsContainerComponent} from './components/admin-home/admin-tabs/users-tab/user-details-container/root/user-details-container.component';
import {CreateUserContainerComponent} from './components/admin-home/admin-tabs/users-tab/create-user-container/root/create-user-container.component';
import {MyProfileContainerComponent} from './components/my-profile/root/my-profile-container.component';
import {RegistrationComponent} from './components/registration/registration.component';
import {EventDetailsUserViewComponent} from './components/event-details/event-details-user-view';
import {EventDetailsContainerComponent} from './components/admin-home/admin-tabs/events-tab/event-details-container/root/event-details-container.component';
import {CreateEventContainerComponent} from './components/admin-home/admin-tabs/events-tab/create-event-container/root/create-event-container.component';
import {FaqComponent} from './components/faq/faq.component';
import {TopTenEventsComponent} from './components/top-ten-events/top-ten-events.component';
import {HomeComponent} from './components/home/home.component';
import {NotAdminGuard} from './guards/not-admin.guard';
import {ResetPasswordComponent} from './components/reset-password/reset-password.component';
import {ResetPasswordAuthComponent} from './components/reset-password/reset-password-auth/reset-password-auth.component';
import {TicketPurchaseComponent} from './components/ticket-purchase/ticket-purchase.component';
import {SearchComponent} from './components/search/search.component';

const routes: Routes = [
  {path: '', redirectTo: 'home', pathMatch: 'full'},
  {path: 'home', canActivate: [NotAdminGuard], component: HomeComponent},
  {path: 'login', canActivate: [NotLoggedInGuard], component: LoginComponent},
  {path: 'message', canActivate: [LoggedInGuard], component: MessageComponent},
  {path: 'administration', canActivate: [AdminGuard], redirectTo: 'administration/users'},
  {path: 'administration/:tabId', canActivate: [AdminGuard], component: AdminHomeComponent},
  {path: 'user-details/:uc', canActivate: [AdminGuard], component: UserDetailsContainerComponent},
  {path: 'user-details/:uc/reset-password', canActivate: [AdminGuard], component: UserDetailsContainerComponent},
  {path: 'create-user', canActivate: [AdminGuard], component: CreateUserContainerComponent},
  {path: 'event-details/:ec', canActivate: [AdminGuard], component: EventDetailsContainerComponent},
  {path: 'my-profile', canActivate: [LoggedInGuard], redirectTo: 'my-profile/my-info'},
  {path: 'my-profile/:tabId', canActivate: [LoggedInGuard], component: MyProfileContainerComponent},
  {path: 'registration', canActivate: [NotLoggedInGuard], component: RegistrationComponent},
  {path: 'create-event', canActivate: [AdminGuard], component: CreateEventContainerComponent},
  {path: 'create-user', canActivate: [AdminGuard], component: CreateUserContainerComponent},
  {path: 'event-details/:eventCode', canActivate: [LoggedInGuard], component: EventDetailsUserViewComponent},
  {path: 'faq', canActivate: [], component: FaqComponent},
  {path: 'top-ten-events', component: TopTenEventsComponent},
  {path: 'search', canActivate: [], component: SearchComponent},
  {path: 'top-ten-events', component: TopTenEventsComponent},
  {path: 'reset-password', component: ResetPasswordComponent},
  {path: 'reset-password/:rc', component: ResetPasswordAuthComponent},
  {path: 'ticket-purchase', canActivate: [LoggedInGuard], component: TicketPurchaseComponent}];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
