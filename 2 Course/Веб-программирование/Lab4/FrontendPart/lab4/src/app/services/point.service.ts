import {Injectable, OnInit} from '@angular/core';
import {Point} from "../models/point";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class PointService{
  points: Point[] = [];
  r: number = 1;

  constructor(private http: HttpClient) {
    this.fetchPoints();
  }

  updatePoints(){
    this.points = [];
    this.fetchPoints();
  }

  fetchPoints(){
    console.log("Fetching points...");
    this.points = [];
    this.http.get<Point[]>(`${environment.api}/points`)
      .subscribe(
        this.addPoints.bind(this),
        console.error,
        console.log
    );
  }

  postPoint(x: number, y: number, r: number){
    this.http.post<Point>(`${environment.api}/points`, {x, y, r})//.pipe(shareReplay())
      .subscribe(
        data => {this.addPoint(data)},
        error => {console.log(error)},
        console.log
    );
  }

  deletePoints(){
    this.http.delete(`${environment.api}/points`)
        .subscribe(() => console.log("delete successfull"));
  }

  addPoint(p: Point){
    p.x = parseFloat(parseFloat(p.x.toString()).toFixed(2));
    p.y = parseFloat(parseFloat(p.y.toString()).toFixed(2));
    p.r = parseFloat(parseFloat(p.r.toString()).toFixed(2));

    this.points.push(p);
  }

  addPoints(ps: Point[]){
    if(ps){
      ps.forEach(p => this.addPoint(p));
    }
  }
}
