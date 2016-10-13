import { Component, OnInit, Input } from '@angular/core';
import { Response } from '@angular/http';

import { DrunkrService } from '../API/api.service';

@Component({
  selector: 'my-bac',
  templateUrl: '/templates/bac.html',
})
export class BacComponent {

  constructor(
    private DrunkrService: DrunkrService) {
  }

  public weight: number;
  public ouncesConsumed: number;
  public ABV: number;
  public gender: boolean;
  public isLoading: boolean;
  public gotBac: boolean;
  @Input() public bacNum: number;
  @Input() public errorMessage: string;

  public onSubmit() {
    this.isLoading = true;
    
    this.DrunkrService.bac(this.weight, this.ouncesConsumed, this.ABV, this.gender)
      .then(bacNum => {
        this.isLoading = false;
        this.bacNum = bacNum.bac;
        this.gotBac = true;
      }, (reason: Response) => {
        this.errorMessage = reason.json()["message"];
        this.isLoading = false;
      });
    return false;
  }
}