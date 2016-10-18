"use strict";
var router_1 = require('@angular/router');
var home_component_1 = require('./components/home.component');
var login_component_1 = require('./components/login.component');
var signup_component_1 = require('./components/signup.component');
var drinks_component_1 = require('./components/drinks.component');
var createDrink_component_1 = require('./components/createDrink.component');
var bac_component_1 = require('./components/bac.component');
var appRoutes = [
    {
        path: '',
        redirectTo: '/home',
        pathMatch: 'full'
    },
    {
        path: 'home',
        component: home_component_1.HomeComponent
    },
    {
        path: 'drinks',
        component: drinks_component_1.DrinksComponent
    },
    {
        path: 'drinks/:id',
        component: drinks_component_1.DrinkComponent
    },
    {
        path: 'login',
        component: login_component_1.LoginComponent
    },
    {
        path: 'signup',
        component: signup_component_1.SignupComponent
    },
    {
        path: 'createDrink',
        component: createDrink_component_1.CreateDrinkComponent
    },
    {
        path: 'bac',
        component: bac_component_1.BacComponent
    }
];
exports.routing = router_1.RouterModule.forRoot(appRoutes, { useHash: true });
//# sourceMappingURL=app.routing.js.map