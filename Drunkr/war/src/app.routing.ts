import { ModuleWithProviders }  from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { HomeComponent } from './components/home.component';
import { LoginComponent } from './components/login.component';
import { SignupComponent } from './components/signup.component';
import { DrinksComponent, DrinkComponent } from './components/drinks.component';
import { CreateDrinkComponent} from './components/createDrink.component';

const appRoutes: Routes = [
    {
        path: '',
        redirectTo: '/home',
        pathMatch: 'full'
    },
    {
        path: 'home',
        component: HomeComponent
    },
    {
        path: 'drinks',
        component: DrinksComponent
    },
    {
        path: 'drinks/:id',
        component: DrinkComponent
    },
    {
        path: 'login',
        component: LoginComponent
    },
    {
        path: 'signup',
        component: SignupComponent
    },
    {
        path: 'createDrink',
        component: CreateDrinkComponent
    }
];

export const routing: ModuleWithProviders = RouterModule.forRoot(appRoutes, { useHash: true });
