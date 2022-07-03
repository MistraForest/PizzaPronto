package services;

import java.util.List;

import root.Pronto;

public class ClassConfiguration {
	
	private List<Object> clazz;

	public ClassConfiguration() {
	}

	public ClassConfiguration(List<Object> aClass) {
		this.setClazz(aClass);
	}

	public List<Object> getClazz() {
		return clazz;
	}

	public void setClazz(List<Object> clazz) {
		this.clazz = clazz;
	}

}
