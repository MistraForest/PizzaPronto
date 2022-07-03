package Ue1;


import java.awt.Color;

public class ChefVO{
	
	private String lastName;
	private String firstName;
	private Color colorApron;
	
	public ChefVO(String lastName, String firstName, Color colorApron) {
		this.lastName = lastName;
		this.firstName = firstName;
		this.colorApron = colorApron;
	}

	public ChefVO() {
		this("Bruno", "Njo", Color.darkGray);
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public Color getColorApron() {
		return colorApron;
	}

	public void setColorApron(Color colorApron) {
		this.colorApron = colorApron;
	}
	
}
