import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {FooterComponent} from './sections/footer/footer.component';
import {LoginComponent} from './components/login/login.component';
import {MessageComponent} from './components/message/message.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {httpInterceptorProviders} from './interceptors';
import { AdminHomeComponent } from './components/admin-home/root/admin-home.component';
import { MatSidenavModule } from '@angular/material/sidenav';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatButtonModule } from '@angular/material/button';
import { AdminSideMenuComponent } from './components/admin-home/admin-side-menu/admin-side-menu.component';
import { UsersTabComponent } from './components/admin-home/admin-tabs/users-tab/users-tab.component';
import { AdminTabsComponent } from './components/admin-home/admin-tabs/admin-tabs.component';
import { EventsTabComponent } from './components/admin-home/admin-tabs/events-tab/events-tab.component';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatTableModule} from '@angular/material/table';
import {ContentComponent} from './sections/content/content.component';
import { AdminHeaderComponent } from './sections/header/admin-header/admin-header.component';
import { CustomerHeaderComponent } from './sections/header/customer-header/customer-header.component';
import {MatInputModule} from '@angular/material/input';
import {MatSortModule} from '@angular/material/sort';
import { UserDetailsContainerComponent } from './components/admin-home/admin-tabs/users-tab/user-details-container/root/user-details-container.component';
import { UserDetailsComponent } from './components/admin-home/admin-tabs/users-tab/user-details-container/user-details/user-details.component';
import { UserInfoComponent } from './components/admin-home/admin-tabs/users-tab/user-details-container/user-details/user-info/user-info.component';
import { CreateUserContainerComponent } from './components/admin-home/admin-tabs/users-tab/create-user-container/root/create-user-container.component';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {FlexLayoutModule, FlexModule} from '@angular/flex-layout';
// tslint:disable-next-line:max-line-length
import { CreateUserComponent } from './components/admin-home/admin-tabs/users-tab/create-user-container/create-user/create-user.component';
import { CreateUserFormComponent } from './components/admin-home/admin-tabs/users-tab/create-user-container/create-user/create-user-form/create-user-form.component';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatNativeDateModule} from '@angular/material/core';
import { MustMatchDirective } from './utils/must-match.directive';
import { GuestHeaderComponent } from './sections/header/guest-header/guest-header.component';
import { MyProfileContainerComponent } from './components/my-profile/root/my-profile-container.component';
import { MyProfileSideMenuComponent } from './components/my-profile/my-profile-side-menu/my-profile-side-menu.component';
import { ProfileInfoComponent } from './components/my-profile/my-profile-tabs/my-info-tab/profile-info/profile-info.component';
import { MyProfileTabsComponent } from './components/my-profile/my-profile-tabs/my-profile-tabs.component';
import { MyInfoTabComponent } from './components/my-profile/my-profile-tabs/my-info-tab/my-info-tab.component';
import { EditProfileComponent } from './components/my-profile/my-profile-tabs/my-info-tab/edit-profile/edit-profile.component';
import { DeleteAccountComponent } from './components/my-profile/my-profile-tabs/my-info-tab/delete-account/delete-account.component';
import { TopEventsComponent} from './components/home/top-events/top-events.component';
import { RegistrationComponent } from './components/registration/registration.component';
import { EventDetailsUserViewComponent } from './components/event-details/event-details-user-view';
import {MatPaginatorModule} from '@angular/material/paginator';
import { EventDetailsContainerComponent } from './components/admin-home/admin-tabs/events-tab/event-details-container/root/event-details-container.component';
import { EventDetailsComponent } from './components/admin-home/admin-tabs/events-tab/event-details-container/event-details/event-details.component';
import { EventInfoComponent } from './components/admin-home/admin-tabs/events-tab/event-details-container/event-details/event-info/event-info.component';
import { ShowsTableComponent } from './components/admin-home/admin-tabs/events-tab/event-details-container/event-details/event-info/shows-table/shows-table.component';
import { CreateEventContainerComponent } from './components/admin-home/admin-tabs/events-tab/create-event-container/root/create-event-container.component';
import { CreateEventComponent } from './components/admin-home/admin-tabs/events-tab/create-event-container/create-event/create-event.component';
import { FaqComponent } from './components/faq/faq.component';
import { HomeComponent } from './components/home/home.component';
import { TopTenEventsComponent } from './components/top-ten-events/top-ten-events.component';
import { HeaderComponent } from './sections/header/header.component';
import { TopEventsListComponent } from './components/top-ten-events/top-events-list/top-events-list.component';
import { SearchComponent } from './components/search/search.component';
import { ArtistComponent } from './components/search/artist/artist.component';
import { EventComponent } from './components/search/event/event.component';
import { LocationComponent } from './components/search/location/location.component';
import { ShowComponent } from './components/search/show/show.component';
import { ResetPasswordComponent} from './components/reset-password/reset-password.component';
import { ResetPasswordAuthComponent } from './components/reset-password/reset-password-auth/reset-password-auth.component';
import { TicketPurchaseComponent } from './components/ticket-purchase/ticket-purchase.component';
import {MatStepperModule} from '@angular/material/stepper';
import { ChooseTicketComponent } from './components/ticket-purchase/choose-ticket/choose-ticket.component';
import { PaymentMethodOverviewComponent } from './components/ticket-purchase/payment-method-overview/payment-method-overview.component';
import {MatSelectModule} from '@angular/material/select';
import { ChartComponent } from './components/top-ten-events/chart/chart.component';
import { LatestNewsComponent } from './components/home/latest-news/latest-news.component';
import { NewsDetailsComponent } from './components/news-details/news-details.component';
import { NewsDetailsInnerComponent } from './components/news-details/news-details-inner/news-details-inner.component';
import { ErrorNotificationComponent } from './sections/error-notification/error-notification.component';
import { NewsListComponent } from './components/news-list/news-list.component';
import { NewsListInnerComponent } from './components/news-list/news-list-inner/news-list-inner.component';
import { SearchShared} from './components/search/search-shared';
import { ArtistAdvancedComponent } from './components/search/artist/artist-advanced/artist-advanced.component';
import { LocationAdvancedComponent } from './components/search/location/location-advanced/location-advanced.component';
import { DropdownCountryComponent } from './components/search/location/location-advanced/dropdown-country/dropdown-country.component';
import { ArtistEventComponent } from './components/search/artist/artist-event/artist-event.component';
import { LocationShowComponent } from './components/search/location/location-show/location-show.component';
import { ChangePasswordComponent } from './components/my-profile/my-profile-tabs/my-info-tab/change-password/change-password.component';
import { ResetPasswordAdminContainerComponent } from './components/admin-home/admin-tabs/users-tab/reset-password-admin-container/root/reset-password-admin-container.component';
import { ResetPasswordAdminComponent } from './components/admin-home/admin-tabs/users-tab/reset-password-admin-container/reset-password-admin/reset-password-admin.component';
import { ResetPasswordAdminFormComponent } from './components/admin-home/admin-tabs/users-tab/reset-password-admin-container/reset-password-admin/reset-password-admin-form/reset-password-admin-form.component';
import { MyTicketsTabComponent } from './components/my-profile/my-profile-tabs/my-tickets-tab/my-tickets-tab.component';
import { PaymentDoneComponent } from './components/ticket-purchase/payment-done/payment-done.component';
import { MyInvoiceComponent } from './components/my-profile/my-profile-tabs/my-tickets-tab/my-invoice/my-invoice.component';
import {EventAdvancedComponent} from './components/search/event/event-advanced/event-advanced.component';
import { SpinnerComponent } from './components/spinner/spinner.component';
import { MerchandiseComponent } from './components/merchandise/merchandise.component';
import { ProductDetailsComponent } from './components/merchandise/product-details/product-details.component';
import { NewsTabComponent } from './components/admin-home/admin-tabs/news-tab/news-tab.component';
import { NewsDetailsContainerComponent } from './components/admin-home/admin-tabs/news-tab/news-details-container/root/news-details-container.component';
import { AdminNewsDetailsComponent } from './components/admin-home/admin-tabs/news-tab/news-details-container/admin-news-details/admin-news-details.component';
import { NewsInfoComponent } from './components/admin-home/admin-tabs/news-tab/news-details-container/admin-news-details/news-info/news-info.component';
import { NewsDetailsDialogComponent } from './components/admin-home/admin-tabs/news-tab/news-details-container/admin-news-details/news-info/news-details-dialog/news-details-dialog.component';
import { CreateNewsContainerComponent } from './components/admin-home/admin-tabs/news-tab/create-news-container/root/create-news-container.component';
import { CreateNewsComponent } from './components/admin-home/admin-tabs/news-tab/create-news-container/create-news/create-news.component';
import { CreateNewsFormComponent } from './components/admin-home/admin-tabs/news-tab/create-news-container/create-news/create-news-form/create-news-form.component';
import { CreateShowsComponent } from './components/admin-home/admin-tabs/events-tab/create-event-container/create-event/create-event-form/create-shows/create-shows.component';
import { CreateEventFormComponent } from './components/admin-home/admin-tabs/events-tab/create-event-container/create-event/create-event-form/create-event-form.component';
import { CreateArtistsComponent } from './components/admin-home/admin-tabs/events-tab/create-event-container/create-event/create-event-form/create-artists/create-artists.component';
import {MatDialogModule} from '@angular/material/dialog';
import { SeatingPlanComponent } from './components/ticket-purchase/choose-ticket/seating-plan/seating-plan.component';
import { MyInvoicesTabComponent } from './components/my-profile/my-profile-tabs/my-invoices-tab/my-invoices-tab.component';
import { InvoiceDetailsComponent } from './components/my-profile/my-profile-tabs/my-invoices-tab/invoice-details/invoice-details.component';
import { MerchandisePurchaseComponent } from './components/merchandise/merchandise-purchase/merchandise-purchase.component';

@NgModule({
  declarations: [
    AppComponent,
    FooterComponent,
    LoginComponent,
    MessageComponent,
    AdminHomeComponent,
    AdminSideMenuComponent,
    UsersTabComponent,
    AdminTabsComponent,
    ContentComponent,
    EventsTabComponent,
    AdminHeaderComponent,
    CustomerHeaderComponent,
    MustMatchDirective,
    UserDetailsContainerComponent,
    UserDetailsComponent,
    UserInfoComponent,
    CreateUserContainerComponent,
    CreateUserComponent,
    CreateUserFormComponent,
    GuestHeaderComponent,
    MyProfileContainerComponent,
    MyProfileSideMenuComponent,
    ProfileInfoComponent,
    MyProfileTabsComponent,
    MyInfoTabComponent,
    EditProfileComponent,
    DeleteAccountComponent,
    TopEventsComponent,
    RegistrationComponent,
    GuestHeaderComponent,
    EventDetailsUserViewComponent,
    EventDetailsContainerComponent,
    EventDetailsComponent,
    EventInfoComponent,
    ShowsTableComponent,
    CreateEventContainerComponent,
    CreateEventComponent,
    FaqComponent,
    HomeComponent,
    TopTenEventsComponent,
    HeaderComponent,
    TopEventsListComponent,
    ResetPasswordComponent,
    ResetPasswordAuthComponent,
    TicketPurchaseComponent,
    ChooseTicketComponent,
    PaymentMethodOverviewComponent,
    ChartComponent,
    LatestNewsComponent,
    NewsDetailsComponent,
    NewsDetailsInnerComponent,
    ErrorNotificationComponent,
    NewsListComponent,
    NewsListInnerComponent,
    TopEventsListComponent,
    SearchComponent,
    ArtistComponent,
    EventComponent,
    LocationComponent,
    ShowComponent,
    ArtistAdvancedComponent,
    LocationAdvancedComponent,
    DropdownCountryComponent,
    ArtistEventComponent,
    LocationShowComponent,
    DropdownCountryComponent,
    ChangePasswordComponent,
    ResetPasswordAdminContainerComponent,
    ResetPasswordAdminComponent,
    ResetPasswordAdminFormComponent,
    MyTicketsTabComponent,
    PaymentDoneComponent,
    MyInvoiceComponent,
    MerchandiseComponent,
    ProductDetailsComponent,
    EventAdvancedComponent,
    SpinnerComponent,
    MyInvoiceComponent,
    MerchandiseComponent,
    ProductDetailsComponent,
    NewsTabComponent,
    NewsDetailsContainerComponent,
    AdminNewsDetailsComponent,
    NewsInfoComponent,
    NewsDetailsDialogComponent,
    CreateNewsContainerComponent,
    CreateNewsComponent,
    CreateNewsFormComponent,
    CreateShowsComponent,
    CreateEventFormComponent,
    CreateArtistsComponent,
    SeatingPlanComponent,
    MyInvoicesTabComponent,
    InvoiceDetailsComponent,
    MerchandisePurchaseComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    NgbModule,
    FormsModule,
    NoopAnimationsModule,
    MatToolbarModule,
    MatSidenavModule,
    MatListModule,
    MatButtonModule,
    MatIconModule,
    MatFormFieldModule,
    MatTableModule,
    MatInputModule,
    MatSortModule,
    MatCheckboxModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatPaginatorModule,
    FlexModule,
    MatStepperModule,
    MatSelectModule,
    MatDialogModule
  ],
  providers: [httpInterceptorProviders, MatDatepickerModule, MatNativeDateModule, ChooseTicketComponent],
  bootstrap: [AppComponent]
})
export class AppModule {
}
