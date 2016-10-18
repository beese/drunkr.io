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
var http_1 = require('@angular/http');
var forms_1 = require('@angular/forms');
var platform_browser_1 = require('@angular/platform-browser');
var app_component_1 = require('./app.component');
var api_service_1 = require('./API/api.service');
var app_routing_1 = require('./app.routing');
var home_component_1 = require('./components/home.component');
var login_component_1 = require('./components/login.component');
var signup_component_1 = require('./components/signup.component');
var drinks_component_1 = require('./components/drinks.component');
var createDrink_component_1 = require('./components/createDrink.component');
var bac_component_1 = require('./components/bac.component');
var AppModule = (function () {
    function AppModule() {
    }
    AppModule = __decorate([
        core_1.NgModule({
            imports: [platform_browser_1.BrowserModule, http_1.HttpModule, app_routing_1.routing, forms_1.FormsModule],
            declarations: [app_component_1.AppComponent, home_component_1.HomeComponent, login_component_1.LoginComponent, signup_component_1.SignupComponent, bac_component_1.BacComponent, createDrink_component_1.CreateDrinkComponent, drinks_component_1.DrinkComponent, drinks_component_1.DrinksComponent],
            bootstrap: [app_component_1.AppComponent],
            providers: [api_service_1.DrunkrService, api_service_1.UserService]
        }), 
        __metadata('design:paramtypes', [])
    ], AppModule);
    return AppModule;
}());
exports.AppModule = AppModule;
//# sourceMappingURL=app.module.js.map