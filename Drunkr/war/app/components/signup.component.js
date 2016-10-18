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
var SignupComponent = (function () {
    function SignupComponent(router, DrunkrService, UserService) {
        this.router = router;
        this.DrunkrService = DrunkrService;
        this.UserService = UserService;
    }
    SignupComponent.prototype.onSubmit = function () {
        var _this = this;
        this.isLoading = true;
        this.DrunkrService.signup(this.username, this.email, this.password)
            .then(function (User) {
            _this.isLoading = false;
            _this.UserService.currentUser = User;
            _this.router.navigate(['/']);
        }, function (reason) {
            _this.errorMessage = reason.json()["message"];
            _this.isLoading = false;
        });
        return false;
    };
    SignupComponent.prototype.ngOnInit = function () {
    };
    __decorate([
        core_1.Input(), 
        __metadata('design:type', String)
    ], SignupComponent.prototype, "errorMessage", void 0);
    SignupComponent = __decorate([
        core_1.Component({
            selector: 'my-signup',
            templateUrl: '/templates/signup.html',
        }), 
        __metadata('design:paramtypes', [router_1.Router, api_service_1.DrunkrService, api_service_1.UserService])
    ], SignupComponent);
    return SignupComponent;
}());
exports.SignupComponent = SignupComponent;
//# sourceMappingURL=signup.component.js.map