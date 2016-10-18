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
var http_1 = require('@angular/http');
require('rxjs/add/operator/toPromise');
var useMocks = false;
var fakeDrinks = [
    {
        key: {
            kind: "drink",
            id: 1234
        },
        propertyMap: {
            Name: "G+T",
            Description: "Delicious",
            TasteRating: 5,
            Ingredients: [
                {
                    name: "Gin",
                    amount: 3,
                    unit: "oz",
                    abv: .48
                },
                {
                    name: "Tonic",
                    amount: 5,
                    unit: "oz",
                    abv: 0
                },
                {
                    name: "Lime",
                    amount: 1,
                    unit: "squeeze",
                    abv: 0
                }
            ]
        }
    },
    {
        key: {
            kind: "drink",
            id: 4321
        },
        propertyMap: {
            Name: "captain and coke",
            Description: "shiver me timbers",
            TasteRating: 4.5,
            Ingredients: [
                {
                    name: "captain jack rum",
                    amount: 4,
                    unit: "oz",
                    abv: .48
                },
                {
                    name: "Coca-Cola",
                    amount: 4,
                    unit: "oz",
                    abv: .0
                }
            ]
        }
    }
];
var DrunkrService = (function () {
    function DrunkrService(http) {
        this.http = http;
        this.val = 14;
    }
    DrunkrService.prototype.urlEncode = function (obj) {
        var urlSearchParams = new http_1.URLSearchParams();
        for (var key in obj) {
            urlSearchParams.append(key, obj[key]);
        }
        return urlSearchParams.toString();
    };
    DrunkrService.prototype.apiPost = function (url, body) {
        var headers = new http_1.Headers({ 'Content-Type': 'application/x-www-form-urlencoded' });
        var options = new http_1.RequestOptions({ headers: headers });
        return this.http.post(url, this.urlEncode(body), options);
    };
    DrunkrService.prototype.login = function (username, password) {
        return this.apiPost('/login', {
            username: username,
            password: password
        })
            .toPromise()
            .catch(this.handleError)
            .then(function (resp) { return resp.json(); });
    };
    DrunkrService.prototype.signup = function (username, email, password) {
        return this.apiPost('/signup', {
            username: username,
            password: password,
            email: email
        })
            .toPromise()
            .catch(this.handleError)
            .then(function (resp) { return resp.json(); });
    };
    DrunkrService.prototype.createDrink = function (drink) {
        var _this = this;
        return this.apiPost('/api/auth/createDrink', {
            name: drink.propertyMap.Name,
            description: drink.propertyMap.Description,
            ingredients: JSON.stringify(drink.propertyMap.Ingredients),
            tasteRating: drink.propertyMap.TasteRating
        })
            .toPromise()
            .catch(this.handleError)
            .then(function (resp) { return resp.json(); })
            .then(function (db) { return _this.toDrink(db); });
    };
    DrunkrService.prototype.bac = function (weight, ouncesConsumed, ABV, gender) {
        if (useMocks) {
            return new Promise(function (resolve, reject) {
                setTimeout(function () {
                    resolve({ "bac": 0.07 });
                }, 500);
            });
        }
        return this.apiPost('/bac', {
            weight: weight,
            ouncesConsumed: ouncesConsumed,
            ABV: ABV,
            gender: gender
        })
            .toPromise()
            .catch(this.handleError)
            .then(function (resp) { return resp.json(); });
    };
    DrunkrService.prototype.search = function (query) {
        var _this = this;
        var params = new http_1.URLSearchParams();
        params.set('query', query);
        return this.http.get('/getDrink', { search: params })
            .toPromise()
            .catch(this.handleError)
            .then(function (resp) { return resp.json(); })
            .then(function (drinkbases) { return drinkbases.map(_this.toDrink); });
    };
    DrunkrService.prototype.drink = function (id) {
        if (useMocks) {
            return new Promise(function (resolve, reject) {
                setTimeout(function () {
                    var drink = fakeDrinks.find(function (v) { return v.key.id == id; });
                    if (drink) {
                        resolve(drink);
                    }
                    else {
                        reject("not found");
                    }
                }, 500);
            });
        }
        return this.http.get("/getDrink?id=" + id)
            .toPromise()
            .catch(this.handleError)
            .then(function (resp) { return resp.json(); })
            .then(this.toDrink);
    };
    DrunkrService.prototype.drinks = function () {
        var _this = this;
        if (useMocks) {
            return new Promise(function (resolve, reject) {
                setTimeout(function () {
                    resolve(fakeDrinks);
                }, 500);
            });
        }
        return this.http.get('/getDrink')
            .toPromise()
            .catch(this.handleError)
            .then(function (resp) { return resp.json(); })
            .then(function (drinkbases) { return drinkbases.map(_this.toDrink); });
    };
    DrunkrService.prototype.toDrink = function (original) {
        return {
            key: original.key,
            propertyMap: {
                Name: original.propertyMap.Name,
                Description: original.propertyMap.Description,
                TasteRating: original.propertyMap.TasteRating,
                Ingredients: JSON.parse(original.propertyMap.Ingredients),
                averageRating: original.propertyMap.averageRating,
                totalRatings: original.propertyMap.totalRatings,
                AlcoholContent: original.propertyMap.AlcoholContent,
                grade: original.propertyMap.grade
            }
        };
    };
    DrunkrService.prototype.rateDrink = function (id, stars) {
        if (useMocks) {
            return new Promise(function (resolve, reject) {
                setTimeout(function () {
                    resolve(fakeDrinks.find(function (v) { return v.key.id == id; }));
                }, 500);
            });
        }
        return this.apiPost("/api/auth/rateDrink", {
            rating: stars,
            drinkID: id
        })
            .toPromise()
            .catch(this.handleError)
            .then(function (resp) { return resp.json(); })
            .then(this.toDrink);
    };
    DrunkrService.prototype.logout = function () {
        return this.http.get("/api/auth/logout")
            .toPromise()
            .then(function (resp) { return resp.json(); });
    };
    DrunkrService.prototype.handleError = function (error) {
        console.error('An error occurred', error); // for demo purposes only
        return Promise.reject(error.message || error);
    };
    DrunkrService.prototype.me = function () {
        return this.http.get("/api/auth/me")
            .toPromise()
            .then(function (resp) { return resp.json(); });
    };
    DrunkrService = __decorate([
        core_1.Injectable(), 
        __metadata('design:paramtypes', [http_1.Http])
    ], DrunkrService);
    return DrunkrService;
}());
exports.DrunkrService = DrunkrService;
var UserService = (function () {
    function UserService(DrunkrService) {
        this.DrunkrService = DrunkrService;
        this.loadingUser = false;
        this.currentUser = null;
        this.refreshUser();
    }
    Object.defineProperty(UserService.prototype, "isLoggedIn", {
        get: function () {
            return this.currentUser != null;
        },
        enumerable: true,
        configurable: true
    });
    UserService.prototype.refreshUser = function () {
        var _this = this;
        this.loadingUser = true;
        this.DrunkrService.me()
            .catch(function (err) {
            _this.loadingUser = false;
            _this.currentUser = null;
            console.log("user not logged in");
        })
            .then(function (user) {
            _this.loadingUser = false;
            _this.currentUser = user;
        });
    };
    UserService = __decorate([
        core_1.Injectable(),
        __param(0, core_1.Inject(DrunkrService)), 
        __metadata('design:paramtypes', [DrunkrService])
    ], UserService);
    return UserService;
}());
exports.UserService = UserService;
//# sourceMappingURL=api.service.js.map