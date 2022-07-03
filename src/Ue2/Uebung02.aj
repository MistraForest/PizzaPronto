package Ue2;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public abstract aspect Uebung02 {

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
}
