
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
        Decripiton: string;
        TasteRating: number;
        Ingredients: Ingredient[];
        averageRating: number;
        totalRatings: number;
        AlcoholContent: number;
    }
}
