import { Injectable } from '@angular/core';
import { UserToken } from "../models/user-token";
import { HttpClient } from "@angular/common/http";
import { shareReplay } from "rxjs";
import { environment } from "src/environments/environment";
import {Router} from "@angular/router";
import {PointService} from "./point.service";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient, private pointService: PointService) {}

  getLogin(){
    let tk: string | null = localStorage.getItem('token');

  }

  doLogin(user: String, pass: String, router: Router){
    const l = this.login(user, pass);
    l.subscribe(
      (data) => {
        let tk: UserToken = new UserToken(data.token);
        console.log(tk);
        this.setSession(tk);
        localStorage.setItem('login', user.toString());
        this.pointService.updatePoints();
        router.navigateByUrl('../login');
      },
      error => {
        console.log(error);
        if (error.status == 400) {
          alert('Неверный логин или пароль');
        } else {
          alert('Ошибка сервера');
        }
      }
    );
    return l;
  }

  doRegister(user: String, pass: String, router: Router){
    const obs = this.register(user, pass)
      .subscribe(
        (data: any) => {
          alert(data.message);
          router.navigateByUrl('/');
        },
        error => alert("Ошибка: " + error.error.message)
    );
    return obs;
  }

  register(username: String, password: String){
    return this.http.post<UserToken>(`${environment.api}/register`, {username, password}).pipe(shareReplay());
  }

  login(username: String, password: String) {
    return this.http.post<UserToken>(`${environment.api}/login`, {username, password});
  }

  logout() {
    localStorage.removeItem("token");
    localStorage.removeItem("expiresIn");
  }

  setSession(token: UserToken){
    localStorage.setItem('token', token.token);
  }

  public isAuthenticated(): boolean {
    const token = localStorage.getItem('token');
    return token != undefined;
  }
}
