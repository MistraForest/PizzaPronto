
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
	
	
	public void setLastName(String LastName){
		this.lastName = LastName;
	}
	public void setGender(String Gender){
		this.gender = Gender;
	}
	public void setFirstName(String FirstName){
		this.firstName = FirstName;
	}
	public void setDateOfBirth(LocalDate DateOfBirth){
		this.dateOfBirth = DateOfBirth;
	}
	
	public String getLastName(){
		return lastName;
	}
	public String getFirstName(){
		return firstName;
	}
	public LocalDate getDateOfBirth(){
		return dateOfBirth;
	}
	public String getGender(){
		return gender;
	}
	
	public String toString(){
		
	}	
	public String dobToString(){
		
	}	
	public short calculateAge(LocalDate arg0){
		
	}	
}