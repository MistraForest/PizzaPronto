package PojoPronto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClazzPojo {

	@Override
	public String toString() {
		return String.format(
				"ClazzPojo {packageName=%s, className=%s, \nproperties=%s, \ngetters=%s, \nsetters=%s, \nconstructors=%s}",
				packageName, className, properties, getters, setters, constructors);
	}

	private String packageName;
	private String className;
	private List<Propertie> properties;
	private List<GetterClazz> getters;
	private List<SetterClazz> setters;
	private List<ConstructorPojo> constructors;

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public List<Propertie> getProperties() {
		return properties;
	}

	public void setProperties(List<Propertie> properties) {
		this.properties = properties;
	}

	public List<GetterClazz> getGetters() {
		return getters;
	}

	public void setGetters(List<GetterClazz> getters) {
		this.getters = getters;
	}

	public List<SetterClazz> getSetters() {
		return setters;
	}

	public void setSetters(List<SetterClazz> setters) {
		this.setters = setters;
	}

	public List<ConstructorPojo> getConstructors() {
		return constructors;
	}

	public void setConstructors(List<ConstructorPojo> constructors) {
		this.constructors = constructors;
	}

}
