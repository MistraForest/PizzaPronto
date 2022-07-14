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

	/*
	 * private final String SPACE_1 = " "; private final String PARANTHESIS_OPEN =
	 * "("; private final String PARANTHESIS_CLOSE = ")"; private final String
	 * CURLY_BRACKET_OPEN = "{"; private final String CURLY_BRACKET_CLOSE = "}";
	 * private final String TAB = "\t";
	 */
	private final String NEW_LINE = "\n";
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

		// FIRST STRATEGIE TO BIND CLASS DATA --> not so comfortable
//		bindWithHighTemplateAbstraction(superClass, dataModel);

		// BIND DATA with String representation of ObjectNodes --> better, must be
		// improved
//		bindWithStringObjectNode(superClass, dataModel);

		// BIND DATA with a POJO
		bindWithPOJO(superClass, dataModel);

		return engine;
	}

	private void bindWithPOJO(ClassConfiguration superClass, Map<String, ClazzPojo> dataModel) {
		// List<ClazzPojo> pojos = new ArrayList<>();

		for (Object actuelClass : superClass.getClazz()) {
			
			ClazzPojo clazzPojo = new ClazzPojo();
			String className = actuelClass.getClass().getSimpleName();
			String packageName = actuelClass.getClass().getPackage().getName();

			clazzPojo.setPackageName(packageName);
			clazzPojo.setClassName(className);
			clazzPojo.setProperties(attributesPojoNodes(actuelClass.getClass()).getProperties());
			clazzPojo.setConstructors(constructorsPojoNode(actuelClass.getClass()).getConstructors());
			clazzPojo.setGetters(gettersPojoNodes(actuelClass.getClass()).getGetters());
			clazzPojo.setSetters(settersPojoNodes(actuelClass.getClass()).getSetters());

			dataModel.put("clazzPojo", clazzPojo);
			configModel.put(actuelClass.getClass().getSimpleName(), dataModel);
			dataModel = new HashMap<String, ClazzPojo>();

		}
		System.out.println(configModel);
//		superClass.getClazz().forEach(actuelClass -> {
//			
//			String className = actuelClass.getClass().getSimpleName();
//			String packageName = actuelClass.getClass().getPackage().getName();
//			
//			clazzPojo.setPackageName(packageName);
//			clazzPojo.setClassName(className);
//			clazzPojo.setProperties(attributesPojoNodes(actuelClass.getClass()).getProperties());
//			clazzPojo.setConstructors(constructorsPojoNode(actuelClass.getClass()).getConstructors());
//			clazzPojo.setGetters(gettersPojoNodes(actuelClass.getClass()).getGetters());
//			clazzPojo.setSetters(settersPojoNodes(actuelClass.getClass()).getSetters());
//
//			pojos.add(clazzPojo);
//
//			dataModel.put("clazzPojo", clazzPojo);
//
//			configModel.add(dataModel);
//
//		});
	}

	/*
	 * private void bindWithStringObjectNode(ClassConfiguration superClass,
	 * Map<String, Object> dataModel) {
	 * 
	 * superClass.getClazz().forEach(actuelClass -> {
	 * 
	 * List<ObjectNode> clazzAttr = handleAttributesNodes(actuelClass.getClass());
	 * 
	 * // dataModel.put("properties", clazzAttr); // System.out.println(clazzAttr);
	 * List<ObjectNode> clazzGetter = handleGettersNodes(actuelClass.getClass());
	 * List<ObjectNode> clazzSetter = handleSettersNodes(actuelClass.getClass());
	 * 
	 * ArrayNode getter = array(clazzGetter).build(); ArrayNode setter =
	 * array(clazzSetter).build();
	 * 
	 * ObjectNode requestNode = object("package",
	 * actuelClass.getClass().getPackage().getName()) .with("className",
	 * actuelClass.getClass().getSimpleName()).with("classProperties",
	 * array(clazzAttr)) .with("Getters", getter).with("Setters", setter).build();
	 * JsonNode jn = null; try { jn = new
	 * ObjectMapper().readTree(requestNode.toString()); } catch
	 * (JsonProcessingException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } // System.out.println(jn);
	 * 
	 * // dataModel.put("object", jn);
	 * 
	 * 
	 * Method[] allDeclaredMethod = getMethods(actuelClass.getClass());
	 * Arrays.asList(allDeclaredMethod).stream().forEach(method -> { if
	 * (isGetter(method)) { bindGetters(dataModel, method); } bindSetters(dataModel,
	 * method); });
	 * 
	 * 
	 * // configModel.add(dataModel);
	 * 
	 * }); }
	 * 
	 * private void bindWithHighTemplateAbstraction(ClassConfiguration superClass,
	 * Map<String, Object> dataModel) { superClass.getClazz().forEach(actuelClass ->
	 * {
	 * 
	 * Field[] declaredFields = actuelClass.getClass().getDeclaredFields(); String[]
	 * fieldArray = new String[declaredFields.length];
	 * 
	 * Constructor<?>[] declaredConstructors =
	 * actuelClass.getClass().getConstructors();
	 * 
	 * Method[] declaredMethods = actuelClass.getClass().getDeclaredMethods();
	 * String[] methodArray = new String[declaredMethods.length];
	 * 
	 * handleClassAttributtes(declaredFields, fieldArray);
	 * 
	 * List<String> constructors = handleConstructors(declaredConstructors);
	 * 
	 * handleClassMethods(declaredMethods, methodArray);
	 * 
	 * bindClazzData(dataModel, actuelClass, fieldArray, methodArray, constructors);
	 * 
	 * // configModel.add(dataModel);
	 * 
	 * }); }
	 * 
	 * private void bindClazzData(Map<String, Object> dataModel, Object actuelClass,
	 * String[] fieldArray, String[] methodArray, List<String> constructors) {
	 * dataModel.put("package", actuelClass.getClass().getPackage() + ";");
	 * dataModel.put("className", actuelClass.getClass().getSimpleName());
	 * dataModel.put("someImports", importClassList); dataModel.put("props",
	 * fieldArray); dataModel.put("constrs", constructors); dataModel.put("meths",
	 * methodArray); }
	 * 
	 * private void bindSetters(Map<String, Object> dataModel, Method method) {
	 * Parameter[] parameters = getParameters(method);
	 * Arrays.asList(parameters).stream().filter(f ->
	 * isSetter(method)).forEach(parameter -> { dataModel.put("set_modifier",
	 * Modifier.toString(method.getModifiers())); dataModel.put("set_returnType",
	 * method.getReturnType().getSimpleName()); dataModel.put("setter",
	 * method.getName()); dataModel.put("field", retrieveFieldName(method));
	 * dataModel.put("paramType", parameter.getType().getSimpleName());
	 * dataModel.put("param", parameter.getName());
	 * 
	 * }); }
	 * 
	 * private void bindGetters(Map<String, Object> dataModel, Method method) {
	 * String modifier = Modifier.toString(method.getModifiers()); String returnType
	 * = method.getReturnType().getSimpleName(); String methodName =
	 * method.getName();
	 * 
	 * dataModel.put("getModifier", modifier); dataModel.put("get_returnType",
	 * returnType); dataModel.put("getter", methodName); dataModel.put("val",
	 * retrieveFieldName(method)); }
	 * 
	 */
	private Field[] getFields(Class<?> clazz) {
		Field[] allFields = clazz.getDeclaredFields();
		return allFields;
	}

	private Parameter[] getParameters(Method method) {
		Parameter[] parameters = method.getParameters();
		return parameters;
	}

	private Method[] getMethods(Class<?> actuelClass) {
		Method[] allDeclaredMethod = actuelClass.getDeclaredMethods();
		return allDeclaredMethod;
	}

	private Constructor<?>[] getConstructors(Class<?> clazz) {
		Constructor<?>[] declaredConstructors = clazz.getDeclaredConstructors();
		return declaredConstructors;
	}

	/*
	 * private List<String> handleConstructors(Constructor<?>[]
	 * declaredConstructors) {
	 * 
	 * StringBuffer buffer = new StringBuffer(); List<String> constructors = new
	 * ArrayList<>();
	 * 
	 * for (Constructor<?> constructor : declaredConstructors) {
	 * 
	 * int parameterCount = constructor.getParameterCount(); String modifier =
	 * Modifier.toString(constructor.getModifiers()); String type =
	 * constructor.getName();
	 * 
	 * if (parameterCount == 0) { // TODO Bessere Lösung
	 * 
	 * buffer.append(modifier).append(SPACE_1).append(extractType(type)).append(
	 * PARANTHESIS_OPEN)
	 * .append(PARANTHESIS_CLOSE).append(CURLY_BRACKET_OPEN).append(NEW_LINE).append
	 * (TAB) .append(CURLY_BRACKET_CLOSE);
	 * 
	 * constructors.add(buffer.toString()); }
	 * 
	 * buffer.append(modifier).append(SPACE_1).append(extractType(type)).append(
	 * PARANTHESIS_OPEN);
	 * 
	 * buffer.append(PARANTHESIS_CLOSE).append(CURLY_BRACKET_OPEN).append(NEW_LINE).
	 * append(TAB) .append(CURLY_BRACKET_CLOSE); } return constructors; }
	 * 
	 * private void handleClassMethods(Method[] declaredMethods, String[]
	 * methodArray) {
	 * 
	 * String classToImport; for (int i = 0; i < methodArray.length; i++) {
	 * 
	 * StringBuffer signature = new StringBuffer(); StringBuffer parameter = new
	 * StringBuffer(); String type =
	 * declaredMethods[i].getGenericReturnType().getTypeName();
	 * 
	 * signature.append(Modifier.toString(declaredMethods[i].getModifiers())).append
	 * (SPACE_1)
	 * .append(extractType(type)).append(SPACE_1).append(declaredMethods[i].getName(
	 * )) .append(PARANTHESIS_OPEN);
	 * 
	 * int parameterNumber = declaredMethods[i].getParameterCount();
	 * 
	 * if (parameterNumber != 0) {
	 * 
	 * for (int j = 0; j < declaredMethods[i].getGenericParameterTypes().length;
	 * j++) {
	 * 
	 * classToImport =
	 * declaredMethods[i].getGenericParameterTypes()[j].getTypeName();
	 * addClasstoImport(importClassList, classToImport);
	 * 
	 * parameter.append(classToImport).append(SPACE_1)
	 * .append(declaredMethods[i].getParameters()[j].getName());
	 * 
	 * String methodName = declaredMethods[i].getName().toLowerCase();
	 * 
	 * parameter = buildMethodParameters(fieldNames, parameter, methodName); } }
	 * 
	 * signature.append(extractType(parameter.toString())).append(PARANTHESIS_CLOSE)
	 * .append(CURLY_BRACKET_OPEN)
	 * .append(NEW_LINE).append(TAB).append(CURLY_BRACKET_CLOSE).append(NEW_LINE);
	 * 
	 * methodArray[i] = signature.toString();
	 * 
	 * } }
	 * 
	 * private void handleClassAttributtes(Field[] declaredFields, String[]
	 * fieldArray) {
	 * 
	 * String classToImport; for (int i = 0; i < fieldArray.length; i++) {
	 * 
	 * StringBuffer member = new StringBuffer(); String type =
	 * declaredFields[i].getGenericType().getTypeName();
	 * 
	 * member.append(Modifier.toString(declaredFields[i].getModifiers())).append(
	 * SPACE_1).append(extractType(type))
	 * .append(SPACE_1).append(declaredFields[i].getName());
	 * 
	 * fieldNames.add(declaredFields[i].getName());
	 * 
	 * classToImport = declaredFields[i].getType().getName();
	 * 
	 * addClasstoImport(importClassList, classToImport); fieldArray[i] =
	 * member.toString(); } }
	 * 
	 * private List<String> handleClassAttributtes(Field[] declaredFields) {
	 * 
	 * List<String> fieldsForClazz = new ArrayList<String>(); StringBuffer member =
	 * new StringBuffer(); for (Field field : declaredFields) { String type =
	 * field.getGenericType().getTypeName();
	 * 
	 * member.append(Modifier.toString(field.getModifiers())).append(SPACE_1).append
	 * (extractType(type))
	 * .append(SPACE_1).append(field.getName()).append(NEW_LINE).append(TAB);
	 * 
	 * } fieldsForClazz.add(member.toString());
	 * 
	 * return fieldsForClazz; }
	 * 
	 * private List<ObjectNode> handleAttributesNodes(Class<?> clazz) {
	 * 
	 * Field[] declaredFields = clazz.getDeclaredFields(); ObjectNode fieldsForClazz
	 * = null; List<ObjectNode> nodes = new ArrayList<ObjectNode>();
	 * 
	 * for (Field field : declaredFields) { String type =
	 * field.getGenericType().getTypeName(); fieldsForClazz = object("modifier",
	 * Modifier.toString(field.getModifiers())).with("type", extractType(type))
	 * .with("field", field.getName()).build();
	 * 
	 * nodes.add(fieldsForClazz); } // System.out.println(fieldsForClazz);
	 * 
	 * return nodes; }
	 * 
	 * private List<ObjectNode> attributesNodes(Class<?> clazz) {
	 * 
	 * Field[] declaredFields = clazz.getDeclaredFields(); ObjectNode fieldsForClazz
	 * = null; List<ObjectNode> nodes = new ArrayList<ObjectNode>();
	 * 
	 * for (Field field : declaredFields) {
	 * 
	 * String type = field.getGenericType().getTypeName(); String modifier =
	 * Modifier.toString(field.getModifiers()); String propertieName =
	 * field.getName();
	 * 
	 * fieldsForClazz = object("modifier", modifier).with("type",
	 * extractType(type)).with("field", propertieName) .build();
	 * 
	 * nodes.add(fieldsForClazz); }
	 * 
	 * return nodes; }
	 * 
	 * private List<ObjectNode> handleGettersNodes(Class<?> clazz) {
	 * 
	 * List<ObjectNode> nodes = new ArrayList<ObjectNode>();
	 * 
	 * for (Method method : getMethods(clazz)) {
	 * 
	 * if (isGetter(method)) { ObjectNode getter = null; String modifier =
	 * Modifier.toString(method.getModifiers()); String returnType =
	 * method.getReturnType().getSimpleName(); String methodName = method.getName();
	 * getter = object("modifier", modifier).with("returnType",
	 * extractType(returnType)) .with("name", methodName).with("fieldName",
	 * retrieveFieldName(method)).build();
	 * 
	 * nodes.add(getter); }
	 * 
	 * } return nodes; }
	 * 
	 * private List<ObjectNode> handleSettersNodes(Class<?> clazz) {
	 * 
	 * Method[] allDeclaredMethod = getMethods(clazz); ObjectNode setter = null;
	 * List<ObjectNode> nodes = new ArrayList<ObjectNode>();
	 * 
	 * for (Method method : allDeclaredMethod) { if (isSetter(method)) { String
	 * modifier = Modifier.toString(method.getModifiers()); String returnType =
	 * method.getReturnType().getSimpleName(); String methodName = method.getName();
	 * Parameter parameter = getParameters(method)[0]; setter = object("modifier",
	 * modifier).with("returnType", returnType).with("methodName", methodName)
	 * .with("fieldName", retrieveFieldName(method)).with("paramType",
	 * parameter.getType().getName()) .with("paramName",
	 * parameter.getName()).build(); nodes.add(setter); }
	 * 
	 * } return nodes; }
	 */
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

		for (Constructor<?> actualConstructor : getConstructors(clazz)) {

			ConstructorPojo constructor = new ConstructorPojo();

			int parameterCount = actualConstructor.getParameterCount();
			String modifier = "";
			String type = "";

			if (parameterCount == 0) {

				modifier = Modifier.toString(actualConstructor.getModifiers());
				type = actualConstructor.getName();

				constructor.setModifier(modifier);
				constructor.setConstructorName(type);
				constructor.setNoArgs(true);
				constructor.setConstructorParameters(null);

				constructors.add(constructor);
				clazzPojo.setConstructors(constructors);
			}
			if (parameterCount > 0) {

				modifier = Modifier.toString(actualConstructor.getModifiers());
				type = actualConstructor.getName();

				constructor.setModifier(modifier);
				constructor.setConstructorName(type);
				constructor.setNoArgs(false);

				List<String> parameters = new ArrayList<>();

				for (Parameter actualParameter : actualConstructor.getParameters()) {
					ParameterPojo parameter = new ParameterPojo();
					parameter.setType(actualParameter.getType().getSimpleName());
					parameter.setParamName(actualParameter.getName());
					//String paramet = parameter.getType().concat(" "+parameter.getParamName());
					parameters.add(parameter.templateString());
				}
				constructor.setConstructorParameters(parameters);
				
				constructors.add(constructor);
				//System.out.println(clazz.getSimpleName()+" constructor:"+constructor);
			}

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
				getter.setReturnType(extractType(returnType));
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
				String paramType = parameter.getType().getName();
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

	/*
	 * private StringBuffer buildMethodParameters(List<String> fieldNames,
	 * StringBuffer parameter, String methodName) {
	 * 
	 * // Handle parameters for setters if (methodName.startsWith("set")) {
	 * 
	 * for (String field : fieldNames) { if
	 * (methodName.contains(field.toLowerCase())) { String copy =
	 * parameter.toString().replace("arg0", field); parameter = new
	 * StringBuffer(copy); } } }
	 * 
	 * // Handle other method's parameters // coming soon...
	 * 
	 * return parameter; }
	 * 
	 * private void addClasstoImport(List<String> importClassList, String
	 * classToImport) { if (!importClassList.contains(classToImport))
	 * importClassList.add(classToImport); }
	 */

	private String extractType(String type) {
		return type.substring(type.lastIndexOf('.') + 1);
	}

	public void writeFile() {
		Writer file = Writer.nullWriter();
		
		try {
			for(Map.Entry<String, Map<String, ClazzPojo>> entry: configModel.entrySet()) {
				String packageName = "";
				String className = "";
				ClazzPojo pojo = entry.getValue().get("clazzPojo");
				
				//System.out.println(entry.getValue());
				
				packageName = pojo.getPackageName();
				className = pojo.getClassName(); //pojo.getClassName()
				
				packageName = packageName.replace("package ", "");

				packageName = GENERATED + packageName + "/";

				new File(packageName).mkdirs();
				file = new FileWriter(new File(packageName + className + ".java"));
				
				template.process(entry.getValue(), file);
				file.flush();
			}
		//	for (Map<String, ClazzPojo> model : configModel) {
				
				
				// Handling when using ObjectNode
//				String obj = model.get("object").toString();
//				JsonNode node = new ObjectMapper().readTree(obj);
//				String packageName = node.get("package").toString().replaceAll("\"", "");
//				String className = node.get("className").toString().replaceAll("\"", "");

				

				// packageName = model.get("package").toString();
				// className = model.get("className").toString();

				// packageName = packageName.replace(";", File.separator);

				
		//	}

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

	/*
	 * public static JsonNode parseJson(String json) throws Exception {
	 * 
	 * ObjectMapper mapper = new ObjectMapper(); JsonFactory f = new
	 * MappingJsonFactory(); JsonParser jp = null; JsonNode rootNode = null; try {
	 * jp = f.createJsonParser(json); rootNode = mapper.readTree(jp); } catch
	 * (Exception e) { throw e; } finally { if (jp != null) jp.close(); } return
	 * rootNode; }
	 */
}
