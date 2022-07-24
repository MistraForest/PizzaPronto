package PojoPronto;

import java.util.List;

public class PojoMethod {

	private boolean noArgs;
	private String modifier;
	private String returnType;
	private String methodName;
	private Object body;
	private List<String> methodParameters;

	public boolean isNoArgs() {
		return noArgs;
	}

	public void setNoArgs(boolean noArgs) {
		this.noArgs = noArgs;
	}

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

	public Object getBody() {
		return body;
	}

	public void setBody(Object body) {
		this.body = body;
	}

	public List<String> getMethodParameters() {
		return methodParameters;
	}

	public void setMethodParameters(List<String> methodParameters) {
		this.methodParameters = methodParameters;
	}

	@Override
	public String toString() {
		return String.format(
				"PojoMethod {\nnoArgs=%s, \nmodifier=%s, \nreturnType=%s, \nmethodName=%s, \nbody=%s, \nmethodParameters=%s\n}",
				noArgs, modifier, returnType, methodName, body, methodParameters);
	}
}
