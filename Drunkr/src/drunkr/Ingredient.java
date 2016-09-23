package drunkr;

public class Ingredient {
	public String name;
	public double amount;
	public String unit;

	public double abv;
	
	public Ingredient(String name, double amount, String unit, double abv) {
		this.name = name;
		this.amount = amount;
		this.unit = unit;
		this.abv = abv;
	}
}

