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
import { EventSearchComponent } from './components/event-search/event-search.component';
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
import { CurrentNewsComponent } from './components/home/current-news/current-news.component';
import { TopEventsListComponent } from './components/top-ten-events/top-events-list/top-events-list.component';
import { ResetPasswordComponent} from './components/reset-password/reset-password.component';
import { ResetPasswordAuthComponent } from './components/reset-password/reset-password-auth/reset-password-auth.component';
import { TicketPurchaseComponent } from './components/ticket-purchase/ticket-purchase.component';
import {MatStepperModule} from '@angular/material/stepper';
import { ChooseTicketComponent } from './components/ticket-purchase/choose-ticket/choose-ticket.component';
import { PaymentMethodOverviewComponent } from './components/ticket-purchase/payment-method-overview/payment-method-overview.component';

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
    EventSearchComponent,
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
    CurrentNewsComponent,
    TopEventsListComponent,
    ResetPasswordComponent,
    ResetPasswordAuthComponent,
    TicketPurchaseComponent,
    ChooseTicketComponent,
    PaymentMethodOverviewComponent
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
    MatStepperModule
  ],
  providers: [httpInterceptorProviders, MatDatepickerModule, MatNativeDateModule, ChooseTicketComponent],
  bootstrap: [AppComponent]
})
export class AppModule {
}
