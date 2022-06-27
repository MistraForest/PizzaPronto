
package Ue2;

public aspect PizzaVO {
	
	declare parents : Ue1.PizzaVO extends Pronto;
	static Ue1.PizzaVO pizza = new Ue1.PizzaVO();
	
	pointcut pizzaToString(Ue1.PizzaVO p): args(p) &&
			execution(void TestPizzaPronto_U2.print(Pronto));
			
	after(Ue1.PizzaVO pizza) returning() : pizzaToString(pizza) {
		pizza.toString();
	}	

	public String Ue1.PizzaVO.toString (){
		String value = "[";
		for (int i = 0; i < pizza.getIngredients().length; i++) {
			value = value.concat(pizza.getIngredients()[i]+", ");
		}
		value = value.concat("]");
		value = value.replaceAll(", ]", "]");
		return "Pizza: {Lastname: "+pizza.getName()+
				", Ingredients: "+value+
				", Price: "+pizza.getPrice()+" €}";
	
	}
}