import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MainComponent } from './routes/main/main.component';
import { AuthComponent } from './routes/auth/auth.component';
import { PointFormComponent } from './components/point-form/point-form.component';
import {ReactiveFormsModule} from "@angular/forms";
import { RegisterComponent } from './routes/register/register.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {AuthInterceptorService} from "./services/auth-interceptor.service";
import {AuthGuardService} from "./services/auth-guard.service";
import { PointsAreaComponent } from './components/points-area/points-area.component';
import { PointTableComponent } from './components/point-table/point-table.component';

@NgModule({
  declarations: [
    AppComponent,
    MainComponent,
    AuthComponent,
    PointFormComponent,
    RegisterComponent,
    PointsAreaComponent,
    PointTableComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,

  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptorService, multi: true },
    AuthGuardService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
