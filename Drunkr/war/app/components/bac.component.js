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
var api_service_1 = require('../API/api.service');
var BacComponent = (function () {
    function BacComponent(DrunkrService) {
        this.DrunkrService = DrunkrService;
    }
    BacComponent.prototype.onSubmit = function () {
        var _this = this;
        this.isLoading = true;
        this.DrunkrService.bac(this.weight, this.ouncesConsumed, this.ABV, this.gender)
            .then(function (bacNum) {
            _this.isLoading = false;
            _this.bacNum = bacNum.bac;
            _this.gotBac = true;
        }, function (reason) {
            _this.errorMessage = reason.json()["message"];
            _this.isLoading = false;
        });
        return false;
    };
    __decorate([
        core_1.Input(), 
        __metadata('design:type', Number)
    ], BacComponent.prototype, "bacNum", void 0);
    __decorate([
        core_1.Input(), 
        __metadata('design:type', String)
    ], BacComponent.prototype, "errorMessage", void 0);
    BacComponent = __decorate([
        core_1.Component({
            selector: 'my-bac',
            templateUrl: '/templates/bac.html',
        }), 
        __metadata('design:paramtypes', [api_service_1.DrunkrService])
    ], BacComponent);
    return BacComponent;
}());
exports.BacComponent = BacComponent;
//# sourceMappingURL=bac.component.js.map