
<# BINDING ELEMENTS WITH POJO CLASS>

package Ue1;


public class ChefVO {

	private java.lang.String lastName;
	private java.lang.String firstName;
	private java.awt.Color colorApron;
	
	public Ue1.ChefVO(String arg0, String arg1, Color arg2){
		
	}	
	public Ue1.ChefVO(){
		
	}	
	
	
	public void setLastName(String arg0){
		this.lastName = arg0;
	}
	public void setColorApron(Color arg0){
		this.colorApron = arg0;
	}
	public void setFirstName(String arg0){
		this.firstName = arg0;
	}
	
	public Color getColorApron(){
		return colorApron;
	}
	public String getFirstName(){
		return firstName;
	}
	public String getLastName(){
		return lastName;
	}
	
	public String toString(){
		
	}	
}