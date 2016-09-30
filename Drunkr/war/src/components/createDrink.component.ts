import { Component, OnInit, Input } from '@angular/core';
import { Response } from '@angular/http';
import { Router } from '@angular/router';

import { DrunkrService, UserService } from '../API/api.service';
import { Drink, Ingredient } from '../API/api.models';

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

  public drink: Drink = {
    key: {
      id: -1,
      kind: "Drink"
    },
    propertyMap: {
      Name: "",
      Description: "",
      Ingredients: [],
      TasteRating: 0,
      averageRating: 0,
      AlcoholContent: 0,
      totalRatings: 1,
      grade: ""
    }
  };

  public onSubmit() {
    this.isLoading = true;
    this.drink.propertyMap.Ingredients.map(i => {
      i.abv = i.abv / 2 / 100;
      return i;
    });
    this.DrunkrService.createDrink(this.drink)
      .then(drink => {
        this.isLoading = false;
        this.router.navigate(['/drinks', drink.key.id]);
      }, (reason: Response) => {
        this.errorMessage = reason.json()["message"];
        this.isLoading = false;
      });
    return false;
  }

  ngOnInit(): void {
    this.addIng();
  }

  addIng() {
    this.drink.propertyMap.Ingredients.push(<Ingredient>{
      name: "",
      unit: "oz",
      abv: 0,
      amount: 0
    });
  }

  del(idx: number) {
    this.drink.propertyMap.Ingredients.splice(idx, 1);
  }

  rate(stars: number, event: MouseEvent) {
    this.drink.propertyMap.TasteRating = stars;

    (<HTMLButtonElement>event.target).blur();

    return false;
  }
}