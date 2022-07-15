package utils;

import java.util.HashMap;
import java.util.Map;

public class ImportUtil {
	
	private static final Map<Class<?>, Class<?>> IMPORT_NOT_MAP;
	
	static {
		IMPORT_NOT_MAP = new HashMap<Class<?>, Class<?>>();
		IMPORT_NOT_MAP.put(Integer.class, int.class);
		IMPORT_NOT_MAP.put(Byte.class, byte.class);
		IMPORT_NOT_MAP.put(Character.class, char.class);
		IMPORT_NOT_MAP.put(Boolean.class, boolean.class);
		IMPORT_NOT_MAP.put(Double.class, double.class);
		IMPORT_NOT_MAP.put(Float.class, float.class);
		IMPORT_NOT_MAP.put(Long.class, long.class);
		IMPORT_NOT_MAP.put(Short.class, short.class);
		IMPORT_NOT_MAP.put(Void.class, void.class);
		IMPORT_NOT_MAP.put(String.class, String.class);
		IMPORT_NOT_MAP.put(String[].class, String[].class);
	}
	
	public static boolean isImportable(Object object) {
	    return IMPORT_NOT_MAP.containsKey(object.getClass());
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
