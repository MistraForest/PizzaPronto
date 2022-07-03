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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Ue1.ChefVO;
import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;


public class GeneratorService {

	private static GeneratorService engine = new GeneratorService();
	
	private final String SPACE_1 = " ";
	private final String PARANTHESIS_OPEN = "(";
	private final String PARANTHESIS_CLOSE = ")";
	private final String CURLY_BRACKET_OPEN = "{";
	private final String CURLY_BRACKET_CLOSE = "}";
	private final String TAB = "\t";
	private final String NEW_LINE = "\n";
	private final String GENERATED = "generatedCode/";
	
	private Template template;
	
	private List<String> fieldNames = new ArrayList<String>();
	List<String> importClassList = new ArrayList<String>();
	Map<String, Object> dataModel = new HashMap<String, Object>();

	private GeneratorService() {

		init();
	}

	private void init() {

		Configuration configuration = new Configuration(new Version(2, 3, 31));

		/*
		 * configuration.setIncompatibleImprovements(new Version(2, 3, 31));
		 * configuration.setDefaultEncoding("UTF-8");
		 * configuration.setLocale(Locale.GERMAN);
		 * configuration.setTemplateExceptionHandler(TemplateExceptionHandler.
		 * RETHROW_HANDLER);
		 */
		FileTemplateLoader ftl1 = null;

		try {
			ftl1 = new FileTemplateLoader(new File("src/templates"));
			configuration.setTemplateLoader(ftl1);
			template = configuration.getTemplate("chefVO.ftl");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static GeneratorService getGenerator() {
		return engine;
	}

	public GeneratorService buildData(ChefVO chef) {
		
		Field[] declaredFields = chef.getClass().getDeclaredFields();
		String[] fieldArray = new String[declaredFields.length];
		
		Constructor<?>[] declaredConstructors = chef.getClass().getConstructors();
		
		Method[] declaredMethods = chef.getClass().getDeclaredMethods();
		String[] methodArray = new String[declaredMethods.length];

		handleClassAttributtes(declaredFields, fieldArray);
		
		List<String> constructors = handleConstructors(declaredConstructors);
	
		handleClassMethods(declaredMethods, methodArray);

		dataModel.put("package", chef.getClass().getPackage()+";");
		dataModel.put("className", chef.getClass().getSimpleName());
		dataModel.put("imports", importClassList);
		dataModel.put("properties", fieldArray);
		dataModel.put("constructors", constructors);
		dataModel.put("methods", methodArray);

		return engine;
	}

	private List<String> handleConstructors(Constructor<?>[] declaredConstructors) {
		
		StringBuffer buffer = new StringBuffer();
		List<String> constructors = new ArrayList<>();
		
		for (Constructor<?> constructor : declaredConstructors) {
			int parameterCount = constructor.getParameterCount();
			String modifier = Modifier.toString(constructor.getModifiers());
			String type = constructor.getName();
			if(parameterCount == 0) { // TODO Bessere Lösung
				
				buffer.append(modifier)
						.append(SPACE_1)
						.append(extractType(type))
						.append(PARANTHESIS_OPEN)
						.append(PARANTHESIS_CLOSE)
						.append(CURLY_BRACKET_OPEN)
						.append(NEW_LINE)
						.append(TAB)
						.append(CURLY_BRACKET_CLOSE);
				
				constructors.add(buffer.toString());
			}
			
			Parameter[] parameters = constructor.getParameters();
			buffer.append(modifier)
					.append(SPACE_1)
					.append(extractType(type))
					.append(PARANTHESIS_OPEN);
			System.out.println(constructor.getGenericParameterTypes().length);
			
			buffer.append(PARANTHESIS_CLOSE)
					.append(CURLY_BRACKET_OPEN)
					.append(NEW_LINE)
					.append(TAB)
					.append(CURLY_BRACKET_CLOSE)
					;
		}
		return constructors;
	}

	private void handleClassMethods(Method[] declaredMethods, String[] methodArray) {
		String classToImport;
		for (int i = 0; i < methodArray.length; i++) {

			StringBuffer signature = new StringBuffer();
			StringBuffer parameter = new StringBuffer();
			String type = declaredMethods[i].getGenericReturnType().getTypeName();

			signature.append(Modifier.toString(declaredMethods[i].getModifiers()))
					.append(SPACE_1)
					.append(extractType(type))
					.append(SPACE_1)
					.append(declaredMethods[i].getName())
					.append(PARANTHESIS_OPEN);

			int parameterNumber = declaredMethods[i].getParameterCount();

			if(parameterNumber != 0) {

				for (int j = 0; j < declaredMethods[i].getGenericParameterTypes().length; j++) {

					classToImport = declaredMethods[i].getGenericParameterTypes()[j].getTypeName();
					addClasstoImport(importClassList, classToImport);

					parameter.append(classToImport)
							.append(SPACE_1)
							.append(declaredMethods[i].getParameters()[j].getName());

					String methodName = declaredMethods[i].getName().toLowerCase();

					parameter = buildMethodParameters(fieldNames, parameter, methodName);
				}
			}

			signature.append(extractType(parameter.toString()))
					.append(PARANTHESIS_CLOSE)
					.append(CURLY_BRACKET_OPEN)
					.append(NEW_LINE)
					.append(TAB)
					.append(CURLY_BRACKET_CLOSE)
					.append(NEW_LINE);


			methodArray[i] = signature.toString();


		}
	}

	private void handleClassAttributtes(Field[] declaredFields, String[] fieldArray) {

		String classToImport;
		for (int i = 0; i < fieldArray.length; i++) {
			StringBuffer member = new StringBuffer();
			String type = declaredFields[i].getGenericType().getTypeName();
			member.append(Modifier.toString(declaredFields[i].getModifiers()))
					.append(SPACE_1)
					.append(extractType(type))
					.append(SPACE_1)
					.append(declaredFields[i].getName());

			fieldNames.add(declaredFields[i].getName());

			classToImport = declaredFields[i].getType().getName();

			addClasstoImport(importClassList, classToImport);
			fieldArray[i] = member.toString();
		}
	}

	private StringBuffer buildMethodParameters(List<String> fieldNames, StringBuffer parameter, String methodName) {
		
		//Handle parameters for setters
		if(methodName.startsWith("set")) {

			for (String field : fieldNames) {
				if(methodName.contains(field.toLowerCase())) {
					 String copy = parameter.toString().replace("arg0", field);
					 parameter = new StringBuffer(copy);
				}
			}
		}
		
		//Handle other method's parameters
		//coming soon...
		
		return parameter;
	}

	private void addClasstoImport(List<String> importClassList, String classToImport) {
		if(!importClassList.contains(classToImport))
			importClassList.add(classToImport);
	}

	private String extractType(String type) {
		return type.substring(type.lastIndexOf('.')+1);
	}

	public void  writeFile(){
		Writer file=null;

		try {
			/*
			 * Set<Entry<String,Object>> iterator = dataModel.entrySet();
			 * iterator.forEach(pair->{ System.out.println("Key: "+ pair.getKey()+
			 * " Value: "+pair.getValue()); });
			 */

			String packageName = dataModel.get("package").toString();
			packageName = packageName.replace(";", File.separator);

			packageName = packageName.replace("package ", "");
			packageName = GENERATED+packageName;

			new File(packageName).mkdirs();
			file = new FileWriter (new File(packageName+"Chef.java"));

			template.process(dataModel, file);
			file.flush();
			System.out.println("Success");

		}catch (Exception e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{

			try {
				file.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
