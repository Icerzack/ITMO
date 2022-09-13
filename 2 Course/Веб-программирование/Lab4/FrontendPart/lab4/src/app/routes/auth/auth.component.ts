import { Component, OnInit } from '@angular/core';
import {FormControl, Validators} from "@angular/forms";
import {AuthService} from "../../services/auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.css']
})
export class AuthComponent implements OnInit {
  username = new FormControl('', Validators.required);
  password = new FormControl('', Validators.required);

  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit(): void {
    if(this.authService.isAuthenticated())
      this.router.navigateByUrl("/");
  }

  login(){
    if (this.username.value && this.password.value) {
      console.debug("Submitting form");
      this.authService.doLogin(this.username.value, this.password.value, this.router);
    }
    console.log(`${this.username.value} ${this.password.value}`);
  }

}
