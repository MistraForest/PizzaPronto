
package Ue2;

import java.time.LocalDate;
import java.time.Period;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;

import root.Pronto;

public aspect customerAspect extends Uebung02{
	
	declare parents : Ue1.CustomerVO extends Pronto;
	
	static Ue1.CustomerVO customer = new Ue1.CustomerVO();
	
	
	pointcut doToString(Ue1.CustomerVO customer): args(customer) &&
			execution (void TestPizzaPronto_U2.print(Pronto));
	
	pointcut buildClass(Pronto pronto) : args(pronto) && 
			call(* services.generator.GeneratorService.buildData(..));

	after(Ue1.CustomerVO c) returning: doToString(c){
		c.toString();
	}

	public short Ue1.CustomerVO.calculateAge(LocalDate startDate) {
		LocalDate today = LocalDate.now();
	
		if(startDate == null || today == null)
			return -1;
	
		Period period = Period.between(startDate, today);
		return (short) period.getYears();
	}
	
	public String Ue1.CustomerVO.toString() {
		StringBuffer buffer = new StringBuffer();
		toStringFormater(buffer, customer);
		return buffer.toString();
	}
}