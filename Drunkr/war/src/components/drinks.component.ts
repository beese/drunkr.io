import { Component, OnInit, Input } from '@angular/core';
import { Response } from '@angular/http';
import { Router, ActivatedRoute, Params } from '@angular/router';

import { DrunkrService, UserService } from '../API/api.service';
import { Drink } from '../API/api.models';

@Component({
    selector: 'drinks',
    templateUrl: '/templates/drinks.html',
})
export class DrinksComponent implements OnInit {

    constructor(
        private router: Router,
        private DrunkrService: DrunkrService) {
    }

    @Input()
    isLoading: boolean;

    @Input()
    drinks: Drink[];

    ngOnInit(): void {
        this.isLoading = true;

        this.DrunkrService.drinks()
            .then(drinks => {
                this.drinks = drinks;
                this.isLoading = false;
            });
    }
}

@Component({
    selector: 'drink',
    templateUrl: '/templates/drink.html',
})
export class DrinkComponent implements OnInit {

    constructor(
        private router: Router,
        private route: ActivatedRoute,
        private DrunkrService: DrunkrService,
        @Input() private UserService: UserService) {
    }

    @Input()
    isLoading: boolean;

    @Input()
    drink: Drink;

    @Input()
    ratingSaved: boolean = false;

    id: number;

    ngOnInit(): void {
        this.route.params.forEach(p => {
            this.id = +p['id'];
            this.refresh();
        });
    }

    refresh() {
        this.isLoading = true;
        this.DrunkrService.drink(this.id)
            .then(drink => {
                this.drink = drink;
                this.isLoading = false;
            })
            .catch(drink => {
                this.drink = null;
                this.isLoading = false;
            });
    }

    rate(stars: number, event: MouseEvent) {
        this.isLoading = true;

        this.DrunkrService.rateDrink(this.id, stars)
            .then(drink => {
                this.isLoading = false;
                this.ratingSaved = true;
                this.drink = drink;

                (<HTMLButtonElement>event.target).blur();
            });

        return false;
    }
}
