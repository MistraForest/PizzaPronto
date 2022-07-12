package PojoPronto;

import java.util.List;

public class ConstructorPojo {

	private boolean noArgs;
	private String modifier;
	private String constructorName;
	private List<ParameterPojo> parameters;

	public boolean parameterCount() {
		return parameters.isEmpty();
	}

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

	public String getConstructorName() {
		return constructorName;
	}

	public void setConstructorName(String constructorName) {
		this.constructorName = constructorName;
	}

	public List<ParameterPojo> getParameters() {
		return parameters;
	}

	public void setParameters(List<ParameterPojo> parameters) {
		this.parameters = parameters;
	}

}
