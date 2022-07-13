package PojoPronto;

import java.util.List;

public class ConstructorPojo {

	@Override
	public String toString() {
		return String.format("ConstructorPojo {\n\tnoArgs=%s, \n\tmodifier=%s, \n\tconstructorName=%s, \n\tparameters=%s\n}", noArgs,
				modifier, constructorName, constParameters);
	}

	private boolean noArgs;
	private String modifier;
	private String constructorName;
	private List<ParameterPojo> constParameters;

	public boolean parameterCount() {
		return constParameters.isEmpty();
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

	public List<ParameterPojo> getConstParameters() {
		return constParameters;
	}

	public void setConstParameters(List<ParameterPojo> parameters) {
		this.constParameters = parameters;
	}

}
