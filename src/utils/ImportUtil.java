package utils;

import java.util.HashMap;
import java.util.Map;

public class ImportUtil {
	
	private static final Map<Class<?>, String> IMPORT_NOT_MAP;
	
	static {
		IMPORT_NOT_MAP = new HashMap<Class<?>, String>();
		IMPORT_NOT_MAP.put(Integer.class, Integer.class.getSimpleName());
		IMPORT_NOT_MAP.put(int.class, int.class.getSimpleName());
		IMPORT_NOT_MAP.put(Byte.class, byte.class.getSimpleName());
		IMPORT_NOT_MAP.put(Character.class, char.class.getSimpleName());
		IMPORT_NOT_MAP.put(Boolean.class, boolean.class.getSimpleName());
		IMPORT_NOT_MAP.put(Double.class, double.class.getSimpleName());
		IMPORT_NOT_MAP.put(Float.class, float.class.getSimpleName());
		IMPORT_NOT_MAP.put(float.class, float.class.getSimpleName());
		IMPORT_NOT_MAP.put(Long.class, long.class.getSimpleName());
		IMPORT_NOT_MAP.put(Short.class, Short.class.getSimpleName());
		IMPORT_NOT_MAP.put(short.class, short.class.getSimpleName());
		IMPORT_NOT_MAP.put(Void.class, Void.class.getSimpleName());
		IMPORT_NOT_MAP.put(void.class, void.class.getSimpleName());
		IMPORT_NOT_MAP.put(String.class, String.class.getSimpleName());
		IMPORT_NOT_MAP.put(String[].class, String[].class.getSimpleName());
	}
	
	public static boolean isNotImportable(Class<?> object) {
	    return IMPORT_NOT_MAP.containsKey(object);
	}
	
	public static boolean isWiderPrimitive(Object object) {
	    if (object == null) {
	        return false;
	    }
	    Class<?> clazz = object.getClass();
	    if (clazz == Boolean.class || clazz == Character.class || 
	        clazz == Byte.class || clazz == Short.class ||
	        clazz == Integer.class || clazz == Long.class || 
	        clazz == Float.class || clazz == Double.class ||
	        clazz == String.class || clazz == Void.class) {
	        return true;
	    }
	    return false;
	}
}
