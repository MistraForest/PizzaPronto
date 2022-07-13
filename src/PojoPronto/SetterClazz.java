package PojoPronto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SetterClazz {

	@Override
	public String toString() {
		return String.format(
				"\nSetterClazz {\n\t\tmodifier=%s, \n\t\treturnType=%s, \n\t\tmethodName=%s, \n\t\tfieldName=%s, \n\t\tparamType=%s, \n\t\tparamName=%s, \n\t\tparameters=%s]",
				modifier, returnType, methodName, fieldName, paramType, paramName, parameters);
	}

	private String modifier;
	private String returnType;
	private String methodName;
	private String fieldName;
	private String paramType;
	private String paramName;
	private List<ParameterPojo> parameters;

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getParamType() {
		return paramType;
	}

	public void setParamType(String paramType) {
		this.paramType = paramType;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public List<ParameterPojo> getParameters() {
		return parameters;
	}

	public void setParameters(List<ParameterPojo> parameters) {
		this.parameters = parameters;
	}
}
