package PojoPronto;

public class ParameterPojo {

	@Override
	public String toString() {
		return String.format("ParameterPojo {\n\t\ttype=%s, \n\t\tparamName=%s, \n\t\tclazzToimport=%s\n}", type, paramName, clazzToimport);
	}

	private String type;
	private String paramName;
	private String clazzToimport;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getClazzToimport() {
		return clazzToimport;
	}

	public void setClazzToimport(String clazzToimport) {
		this.clazzToimport = clazzToimport;
	}

}
