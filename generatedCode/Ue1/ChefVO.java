
<# BINDING ELEMENTS WITH POJO CLASS>

package Ue1;

import java.awt.Color;

public class ChefVO {

	private String lastName;
	private String firstName;
	private Color colorApron;
	
	public ChefVO(String arg0, String arg1, Color arg2){
		
	}	
	public ChefVO(){
		
	}	
	
	
	public void setColorApron(Color colorApron){
		this.colorApron = colorApron;
	}
	public void setLastName(String lastName){
		this.lastName = lastName;
	}
	public void setFirstName(String firstName){
		this.firstName = firstName;
	}
	
	public String getLastName(){
		return LastName;
	}
	public String getFirstName(){
		return FirstName;
	}
	public Color getColorApron(){
		return ColorApron;
	}
	
	public String toString(){
		
	}	
}