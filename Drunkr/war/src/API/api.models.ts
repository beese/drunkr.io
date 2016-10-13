
export interface Model {
    key: {
        kind: string;
        id: number;
    }
} 

export interface User extends Model {
    propertyMap: {
        Username: string;
        Email: string;
    }
}

export interface Ingredient {
    name: string;
    amount: number;
    unit: string;
    abv: number;
}

export interface Drink extends Model {
    propertyMap: {
        Name: string;
        Description: string;
        TasteRating: number;
        Ingredients: Ingredient[];
        averageRating: number;
        totalRatings: number;
        AlcoholContent: number;
        grade: string;
    }
}

export interface DrinkBase extends Model {
    propertyMap: {
        Name: string;
        Description: string;
        TasteRating: number;
        Ingredients: string;
        averageRating: number;
        totalRatings: number;
        AlcoholContent: number;
        grade: string;
    }
}

export interface BacResponse {
    bac: number;
}
