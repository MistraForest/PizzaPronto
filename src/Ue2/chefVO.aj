package Ue2;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import Ue1.ChefVO;

public aspect chefVO {	
		
	declare parents : ChefVO extends Pronto;

// Generisch gestalten
	static Ue1.ChefVO chef = new ChefVO();
	
	pointcut chefToString(ChefVO c): args(c) &&
				execution (void TestPizzaPronto_U2.print(Pronto));

	after(ChefVO chef) returning() : chefToString(chef) {
		chef.toString();

	}
	
	//Verbeserung Alle attribute zur Laufzeit
	public String ChefVO.toString (){
		Method[] getters = chef.getClass().getDeclaredMethods();
		StringBuffer buffer = new StringBuffer();
		buffer.append("Chef: {");
		for (Method method : getters) {
			try {
				buffer.append(isGetter(method)?""+method.invoke(chef)+", ":"");
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		buffer.deleteCharAt(buffer.lastIndexOf(","));
		buffer.append("}");
		return buffer.toString();
	
	
	}

	private static void extracted(StringBuffer buffer, Method action) {
		String methodName = action.getName();
		if(methodName.startsWith("get")) {
			buffer.append(methodName+" ");
		}
	}
	
	private static String firstToUpperCase(String name) {
		return name.substring(0, 1).toUpperCase() + name.substring(1);
	}
	
	public static void retrieveAndExecuteBeanGetterMethods(Object bean) throws IntrospectionException {
	    List<PropertyDescriptor> beanGettersList = Arrays.asList(
	            Introspector.getBeanInfo(bean.getClass(), Object.class)
	                    .getPropertyDescriptors());

	    beanGettersList.stream()
	            .filter(pd -> Objects.nonNull(pd.getReadMethod()))
	            .collect(Collectors.toMap(PropertyDescriptor::getName,
	                    pd -> {
	                        try {
	                        	System.out.println(isGetter(pd.getReadMethod()));
	                        	
	                            return pd.getReadMethod().getName();
	                        } catch (Exception e) {
	                            return null;
	                        }
	                    }));

	}
	
	public static boolean isGetter(Method method) {
		   if (Modifier.isPublic(method.getModifiers()) &&
		      method.getParameterTypes().length == 0) {
		         if (method.getName().matches("^get[A-Z].*") &&
		            !method.getReturnType().equals(void.class))
		               return true;
		         if (method.getName().matches("^is[A-Z].*") &&
		            method.getReturnType().equals(boolean.class))
		               return true;
		   }
		   return false;
		}
	
	public static boolean isSetter(Method method) {
		   return Modifier.isPublic(method.getModifiers()) &&
		      method.getReturnType().equals(void.class) &&
		         method.getParameterTypes().length == 1 &&
		            method.getName().matches("^set[A-Z].*");
		}
	

	private String getFieldName(String fieldName){
		return fieldName.substring(0, 1).toUpperCase() + fieldName.substring(
		    1, fieldName.length());
	}
		
}