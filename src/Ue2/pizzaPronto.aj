package Ue2;

import Ue1.ChefVO;
import root.Pronto;
import services.generator.GeneratorService;

public aspect pizzaPronto extends Uebung02{	
		
	declare parents : ChefVO extends Pronto;

// Generisch gestalten
	static Ue1.ChefVO chef = new ChefVO();
	

	pointcut chefToString(ChefVO c): args(c) &&
				execution (void TestPizzaPronto_U2.print(Pronto));
	
	pointcut buildClass(Pronto pronto) : args(pronto) && 
				call(* services.generator.GeneratorService.buildData(..));


	after(ChefVO chef) returning() : chefToString(chef) {
		chef.toString();

	}
	
	//toString to be injected at runtime in the class ChefVO
	public String ChefVO.toString (){
		
		StringBuffer buffer = new StringBuffer();

		toStringFormater(buffer, chef);
	
		return buffer.toString();
	}



}