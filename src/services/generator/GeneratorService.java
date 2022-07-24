package services.generator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.visitor.VoidVisitor;

import PojoPronto.ClazzPojo;
import PojoPronto.ConstructorPojo;
import PojoPronto.GetterClazz;
import PojoPronto.ParameterPojo;
import PojoPronto.PojoMethod;
import PojoPronto.Propertie;
import PojoPronto.SetterClazz;
import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;
import services.ClassConfiguration;
import utils.ImportUtil;
import utils.MethodNameCollector;

public class GeneratorService {

	private static GeneratorService engine = new GeneratorService();

	private final String GENERATED = "generatedCode/";
	private final String TEMPLATE_FILE = "customerVO.ftl";

	private Template template;
	private Map<String, Map<String, ClazzPojo>> configModel = new HashMap<String, Map<String, ClazzPojo>>();

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

	public GeneratorService buildData(ClassConfiguration superClass) throws FileNotFoundException {

		Map<String, ClazzPojo> dataModel = new HashMap<String, ClazzPojo>();

		// BIND DATA with a POJO
		bindWithPOJO(superClass, dataModel);

		return engine;
	}

	private void bindWithPOJO(ClassConfiguration configClass, Map<String, ClazzPojo> dataModel) throws FileNotFoundException {

		for (Object actuelClass : configClass.getClazz()) {

			ClazzPojo clazzPojo = new ClazzPojo();
			String className = actuelClass.getClass().getSimpleName();
			String packageName = actuelClass.getClass().getPackage().getName();

			Path path = Path.of(packageName, className);
			System.out.println(path);

			clazzPojo.setPackageName(packageName);
			clazzPojo.setImportStatments(importLibraryNodes(actuelClass.getClass()).getImportStatments());
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


	private ClazzPojo attributesPojoNodes(Class<?> clazz) {

		ClazzPojo clazzPojo = new ClazzPojo();

		List<Propertie> properties = new ArrayList<>();
		List<String> importClazzes = new ArrayList<>();

		for (Field field : getFields(clazz)) {

			Propertie propertie = new Propertie();
			String typeName = field.getType().getSimpleName();
			String modifier = Modifier.toString(field.getModifiers());
			String propertieName = field.getName();

			propertie.setModifier(modifier);
			propertie.setType(typeName);
			propertie.setPropertieName(propertieName);
			propertie.setClazzesToimport(importClazzes);

			properties.add(propertie);

		}

		clazzPojo.setProperties(properties);

		return clazzPojo;
	}

	private ClazzPojo importLibraryNodes(Class<?> clazz) {

		ClazzPojo clazzPojo = new ClazzPojo();
		Set<String> importStatments = new HashSet<>();
		List<String> propImports = new ArrayList<>();
		List<String> methodImports = new ArrayList<>();

		Field[] fields = getFields(clazz);

		for (Field field : fields) {

			Class<?> fieldType = field.getType();

			if (!ImportUtil.isNotImportable(fieldType)) {

				String fieldTypeName = fieldType.getName();
				if (!importStatments.contains(fieldTypeName)) {
					propImports.add(fieldTypeName);
				}
			}
		}

		Method[] methods = getMethods(clazz);

		for (Method method : methods) {
			Class<?> returnType = method.getReturnType();
			if (!ImportUtil.isNotImportable(returnType)) {
				String typeName = returnType.getName();
				if (!importStatments.contains(typeName)) {
					methodImports.add(typeName);
				}
			}
		}

		if (!(importStatments.containsAll(propImports) && importStatments.containsAll(methodImports))) {
			importStatments.addAll(propImports);
			importStatments.addAll(methodImports);
		}
		clazzPojo.setImportStatments(importStatments);
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
			String name = actualConstructor.getName().replace(clazz.getPackageName() + ".", "");

			constructor.setModifier(modifier);
			constructor.setConstructorName(name);

			if (actualConstructor.isVarArgs()) {

				constructor.setNoArgs(true);
				constructor.setConstructorParameters(null);

				constructors.add(constructor);
			} else {
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
				String paramName = retrieveFieldName(method);
				paramName = unCapFirstLetter(paramName);

				setter.setModifier(modifier);
				setter.setReturnType(returnType);
				setter.setMethodName(methodName);
				setter.setFieldName(unCapFirstLetter(retrieveFieldName(method)));
				setter.setParamType(paramType);
				setter.setParamName(paramName);

				setters.add(setter);
			}
			clazzPojo.setSetters(setters);
		}
		return clazzPojo;
	}

	private ClazzPojo methodsPojoNode(Class<?> clazz) throws FileNotFoundException {

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

				String FILE_PATH = "src/Ue1/";
				VoidVisitor<List<String>> visitor = new MethodNameCollector();
				CompilationUnit cu = StaticJavaParser.parse(new File(FILE_PATH+clazz.getSimpleName()+".java"));
				visitor.visit(cu, new ArrayList<String>());

				if (actualMethod.isVarArgs()) {

					method.setNoArgs(true);
					method.setMethodParameters(null);

					pojoMethods.add(method);
				} else {
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
				className = pojo.getClassName();

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

	private String unCapFirstLetter(String paramName) {
		paramName = Character.toLowerCase(paramName.charAt(0)) + paramName.substring(1);
		return paramName;
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

}
