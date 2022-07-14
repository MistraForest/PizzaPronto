package PojoPronto;

import java.util.List;

public class PojoMethod {

	private boolean noArgs;
	private String modifier;
	private String returnType;
	private String methodName;
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

	public List<String> getMethodParameters() {
		return methodParameters;
	}

	public void setMethodParameters(List<String> methodParameters) {
		this.methodParameters = methodParameters;
	}

}
