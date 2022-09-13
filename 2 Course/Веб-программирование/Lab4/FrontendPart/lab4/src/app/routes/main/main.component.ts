import { Component, OnInit } from '@angular/core';
import {AuthService} from "../../services/auth.service";
import {Router} from "@angular/router";
import {PointService} from "../../services/point.service";
import {Point} from "../../models/point";

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {
  points!: Point[];
  r!: number;

  constructor(private authService: AuthService, private router: Router, private pointServices: PointService) { }

  ngOnInit(): void {
    this.points = this.pointServices.points;
  }

  getUsername(){
    return localStorage.getItem('login');
  }

  logout(){
    this.authService.logout();
    localStorage.removeItem('login');
    console.log('Выход');
    this.router.navigate(['/login']);
  }

}
