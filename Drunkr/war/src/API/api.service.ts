
import { Injectable, Inject } from '@angular/core';
import { Http, Response, Headers, RequestOptions, URLSearchParams } from '@angular/http';

import 'rxjs/add/operator/toPromise';

import { User, Drink, DrinkBase, Ingredient, BacResponse } from './api.models';

const useMocks = false;

const fakeDrinks = <Drink[]>[
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

@Injectable()
export class DrunkrService {
    constructor(private http: Http) { }

    private val: number = 14;

    urlEncode(obj: Object): string {
        let urlSearchParams = new URLSearchParams();
        for (let key in obj) {
            urlSearchParams.append(key, obj[key]);
        }
        return urlSearchParams.toString();
    }

    apiPost(url: string, body: any) {
        let headers = new Headers({ 'Content-Type': 'application/x-www-form-urlencoded' });
        let options = new RequestOptions({ headers: headers });

        return this.http.post(url, this.urlEncode(body), options);
    }

    public login(username: string, password: string) {
        return this.apiPost('/login', {
            username: username,
            password: password
        })
            .toPromise()
            .catch(this.handleError)
            .then(resp => resp.json() as User);
    }

    public signup(username: string, email: string, password: string) {
        return this.apiPost('/signup', {
            username: username,
            password: password,
            email: email
        })
            .toPromise()
            .catch(this.handleError)
            .then(resp => resp.json() as User);
    }

    public createDrink(drink: Drink) {
        return this.apiPost('/api/auth/createDrink', {
            name: drink.propertyMap.Name,
            description: drink.propertyMap.Description,
            ingredients: JSON.stringify(drink.propertyMap.Ingredients),
            tasteRating: drink.propertyMap.TasteRating
        })
            .toPromise()
            .catch(this.handleError)
            .then(resp => resp.json() as DrinkBase)
            .then(db => this.toDrink(db));
    }

    public bac(weight: number, ouncesConsumed: number, ABV: number, gender: boolean) {
        if(useMocks) {
            return new Promise<BacResponse>((resolve, reject) => {
                setTimeout(() => {
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
            .then(resp => resp.json() as BacResponse)
    }
    
    public search(query: string) {
    	let params = new URLSearchParams();
        params.set('query', query);

        return this.http.get('/getDrink', { search: params })
            .toPromise()
            .catch(this.handleError)
            .then(resp => resp.json() as DrinkBase[])
            .then(drinkbases => drinkbases.map(this.toDrink));
    }

    public drink(id: number) {
        if (useMocks) {
            return new Promise<Drink>((resolve, reject) => {
                setTimeout(() => {
                    let drink = fakeDrinks.find(v => v.key.id == id);

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
            .then(resp => resp.json() as DrinkBase)
            .then(this.toDrink);
    }

    public drinks() {
        if (useMocks) {
            return new Promise<Drink[]>((resolve, reject) => {
                setTimeout(() => {
                    resolve(fakeDrinks)
                }, 500);
            });
        }

        return this.http.get('/getDrink')
            .toPromise()
            .catch(this.handleError)
            .then(resp => resp.json() as DrinkBase[])
            .then(drinkbases => drinkbases.map(this.toDrink));
    }

    private toDrink(original: DrinkBase) : Drink {
        return {
            key: original.key,
            propertyMap: {
                Name: original.propertyMap.Name,
                Description: original.propertyMap.Description,
                TasteRating: original.propertyMap.TasteRating,
                Ingredients: <Ingredient[]>JSON.parse(original.propertyMap.Ingredients),
                averageRating: original.propertyMap.averageRating,
                totalRatings: original.propertyMap.totalRatings,
                AlcoholContent: original.propertyMap.AlcoholContent,
                grade: original.propertyMap.grade
            }
        };
    }

    public rateDrink(id: number, stars: number) {
        if (useMocks) {
            return new Promise<Drink>((resolve, reject) => {
                setTimeout(() => {
                    resolve(fakeDrinks.find(v => v.key.id == id));
                }, 500);
            });
        }

        return this.apiPost("/api/auth/rateDrink", {
            rating: stars,
            drinkID: id
        })
            .toPromise()
            .catch(this.handleError)
            .then(resp => resp.json() as DrinkBase)
            .then(this.toDrink);
    }

    public logout() {
        return this.http.get("/api/auth/logout")
            .toPromise()
            .then(resp => resp.json() as {});
    }

    private handleError(error: any): Promise<any> {
        console.error('An error occurred', error); // for demo purposes only
        return Promise.reject(error.message || error);
    }

    public me() {
        return this.http.get("/api/auth/me")
            .toPromise()
            .then(resp => resp.json() as User);
    }
}

@Injectable()
export class UserService {
    constructor( @Inject(DrunkrService) private DrunkrService: DrunkrService) {
        this.refreshUser();
    }

    public loadingUser: boolean = false;
    public currentUser: User = null;

    public get isLoggedIn() {
        return this.currentUser != null;
    }

    public refreshUser() {
        this.loadingUser = true;
        this.DrunkrService.me()
            .catch(err => {
                this.loadingUser = false;
                this.currentUser = null;

                console.log("user not logged in");
            })
            .then(user => {
                this.loadingUser = false;
                this.currentUser = user;
            });
    }
}
