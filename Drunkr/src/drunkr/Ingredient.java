package drunkr;

public class Ingredient {
	public String name;
	public int amount;
	public String unit;

	public double abv;
	
	public Ingredient(String name, int amount, String unit, double abv) {
		this.name = name;
		this.amount = amount;
		this.unit = unit;
		this.abv = abv;
	}
}

