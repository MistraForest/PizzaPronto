
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
	
	
	public void setName(String Name){
		this.name = Name;
	}
	public void setIngredients(String[] Ingredients){
		this.ingredients = Ingredients;
	}
	public void setPrice(float Price){
		this.price = Price;
	}
	
	public String getName(){
		return name;
	}
	public float getPrice(){
		return price;
	}
	public String[] getIngredients(){
		return ingredients;
	}
	
	public String toString(){
		
	}	
}