import {Component, ElementRef, Input, OnInit, QueryList, ViewChild, ViewChildren} from '@angular/core';
import {Point} from "../../models/point";
import {PointService} from "../../services/point.service";

@Component({
  selector: 'app-points-area',
  templateUrl: './points-area.component.html',
  styleUrls: ['./points-area.component.css']
})
export class PointsAreaComponent implements OnInit {
  @Input()
  pointList: Point[] = [];

  get filterPoints(){
    console.log('filter', this.pointList.concat([]));
    return this.pointList.concat([]).filter(x => x.r == this.r);
  }

  @Input()
  get r(): number{
    return this.pointService.r;
  }

  @ViewChildren('circle')
  circles!:QueryList<ElementRef>;
  @ViewChild('canvas')
  canvas!:ElementRef;

  constructor(private pointService: PointService) { }

  ngOnInit(): void {
    console.log(this.pointService.points);
    let cp: Point[] = Array.from(this.pointService.points);
    console.log('vals', cp);
    this.pointService.points.forEach(x => console.log(x));//.filter(x => x.r == this.r);
    this.pointList = this.pointService.points;
    console.log(this.pointList);
  }

  checkPoint(e: MouseEvent){
    console.log('R = ', this.pointService.r);
    const rect = this.canvas.nativeElement.getBoundingClientRect();
    //console.log(this.canvas.nativeElement)
    if(this.pointService.r > 0) {
      const x = (e.clientX - rect.left - 200) / 160 * this.pointService.r;
      const y = -(e.clientY - rect.top - 200) / 160 * this.pointService.r;
      if (y < -5 || y > 3) {
        alert("Y должен быть от -5 до 3");
        return;
      }
      console.log('post!', x, y, this.pointService.r);
      this.pointService.postPoint(x, y, this.pointService.r);
    }
    else{
      alert("Пожалуйста, выберите положительный R");
    }
  }

  scaleCircles(newR: number){
    this.circles?.forEach(circle=>{
      const newX = circle.nativeElement.dataset.x/newR;
      const newY = -circle.nativeElement.dataset.y/newR;
      console.log(`${newR} ${newX} ${-newY}`);
      circle.nativeElement.style = `transform: translate(${newX}px, ${newY}px)`;
    })
  }
}
