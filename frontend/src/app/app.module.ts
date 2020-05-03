import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HeaderComponent} from './sections/header/header.component';
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

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
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
    EventsTabComponent
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
    MatTableModule
  ],
  providers: [httpInterceptorProviders],
  bootstrap: [AppComponent]
})
export class AppModule {
}
