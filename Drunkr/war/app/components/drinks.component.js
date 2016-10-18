"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
var __param = (this && this.__param) || function (paramIndex, decorator) {
    return function (target, key) { decorator(target, key, paramIndex); }
};
var core_1 = require('@angular/core');
var router_1 = require('@angular/router');
var api_service_1 = require('../API/api.service');
var DrinksComponent = (function () {
    function DrinksComponent(router, DrunkrService) {
        this.router = router;
        this.DrunkrService = DrunkrService;
    }
    DrinksComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.isLoading = true;
        this.DrunkrService.drinks()
            .then(function (drinks) {
            _this.allDrinks = _this.drinks = drinks;
            _this.isLoading = false;
        });
    };
    DrinksComponent.prototype.showAll = function () {
        this.searchquery = "";
        this.drinks = this.allDrinks;
        return false;
    };
    DrinksComponent.prototype.onSubmit = function () {
        var _this = this;
        if (this.searchquery.length == 0) {
            this.drinks = this.allDrinks;
            return;
        }
        this.isLoading = true;
        this.DrunkrService.search(this.searchquery)
            .then(function (results) {
            _this.isLoading = false;
            _this.searchResults = _this.drinks = results;
        });
        return false;
    };
    __decorate([
        core_1.Input(), 
        __metadata('design:type', Boolean)
    ], DrinksComponent.prototype, "isLoading", void 0);
    __decorate([
        core_1.Input(), 
        __metadata('design:type', Array)
    ], DrinksComponent.prototype, "drinks", void 0);
    DrinksComponent = __decorate([
        core_1.Component({
            selector: 'drinks',
            templateUrl: '/templates/drinks.html',
        }), 
        __metadata('design:paramtypes', [router_1.Router, api_service_1.DrunkrService])
    ], DrinksComponent);
    return DrinksComponent;
}());
exports.DrinksComponent = DrinksComponent;
var DrinkComponent = (function () {
    function DrinkComponent(router, route, DrunkrService, UserService) {
        this.router = router;
        this.route = route;
        this.DrunkrService = DrunkrService;
        this.UserService = UserService;
        this.ratingSaved = false;
    }
    DrinkComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.route.params.forEach(function (p) {
            _this.id = +p['id'];
            _this.refresh();
        });
    };
    DrinkComponent.prototype.refresh = function () {
        var _this = this;
        this.isLoading = true;
        this.DrunkrService.drink(this.id)
            .then(function (drink) {
            _this.drink = drink;
            _this.isLoading = false;
        })
            .catch(function (drink) {
            _this.drink = null;
            _this.isLoading = false;
        });
    };
    DrinkComponent.prototype.rate = function (stars, event) {
        var _this = this;
        this.isLoading = true;
        this.DrunkrService.rateDrink(this.id, stars)
            .then(function (drink) {
            _this.isLoading = false;
            _this.ratingSaved = true;
            _this.drink = drink;
            event.target.blur();
        });
        return false;
    };
    __decorate([
        core_1.Input(), 
        __metadata('design:type', Boolean)
    ], DrinkComponent.prototype, "isLoading", void 0);
    __decorate([
        core_1.Input(), 
        __metadata('design:type', Object)
    ], DrinkComponent.prototype, "drink", void 0);
    __decorate([
        core_1.Input(), 
        __metadata('design:type', Boolean)
    ], DrinkComponent.prototype, "ratingSaved", void 0);
    DrinkComponent = __decorate([
        core_1.Component({
            selector: 'drink',
            templateUrl: '/templates/drink.html',
        }),
        __param(3, core_1.Input()), 
        __metadata('design:paramtypes', [router_1.Router, router_1.ActivatedRoute, api_service_1.DrunkrService, api_service_1.UserService])
    ], DrinkComponent);
    return DrinkComponent;
}());
exports.DrinkComponent = DrinkComponent;
//# sourceMappingURL=drinks.component.js.map