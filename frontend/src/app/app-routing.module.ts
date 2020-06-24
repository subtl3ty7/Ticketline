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
import {NewsDetailsComponent} from './components/news-details/news-details.component';
import {NewsListComponent} from './components/news-list/news-list.component';
import {TicketPurchaseComponent} from './components/ticket-purchase/ticket-purchase.component';
import {SearchComponent} from './components/search/search.component';
import {ArtistAdvancedComponent} from './components/search/artist/artist-advanced/artist-advanced.component';
import {LocationAdvancedComponent} from './components/search/location/location-advanced/location-advanced.component';
import {ArtistEventComponent} from './components/search/artist/artist-event/artist-event.component';
import {LocationShowComponent} from './components/search/location/location-show/location-show.component';
import {ResetPasswordAdminContainerComponent} from './components/admin-home/admin-tabs/users-tab/reset-password-admin-container/root/reset-password-admin-container.component';
import {MyInvoiceComponent} from './components/my-profile/my-profile-tabs/my-tickets-tab/my-invoice/my-invoice.component';
import {MerchandiseComponent} from './components/merchandise/merchandise.component';
import {ProductDetailsComponent} from './components/merchandise/product-details/product-details.component';
import {NewsDetailsContainerComponent} from './components/admin-home/admin-tabs/news-tab/news-details-container/root/news-details-container.component';
import {CreateNewsContainerComponent} from './components/admin-home/admin-tabs/news-tab/create-news-container/root/create-news-container.component';
import {EventAdvancedComponent} from './components/search/event/event-advanced/event-advanced.component';
import {MyInvoicesTabComponent} from './components/my-profile/my-profile-tabs/my-invoices-tab/my-invoices-tab.component';
import {InvoiceDetailsComponent} from './components/my-profile/my-profile-tabs/my-invoices-tab/invoice-details/invoice-details.component';
import {MerchandisePurchaseComponent} from './components/merchandise/merchandise-purchase/merchandise-purchase.component';

const routes: Routes = [
  {path: '', redirectTo: 'home', pathMatch: 'full'},
  {path: 'home', canActivate: [NotAdminGuard], component: HomeComponent},
  {path: 'login', canActivate: [NotLoggedInGuard], component: LoginComponent},
  {path: 'message', canActivate: [LoggedInGuard], component: MessageComponent},
  {path: 'administration', canActivate: [AdminGuard], redirectTo: 'administration/users'},
  {path: 'administration/:tabId', canActivate: [AdminGuard], component: AdminHomeComponent},
  {path: 'user-details/:uc', canActivate: [AdminGuard], component: UserDetailsContainerComponent},
  {path: 'user-details/:uc/reset-password', canActivate: [AdminGuard], component: ResetPasswordAdminContainerComponent},
  {path: 'create-user', canActivate: [AdminGuard], component: CreateUserContainerComponent},
  {path: 'event-details/:ec', canActivate: [AdminGuard], component: EventDetailsContainerComponent},
  {path: 'news-details/:nc', canActivate: [AdminGuard], component: NewsDetailsContainerComponent},
  {path: 'my-profile', canActivate: [LoggedInGuard], redirectTo: 'my-profile/my-info'},
  {path: 'my-profile/:tabId', canActivate: [LoggedInGuard], component: MyProfileContainerComponent},
  {path: 'registration', canActivate: [NotLoggedInGuard], component: RegistrationComponent},
  {path: 'create-news', canActivate: [AdminGuard], component: CreateNewsContainerComponent},
  {path: 'create-event', canActivate: [AdminGuard], component: CreateEventContainerComponent},
  {path: 'create-user', canActivate: [AdminGuard], component: CreateUserContainerComponent},
  {path: 'events/:eventCode', canActivate: [LoggedInGuard], component: EventDetailsUserViewComponent},
  {path: 'faq', canActivate: [], component: FaqComponent},
  {path: 'top-ten-events', component: TopTenEventsComponent},
  {path: 'search', canActivate: [], component: SearchComponent},
  {path: 'top-ten-events', component: TopTenEventsComponent},
  {path: 'reset-password', component: ResetPasswordComponent},
  {path: 'reset-password/:rc', component: ResetPasswordAuthComponent},
  {path: 'news/:nc', canActivate: [LoggedInGuard], component: NewsDetailsComponent},
  {path: 'news-list', canActivate: [], component: NewsListComponent},
  {path: 'ticket-purchase/:showId', canActivate: [LoggedInGuard], component: TicketPurchaseComponent},
  {path: 'search/artist-events', component: ArtistEventComponent},
  {path: 'search/artist-advanced', component: ArtistAdvancedComponent},
  {path: 'search/event-advanced', component: EventAdvancedComponent},
  {path: 'search/location-advanced', component: LocationAdvancedComponent},
  {path: 'search/location-shows', component: LocationShowComponent},
  {path: 'my-profile/my-tickets/my-invoice', component: MyInvoiceComponent},
  {path: 'my-profile/my-invoices', component: MyInvoicesTabComponent},
  {path: 'my-profile/my-invoices/invoice-details', component: InvoiceDetailsComponent},
  {path: 'merchandise', canActivate: [NotAdminGuard], component: MerchandiseComponent},
  {path: 'merchandise/:merchandiseProductCode', canActivate: [LoggedInGuard],  component: ProductDetailsComponent},
  {path: 'merchandise-purchase/:merchandiseProductCode', canActivate: [LoggedInGuard], component: MerchandisePurchaseComponent}
  ];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
