package services.generator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MappingJsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import PojoPronto.ClazzPojo;
import PojoPronto.ConstructorPojo;
import PojoPronto.GetterClazz;
import PojoPronto.ParameterPojo;
import PojoPronto.PojoMethod;
import PojoPronto.Propertie;
import PojoPronto.SetterClazz;
import Ue1.ChefVO;
import Ue1.CustomerVO;
import Ue1.PizzaVO;
import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;
import lombok.NonNull;
import lombok.val;
import root.Pronto;
import services.ClassConfiguration;
import utils.JsonNodeBuilder;

import static utils.JsonBuilder.object;
import static utils.JsonBuilder.array;

public class GeneratorService {

	private static GeneratorService engine = new GeneratorService();

	private final String GENERATED = "generatedCode/";
	private final String TEMPLATE_FILE = "customerVO.ftl";

	private Template template;
	private Map<String, Map<String, ClazzPojo>> configModel = new HashMap<String, Map<String, ClazzPojo>>();

	/*
	 * private List<String> fieldNames = new ArrayList<String>(); private
	 * List<String> importClassList = new ArrayList<String>();
	 */
	private GeneratorService() {
		init();
	}

	private void init() {

		Configuration configuration = new Configuration(new Version(2, 3, 31));

		configuration.setIncompatibleImprovements(new Version(2, 3, 31));
		configuration.setDefaultEncoding("UTF-8");
		configuration.setLocale(Locale.GERMAN);
		configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

		FileTemplateLoader templateLoader = null;

		try {
			templateLoader = new FileTemplateLoader(new File("src/templates"));
			configuration.setTemplateLoader(templateLoader);

			template = configuration.getTemplate(TEMPLATE_FILE);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static GeneratorService getGenerator() {
		return engine;
	}

	public GeneratorService buildData(ClassConfiguration superClass) {

		Map<String, ClazzPojo> dataModel = new HashMap<String, ClazzPojo>();

		// BIND DATA with a POJO
		bindWithPOJO(superClass, dataModel);

		return engine;
	}

	private void bindWithPOJO(ClassConfiguration configClass, Map<String, ClazzPojo> dataModel) {
		// List<ClazzPojo> pojos = new ArrayList<>();

		for (Object actuelClass : configClass.getClazz()) {

			ClazzPojo clazzPojo = new ClazzPojo();
			String className = actuelClass.getClass().getSimpleName();
			String packageName = actuelClass.getClass().getPackage().getName();

			clazzPojo.setPackageName(packageName);
			clazzPojo.setClassName(className);
			clazzPojo.setProperties(attributesPojoNodes(actuelClass.getClass()).getProperties());
			clazzPojo.setConstructors(constructorsPojoNode(actuelClass.getClass()).getConstructors());
			clazzPojo.setGetters(gettersPojoNodes(actuelClass.getClass()).getGetters());
			clazzPojo.setSetters(settersPojoNodes(actuelClass.getClass()).getSetters());
			clazzPojo.setPojoMethods(methodsPojoNode(actuelClass.getClass()).getPojoMethods());
			
			dataModel.put("clazzPojo", clazzPojo);
			configModel.put(actuelClass.getClass().getSimpleName(), dataModel);
			dataModel = new HashMap<String, ClazzPojo>();

		}
	}

	private Field[] getFields(Class<?> clazz) {
		Field[] allFields = clazz.getDeclaredFields();
		return allFields;
	}

	private Parameter[] getParameters(Method method) {
		Parameter[] parameters = method.getParameters();
		return parameters;
	}

	private Method[] getMethods(Class<?> clazz) {
		Method[] allDeclaredMethod = clazz.getDeclaredMethods();
		return allDeclaredMethod;
	}

	private Constructor<?>[] getConstructors(Class<?> clazz) {
		Constructor<?>[] declaredConstructors = clazz.getDeclaredConstructors();
		return declaredConstructors;
	}

	// Bind data using POJO Class

	private ClazzPojo attributesPojoNodes(Class<?> clazz) {

		ClazzPojo clazzPojo = new ClazzPojo();

		List<Propertie> properties = new ArrayList<>();

		for (Field field : getFields(clazz)) {
			Propertie propertie = new Propertie();
			String type = field.getGenericType().getTypeName();
			String modifier = Modifier.toString(field.getModifiers());
			String propertieName = field.getName();

			propertie.setModifier(modifier);
			propertie.setType(type);
			propertie.setPropertieName(propertieName);

			properties.add(propertie);

		}

		clazzPojo.setProperties(properties);

		return clazzPojo;
	}

	private ClazzPojo constructorsPojoNode(Class<?> clazz) {

		ClazzPojo clazzPojo = new ClazzPojo();
		List<ConstructorPojo> constructors = new ArrayList<>();

		if (getConstructors(clazz).length == 0) {
			clazzPojo.setNoConstructors(true);
		}
		clazzPojo.setNoConstructors(false);
		
		for (Constructor<?> actualConstructor : getConstructors(clazz)) {

			ConstructorPojo constructor = new ConstructorPojo();

			String modifier = Modifier.toString(actualConstructor.getModifiers());
			String name = actualConstructor.getName();
			
			constructor.setModifier(modifier);
			constructor.setConstructorName(name);
			
			if (actualConstructor.isVarArgs()) {

				constructor.setNoArgs(true);
				constructor.setConstructorParameters(null);

				constructors.add(constructor);
			}
			else {
				constructor.setNoArgs(false);

				List<String> parameters = new ArrayList<>();

				for (Parameter actualParameter : actualConstructor.getParameters()) {
					ParameterPojo parameter = new ParameterPojo();
					parameter.setType(actualParameter.getType().getSimpleName());
					parameter.setParamName(actualParameter.getName());
					parameters.add(parameter.templateString());
				}
				constructor.setConstructorParameters(parameters);
				constructors.add(constructor);
			}
			
			clazzPojo.setConstructors(constructors);
		}
		
		return clazzPojo;
	}

	private ClazzPojo gettersPojoNodes(Class<?> clazz) {

		ClazzPojo clazzPojo = new ClazzPojo();

		List<GetterClazz> getters = new ArrayList<>();

		for (Method method : getMethods(clazz)) {
			GetterClazz getter = new GetterClazz();
			if (isGetter(method)) {

				String modifier = Modifier.toString(method.getModifiers());
				String returnType = method.getReturnType().getSimpleName();
				String methodName = method.getName();

				getter.setModifier(modifier);
				getter.setReturnType(returnType);
				getter.setMethodName(methodName);
				getter.setFieldName(retrieveFieldName(method));

				getters.add(getter);
			}
		}
		clazzPojo.setGetters(getters);

		return clazzPojo;
	}

	private ClazzPojo settersPojoNodes(Class<?> clazz) {

		ClazzPojo clazzPojo = new ClazzPojo();

		List<SetterClazz> setters = new ArrayList<>();

		for (Method method : getMethods(clazz)) {
			SetterClazz setter = new SetterClazz();
			if (isSetter(method)) {

				String modifier = Modifier.toString(method.getModifiers());
				String returnType = method.getReturnType().getSimpleName();
				String methodName = method.getName();
				Parameter parameter = getParameters(method)[0];
				String paramType = parameter.getType().getSimpleName();
				String paramName = parameter.getName();

				setter.setModifier(modifier);
				setter.setReturnType(returnType);
				setter.setMethodName(methodName);
				setter.setFieldName(retrieveFieldName(method));
				setter.setParamType(paramType);
				setter.setParamName(paramName);

				setters.add(setter);
			}
			clazzPojo.setSetters(setters);
		}
		return clazzPojo;
	}

	private ClazzPojo methodsPojoNode(Class<?> clazz) {
		
		ClazzPojo clazzPojo = new ClazzPojo();
		List<PojoMethod> pojoMethods = new ArrayList<>();
		
		if (getMethods(clazz).length == 0) {
			clazzPojo.setNoMethods(true);
		}
		clazzPojo.setNoMethods(false);
		
		for (Method actualMethod : getMethods(clazz)) {
			
			if (!isGetter(actualMethod) && !isSetter(actualMethod)) {
				PojoMethod method = new PojoMethod();
				String modifier = Modifier.toString(actualMethod.getModifiers());
				String methodName = actualMethod.getName();
				String returnType = actualMethod.getReturnType().getSimpleName();
				
				method.setModifier(modifier);
				method.setMethodName(methodName);
				method.setReturnType(returnType);
				
				if (actualMethod.isVarArgs()) {
									
					method.setNoArgs(true);
					method.setMethodParameters(null);
					
					pojoMethods.add(method);
				}
				else {
					method.setNoArgs(false);
					List<String> methodParameters = new ArrayList<>();
					
					for (Parameter actuelParameter : getParameters(actualMethod)) {
						ParameterPojo parameter = new ParameterPojo();
						parameter.setType(actuelParameter.getType().getSimpleName());
						parameter.setParamName(actuelParameter.getName());
						methodParameters.add(parameter.templateString());
					}
					method.setMethodParameters(methodParameters);
					pojoMethods.add(method);
				}
				clazzPojo.setPojoMethods(pojoMethods);
			}

		}
		return clazzPojo;	
	}

	public void writeFile() {
		Writer file = Writer.nullWriter();

		try {

			for (Map.Entry<String, Map<String, ClazzPojo>> entry : configModel.entrySet()) {

				String packageName = "";
				String className = "";
				ClazzPojo pojo = entry.getValue().get("clazzPojo");

				packageName = pojo.getPackageName();
				className = pojo.getClassName(); // pojo.getClassName()

				packageName = packageName.replace("package ", "");

				packageName = GENERATED + packageName + "/";

				new File(packageName).mkdirs();
				file = new FileWriter(new File(packageName + className + ".java"));

				template.process(entry.getValue(), file);
				file.flush();
			}

			System.out.println("Generation Success !!!");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			try {
				file.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private boolean isGetter(Method method) {
		if (Modifier.isPublic(method.getModifiers()) && method.getParameterTypes().length == 0) {
			if (method.getName().matches("^get[A-Z].*") && !method.getReturnType().equals(void.class))
				return true;
			if (method.getName().matches("^is[A-Z].*") && method.getReturnType().equals(boolean.class))
				return true;
		}
		return false;
	}

	private boolean isSetter(Method method) {
		return Modifier.isPublic(method.getModifiers()) && method.getReturnType().equals(void.class)
				&& method.getParameterTypes().length == 1 && method.getName().matches("^set[A-Z].*");
	}

	private String retrieveFieldName(Method method) {
		String methodName = "";
		if (isGetter(method)) {
			methodName = method.getName().replaceFirst("get", "");
		}
		if (isSetter(method)) {
			methodName = method.getName().replaceFirst("set", "");
		}
		return methodName;
	}
	
	
	
	
	
	
	private String buildMethodParameters(Method method) {
		StringBuffer parameter = null;
		// Handle parameters for setters
		for (Parameter methodParam : method.getParameters()) {
			if (isSetter(method)) {
				
			}
		
		
			
		//	for (String field : fieldNames) {
			//	if (methodName.contains(field.toLowerCase())) {
					String copy = parameter.toString().replace("arg0", "field");
					parameter = new StringBuffer(copy);
				//}
			//}
		}

		// Handle other method's parameters // coming soon...

		return parameter.toString();

	}

	private void addClasstoImport(List<String> importClassList, String classToImport) {
		if (!importClassList.contains(classToImport))
			importClassList.add(classToImport);
	}

	private String extractType(String type) {
		return type.substring(type.lastIndexOf('.') + 1);
	}

}
