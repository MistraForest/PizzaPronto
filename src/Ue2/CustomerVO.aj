
package Ue2;

import java.time.LocalDate;
import java.time.Period;

public aspect CustomerVO {
	
	declare parents : Ue1.CustomerVO extends Pronto;
	static Ue1.CustomerVO customer;
	
	CustomerVO(){
		customer = new Ue1.CustomerVO();
	}
	
	pointcut doToString(Ue1.CustomerVO customer): args(customer) &&
			execution (void TestPizzaPronto_U2.print(Pronto));

	after(Ue1.CustomerVO c) returning: doToString(c){
		c.toString();
	}

	public static short calculateAge(LocalDate startDate) {
		LocalDate today = LocalDate.now();
	
		if(startDate == null || today == null)
			return -1;
	
		Period period = Period.between(startDate, today);
		return (short) period.getYears();
	}
	
	public String Ue1.CustomerVO.toString() {
		super.toString();
		return "Customer: {Lastname: "+customer.getLastName()+
				", Firstname: "+customer.getFirstName()+
				", Gender: "+customer.getGender()+
				", Birtday: "+customer.dobToString()+
				", Age: "+calculateAge(customer.getDateOfBirth())+"}";
	}
}