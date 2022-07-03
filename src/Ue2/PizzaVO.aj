
package Ue2;

import root.Pronto;

public aspect PizzaVO extends Uebung02{
	
	declare parents : Ue1.PizzaVO extends Pronto;

	static Ue1.PizzaVO pizza = new Ue1.PizzaVO();
	
	pointcut pizzaToString(Ue1.PizzaVO p): args(p) &&
			execution(void TestPizzaPronto_U2.print(Pronto));
	
	pointcut buildClass(Pronto pronto) : args(pronto) && 
			call(* services.generator.GeneratorService.buildData(..));
			
	after(Ue1.PizzaVO pizza) returning() : pizzaToString(pizza) {
		pizza.toString();
	}	

	public String Ue1.PizzaVO.toString (){
		/*
		 * String value = "["; for (int i = 0; i < pizza.getIngredients().length; i++) {
		 * value = value.concat(pizza.getIngredients()[i]+", "); } value =
		 * value.concat("]"); value = value.replaceAll(", ]", "]"); return
		 * "Pizza: {Lastname: "+pizza.getName()+ ", Ingredients: "+value+
		 * ", Price: "+pizza.getPrice()+" €}";
		 */
		StringBuffer buffer = new StringBuffer();
		toStringFormater(buffer, pizza);
		return buffer.toString();
	}
}