package PojoPronto;

import java.util.List;

public class Propertie {

	@Override
	public String toString() {
		return String.format("Propertie {\n\t\tmodifier=%s, \n\t\ttype=%s, \n\t\tpropertieName=%s, \n\t\tclazzToimport=%s\n}", modifier, type,
				propertieName, clazzToimport);
	}

	private String modifier;
	private String type;
	private String propertieName;
	private List<String> clazzToimport;

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPropertieName() {
		return propertieName;
	}

	public void setPropertieName(String propertieName) {
		this.propertieName = propertieName;
	}

	public List<String> getClazzToimport() {
		return clazzToimport;
	}

	public void setClazzToimport(List<String> clazzToimport) {
		this.clazzToimport = clazzToimport;
	}
}
