package Ue2;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public interface ToStringHandler {

	Object invoke(Method method) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException;
}
