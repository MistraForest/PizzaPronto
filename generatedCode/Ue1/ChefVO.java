
<# BINDING ELEMENTS WITH POJO CLASS>

package Ue1;



public class ChefVO {

	private String lastName;
	private String firstName;
	private Color colorApron;
	
	public ChefVO(String arg0, String arg1, Color arg2){
		
	}	
	public ChefVO(){
		
	}	
	
	
	public void setColorApron(Color ColorApron){
		this.colorApron = ColorApron;
	}
	public void setLastName(String LastName){
		this.lastName = LastName;
	}
	public void setFirstName(String FirstName){
		this.firstName = FirstName;
	}
	
	public String getLastName(){
		return lastName;
	}
	public Color getColorApron(){
		return colorApron;
	}
	public String getFirstName(){
		return firstName;
	}
	
	public String toString(){
		
	}	
}