package Ue1;

public class PizzaVO {
	
	private String name;
	private String[] ingredients;
	private float price;
	
	public PizzaVO(String name, String[] ingredients, float price) {
		this.name = name;
		this.ingredients = ingredients;
		this.price = price;
	}

	public PizzaVO() {
		this("Hawai", new String[] {"Ananas","tomato","Cheese"}, 7.5f);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String[] getIngredients() {
		return ingredients;
	}

	public void setIngredients(String[] ingredients) {
		if(ingredients == null || ingredients.length == 0)
			return;
		this.ingredients = ingredients;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		if(price <= 0)
			return;
		this.price = price;
	}
	
	

}
