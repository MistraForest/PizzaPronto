
<# BINDING ELEMENTS WITH POJO CLASS>

package Ue1;



public class CustomerVO {

	private String lastName;
	private String firstName;
	private String gender;
	private LocalDate dateOfBirth;
	
	public CustomerVO(){
		
	}	
	public CustomerVO(String arg0, String arg1, LocalDate arg2){
		
	}	
	public CustomerVO(String arg0, String arg1, String arg2, LocalDate arg3){
		
	}	
	
	
	public void setGender(String gender){
		this.gender = gender;
	}
	public void setLastName(String lastName){
		this.lastName = lastName;
	}
	public void setFirstName(String firstName){
		this.firstName = firstName;
	}
	public void setDateOfBirth(LocalDate dateOfBirth){
		this.dateOfBirth = dateOfBirth;
	}
	
	public String getLastName(){
		return LastName;
	}
	public String getFirstName(){
		return FirstName;
	}
	public String getGender(){
		return Gender;
	}
	public LocalDate getDateOfBirth(){
		return DateOfBirth;
	}
	
	public String toString(){
		
	}	
	public short calculateAge(LocalDate arg0){
		
	}	
	public String dobToString(){
		
	}	
}