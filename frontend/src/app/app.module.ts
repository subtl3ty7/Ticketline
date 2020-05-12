import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {FooterComponent} from './sections/footer/footer.component';
import {GuestHomeComponent} from './components/guest-home/guest-home.component';
import {LoginComponent} from './components/login/login.component';
import {MessageComponent} from './components/message/message.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {httpInterceptorProviders} from './interceptors';
import { AdminHomeComponent } from './components/admin-home/root/admin-home.component';
import { CustomerHomeComponent } from './components/customer-home/customer-home.component';
import { MatSidenavModule } from '@angular/material/sidenav';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatButtonModule } from '@angular/material/button';
import { AdminSideMenuComponent } from './components/admin-home/admin-side-menu/admin-side-menu.component';
import { UsersTabComponent } from './components/admin-home/users-tab/users-tab.component';
import { AdminTabsComponent } from './components/admin-home/admin-tabs/admin-tabs.component';
import { EventsTabComponent } from './components/admin-home/events-tab/events-tab.component';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatTableModule} from '@angular/material/table';
import {ContentComponent} from './sections/content/content.component';
import { AdminHeaderComponent } from './sections/header/admin-header/admin-header.component';
import { CustomerHeaderComponent } from './sections/header/customer-header/customer-header.component';
import {MatInputModule} from '@angular/material/input';
import {MatSortModule} from '@angular/material/sort';
import { UserDetailsContainerComponent } from './components/admin-home/user-details-container/root/user-details-container.component';
import { UserDetailsComponent } from './components/admin-home/user-details-container/user-details/user-details.component';
import { UserInfoComponent } from './components/admin-home/user-details-container/user-details/user-info/user-info.component';
import { CreateUserContainerComponent } from './components/admin-home/create-user-container/root/create-user-container.component';
import {MatCheckboxModule} from '@angular/material/checkbox';
// tslint:disable-next-line:max-line-length
import { ResetPasswordComponent } from './components/admin-home/user-details-container/user-details/reset-password/reset-password.component';
import { CreateUserComponent } from './components/admin-home/create-user-container/create-user/create-user.component';
import { CreateUserFormComponent } from './components/admin-home/create-user-container/create-user/create-user-form/create-user-form.component';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatNativeDateModule} from '@angular/material/core';
import { MustMatchDirective } from './utils/must-match.directive';
import { GuestHeaderComponent } from './sections/header/guest-header/guest-header.component';
import { TopEventsComponent } from './components/guest-home/top-events/top-events.component';
import { RegistrationComponent } from './components/registration/registration.component';


import { EventSearchComponent } from './components/event-search/event-search.component';

@NgModule({
  declarations: [
    AppComponent,
    FooterComponent,
    GuestHomeComponent,
    LoginComponent,
    MessageComponent,
    AdminHomeComponent,
    CustomerHomeComponent,
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
    ResetPasswordComponent,
    CreateUserComponent,
    CreateUserFormComponent,
    GuestHeaderComponent,
    TopEventsComponent
    RegistrationComponent,
    GuestHeaderComponent,
    EventSearchComponent,
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
    MatNativeDateModule
  ],
  providers: [httpInterceptorProviders, MatDatepickerModule, MatNativeDateModule],
  bootstrap: [AppComponent]
})
export class AppModule {
}
