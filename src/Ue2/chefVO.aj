package Ue2;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import Ue1.ChefVO;

public aspect chefVO extends Uebung02{	
		
	declare parents : ChefVO extends Pronto;

// Generisch gestalten
	static Ue1.ChefVO chef = new ChefVO();
	
	pointcut chefToString(ChefVO c): args(c) &&
				execution (void TestPizzaPronto_U2.print(Pronto));

	after(ChefVO chef) returning() : chefToString(chef) {
		chef.toString();

	}
	
	//toString to be injected at runtime in the class ChefVO
	public String ChefVO.toString (){
		
		Method[] getters = chef.getClass().getDeclaredMethods();
		StringBuffer buffer = new StringBuffer();
		buffer.append("Chef: {");
		
		for (Method method : getters) {
			
			try {
				buffer.append(
						isGetter(method) ? retrieveFieldName4ToString(method) + ": " + executeTheGetter(method, chef) + ", " : ""
						);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		buffer.deleteCharAt(buffer.lastIndexOf(", "));
		buffer.append("}");
		return buffer.toString();
	
	
	}

}