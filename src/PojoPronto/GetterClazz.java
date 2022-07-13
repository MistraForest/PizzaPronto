package PojoPronto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetterClazz {

	@Override
	public String toString() {
		return String.format("GetterClazz {\n\t\tmodifier=%s, \n\t\treturnType=%s, \n\t\tmethodName=%s, \n\t\tfieldName=%s\n}", modifier,
				returnType, methodName, fieldName);
	}

	private String modifier;
	private String returnType;
	private String methodName;
	private String fieldName;

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

}
