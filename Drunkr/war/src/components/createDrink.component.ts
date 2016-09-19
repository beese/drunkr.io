import { Component, OnInit, Input } from '@angular/core';
import { Response } from '@angular/http';
import { Router } from '@angular/router';

import { DrunkrService, UserService } from '../API/api.service';

@Component({
  selector: 'my-createDrink',
  templateUrl: '/templates/createDrink.html',
})
export class CreateDrinkComponent implements OnInit {

  constructor(
    private router: Router,
    private DrunkrService: DrunkrService,
    private UserService: UserService) {
  }

  public name: string;
  public tasteRating: number;
  public description: string;
  public isLoading: boolean;
  @Input() public errorMessage: string;

  public onSubmit() {
    this.isLoading = true;
    this.DrunkrService.createDrink()
    .then(User => {
      this.isLoading = false;
      this.UserService.currentUser = User;
      this.router.navigate(['/']);
    }, (reason : Response) => {
      this.errorMessage = reason.json()["message"];
      this.isLoading = false;
    });
    return false;
  }

  ngOnInit(): void {
    
  }
}