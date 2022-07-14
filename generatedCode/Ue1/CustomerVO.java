
<# BINDING ELEMENTS WITH POJO CLASS>

package Ue1;


public class CustomerVO {

	private java.lang.String lastName;
	private java.lang.String firstName;
	private java.lang.String gender;
	private java.time.LocalDate dateOfBirth;
	
	
	public Ue1.CustomerVO(){
		
	}

	public Ue1.CustomerVO(String arg0, String arg1, LocalDate arg2){
		
	}
	

	public Ue1.CustomerVO(String arg0, String arg1, String arg2, LocalDate arg3){
		
	}
	

	
	public void setDateOfBirth(java.time.LocalDate arg0){
		this.dateOfBirth = arg0;
	}
	public void setLastName(java.lang.String arg0){
		this.lastName = arg0;
	}
	public void setGender(java.lang.String arg0){
		this.gender = arg0;
	}
	public void setFirstName(java.lang.String arg0){
		this.firstName = arg0;
	}
	
	public String getGender(){
		return gender;
	}
	public LocalDate getDateOfBirth(){
		return dateOfBirth;
	}
	public String getFirstName(){
		return firstName;
	}
	public String getLastName(){
		return lastName;
	}
}

