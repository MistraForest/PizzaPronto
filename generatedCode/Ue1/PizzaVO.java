
<# BINDING ELEMENTS WITH POJO CLASS>

package Ue1;


public class PizzaVO {

	private String name;
	private String[] ingredients;
	private float price;
	
	public PizzaVO(String arg0, String[] arg1, float arg2){
		
	}	
	public PizzaVO(){
		
	}	
	
	
	public void setName(String name){
		this.name = name;
	}
	public void setPrice(float price){
		this.price = price;
	}
	public void setIngredients(String[] ingredients){
		this.ingredients = ingredients;
	}
	
	public String getName(){
		return Name;
	}
	public String[] getIngredients(){
		return Ingredients;
	}
	public float getPrice(){
		return Price;
	}
	
	public String toString(){
		
	}	
}