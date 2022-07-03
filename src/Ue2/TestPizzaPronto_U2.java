package Ue2;

import java.awt.Color;
import java.lang.reflect.Method;
import java.time.LocalDate;

import Ue1.ChefVO;
import Ue1.PizzaVO;
import services.generator.GeneratorService;
import Ue1.CustomerVO;

public class TestPizzaPronto_U2 {

	public static void main(String[] args) {
		
		LocalDate today = LocalDate.now();
		LocalDate dob = LocalDate.of(1990, 5, 24);
		
		PizzaVO pizza = new PizzaVO();
		ChefVO chef = new ChefVO("Marie", "Tagne", Color.gray);
		CustomerVO customer = new CustomerVO("Tamo", "Arno", dob);
		
		System.out.println("Today: "+ today + "\nDate of birth: "+ dob);
		
		customer.setLastName("Male");
				
		pizza.setName("Camerunes");
		customer.setGender("Male");
		chef.setColorApron(Color.green);
		
		/*
		 * print(chef); print(customer); print(pizza);
		 */
		
		System.out.println(chef);
		System.out.println(customer);
		System.out.println(pizza);
		
		GeneratorService.getGenerator()
						.buildData(chef)
						.writeFile();
	}
	
	public static void print(Pronto msg) {
		//System.out.println(msg);
	}

}
