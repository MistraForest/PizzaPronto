package Ue1;

import java.awt.Color;

import java.time.LocalDate;


public class TestPizzaPronto_U1 {

	public static void main(String[] args) {
		
		
		LocalDate today = LocalDate.now();
		LocalDate dob = LocalDate.of(1990, 5, 24);
		
		PizzaVO pizza = new PizzaVO();
		ChefVO chef = new ChefVO("Marie", "Tagne", Color.gray);
		CustomerVO customer = new CustomerVO("Tamo", "Arno", dob);
		
		System.out.println("Today: "+ today + "\nDate of birth: "+ dob);
		
		System.out.println("Chef: "+ chef);
		System.out.println("Pizza: "+ pizza);
		System.out.println("Customer: "+ customer);
		
		pizza.setName("Camerunes");
		customer.setGender("Male");
		chef.setColorApron(Color.green);
		
		/*
		 * System.out.println("Pizza: {"+pizza.getName()+"," + pizza.getIngredients()
		 * +", "+pizza.getPrice()+" €}"); System.out.println("Customer: {"+
		 * customer.getLastName()+", "+customer.dobToString()+"}");
		 * System.out.println("Chef: {"+chef.getLastName() +", "
		 * +chef.getColorApron()+"}");
		 */
	}

}
