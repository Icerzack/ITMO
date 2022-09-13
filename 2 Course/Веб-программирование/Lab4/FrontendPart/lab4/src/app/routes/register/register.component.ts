import { Component, OnInit } from '@angular/core';
import {FormControl, Validators} from "@angular/forms";
import {AuthService} from "../../services/auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  username = new FormControl('', Validators.required);
  password = new FormControl('', Validators.required);

  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit(): void {
    if(this.authService.isAuthenticated())
      this.router.navigateByUrl("/");
  }

  register(){
    console.log(`${this.username.value} ${this.password.value}`);

    if (this.username.value && this.password.value) {
      console.log('Sending form...');
      this.authService.doRegister(this.username.value, this.password.value, this.router);
    }
  }
}
