package Ue2;

import Ue1.ChefVO;

public aspect chefVO {	
		
	declare parents : ChefVO extends Pronto;
	static Ue1.ChefVO chef = new ChefVO();
	
	pointcut chefToString(ChefVO c): args(c) &&
				execution (void TestPizzaPronto_U2.print(Pronto));

	after(ChefVO chef) returning() : chefToString(chef) {
		chef.toString();
	}
	
	public String ChefVO.toString (){
		return "Chef: {Lastname: "+getLastName()+
				", Firstname: "+getFirstName()+
				", Gender: "+getColorApron()+"}";
	
	}
	
	
}