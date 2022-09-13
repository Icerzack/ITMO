import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormControl, Validators} from "@angular/forms";
import {PointService} from "../../services/point.service";

@Component({
  selector: 'app-point-form',
  templateUrl: './point-form.component.html',
  styleUrls: ['./point-form.component.css']
})
export class PointFormComponent implements OnInit {
  readonly xValues = [-2, -1.5, -1, -0.5, 0, 0.5, 1, 1.5, 2]; //{'-4','-3','-2','-1','0','1','2','3','4'} для координаты по оси X,
  readonly yMin = -5; // Text (-3 ... 3) для координаты по оси Y,
  readonly yMax = 3;
  readonly rValues = [-2, -1.5, -1, -0.5, 0, 0.5, 1, 1.5, 2]; // Select {'-4','-3','-2','-1','0','1','2','3','4'} для задания радиуса области

  x = new FormControl(1);
  y = new FormControl('', Validators.compose([
    Validators.min(-5),
    Validators.max(3)
  ]));
  _r = new FormControl(1);

  @Output() rChange = new EventEmitter<number>();

  @Input()
  set r(value: number){
    this._r.setValue(value);
    console.log(`tried to set ${value}`);
    this.rChange.emit(this._r.value);
  }

  setR(newR: any){
    this._r.setValue(newR.target.value);
    this.pointService.r = this._r.value;
  }

  submit(){
    console.log(`отправка ${this.x.value} ${this.y.value} ${this._r.value}`);
    if(!this.validateY()){
      alert('Y должен быть числом от -5 до 3');
    }
    else if(!this.validateR()){
      alert('R должен быть числом от 0.5 до 2');
    }
    else{
      this.pointService.postPoint(
        parseFloat(this.x.value),
        parseFloat(this.y.value.replace('.', ',')),
        parseFloat(this._r.value)
      );
    }
  }

  reset(){
    this.x.setValue(1);
    this.y.setValue('');
    this._r.setValue(1);
    this.pointService.deletePoints();
    window.location.reload();
    this.pointService.updatePoints();
  }

  validateY() : boolean{
    return parseFloat(this.y.value) >= -5 && parseFloat(this.y.value) <= 3;
  }

  validateR() : boolean{
    return parseFloat(this._r.value) > 0;
  }

  constructor(private pointService: PointService) {
    this._r.valueChanges.subscribe((value)=>{
      this.rChange.emit(value);
    });
  }

  ngOnInit(): void {
  }

}
