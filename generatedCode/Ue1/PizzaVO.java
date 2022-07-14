
<# BINDING ELEMENTS WITH POJO CLASS>

package Ue1;


public class PizzaVO {

	private java.lang.String name;
	private java.lang.String[] ingredients;
	private float price;
	
	
	public Ue1.PizzaVO(String arg0, String[] arg1, float arg2){
		
	}
	

	public Ue1.PizzaVO(){
		
	}

	
	public void setName(java.lang.String arg0){
		this.name = arg0;
	}
	public void setIngredients([Ljava.lang.String; arg0){
		this.ingredients = arg0;
	}
	public void setPrice(float arg0){
		this.price = arg0;
	}
	
	public String getName(){
		return name;
	}
	public String[] getIngredients(){
		return ingredients;
	}
	public float getPrice(){
		return price;
	}
}

