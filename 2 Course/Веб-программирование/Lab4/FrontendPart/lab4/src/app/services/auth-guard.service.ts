import { Injectable } from '@angular/core';
import {AuthService} from "./auth.service";
import {CanActivate, Router} from "@angular/router";

@Injectable()
export class AuthGuardService{
  constructor(public auth: AuthService, public router: Router) {}

  canActivate(): boolean {
    if (this.auth.isAuthenticated()) {
      console.log("Access allowed");
      return true;
    }
    console.log("Access denied: not authorized");
    this.router.navigate(['/login']);
    return false;
  }
}
