package Ue2;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import root.Pronto;

public abstract aspect Uebung02 {
	
	abstract pointcut buildClass(Pronto pronto);

	protected static boolean isGetter(Method method) {
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
	
	protected boolean isSetter(Method method) {
		   return Modifier.isPublic(method.getModifiers()) &&
		      method.getReturnType().equals(void.class) &&
		         method.getParameterTypes().length == 1 &&
		            method.getName().matches("^set[A-Z].*");
	}
	
	protected static Object executeTheGetter(Method method, Pronto pronto) throws IllegalAccessException, InvocationTargetException{
		return invoke(method, pronto);
	}

	protected static String retrieveFieldName4ToString(Method method) {
		return method.getName().replaceFirst("get", "");
	}
	
	protected static Object invoke(Method method, Pronto pronto) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		return method.invoke(pronto);
	}
	
	protected static void toStringFormater(StringBuffer buffer, Pronto pronto) {
		
		buffer.append(pronto.getClass().getName().replace(pronto.getClass().getPackageName()+".", "")+": {");
		for (Method method : getMethods(pronto)) {
			
			try {
				if(method.getReturnType().equals(String[].class)) {
					invoke(method, pronto);
				}
				buffer.append(
						isGetter(method) ? retrieveFieldName4ToString(method) + ": " + executeTheGetter(method, pronto) + ", " : ""
						);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		buffer.deleteCharAt(buffer.lastIndexOf(", "));
		buffer.append("}");
	}

	private static Method[] getMethods(Pronto pronto) {
		Method[] getters = pronto.getClass().getDeclaredMethods();
		return getters;
	}
}

/*
 * for (int i = 0; i < method.invoke(pronto); i++) {
				value = value.concat(pizza.getIngredients()[i]+", "); 
				} 
				value = value.concat("]"); value = value.replaceAll(", ]", "]");
 */
