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
var core_1 = require('@angular/core');
var router_1 = require('@angular/router');
var api_service_1 = require('../API/api.service');
var CreateDrinkComponent = (function () {
    function CreateDrinkComponent(router, DrunkrService, UserService) {
        this.router = router;
        this.DrunkrService = DrunkrService;
        this.UserService = UserService;
        this.drink = {
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
    }
    CreateDrinkComponent.prototype.onSubmit = function () {
        var _this = this;
        this.isLoading = true;
        this.drink.propertyMap.Ingredients.map(function (i) {
            i.abv = i.abv / 2 / 100;
            return i;
        });
        this.DrunkrService.createDrink(this.drink)
            .then(function (drink) {
            _this.isLoading = false;
            _this.router.navigate(['/drinks', drink.key.id]);
        }, function (reason) {
            _this.errorMessage = reason.json()["message"];
            _this.isLoading = false;
        });
        return false;
    };
    CreateDrinkComponent.prototype.ngOnInit = function () {
        this.addIng();
    };
    CreateDrinkComponent.prototype.addIng = function () {
        this.drink.propertyMap.Ingredients.push({
            name: "",
            unit: "oz",
            abv: 0,
            amount: 0
        });
    };
    CreateDrinkComponent.prototype.del = function (idx) {
        this.drink.propertyMap.Ingredients.splice(idx, 1);
    };
    CreateDrinkComponent.prototype.rate = function (stars, event) {
        this.drink.propertyMap.TasteRating = stars;
        event.target.blur();
        return false;
    };
    __decorate([
        core_1.Input(), 
        __metadata('design:type', String)
    ], CreateDrinkComponent.prototype, "errorMessage", void 0);
    CreateDrinkComponent = __decorate([
        core_1.Component({
            selector: 'my-createDrink',
            templateUrl: '/templates/createDrink.html',
        }), 
        __metadata('design:paramtypes', [router_1.Router, api_service_1.DrunkrService, api_service_1.UserService])
    ], CreateDrinkComponent);
    return CreateDrinkComponent;
}());
exports.CreateDrinkComponent = CreateDrinkComponent;
//# sourceMappingURL=createDrink.component.js.map