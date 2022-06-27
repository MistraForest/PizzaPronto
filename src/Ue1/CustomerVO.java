package Ue1;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CustomerVO {
	
	private String lastName;
	private String firstName;
	private String gender;
	private LocalDate dateOfBirth;
	
	public CustomerVO(String lastName, String firstName, String gender, LocalDate dob) {
		this.lastName = lastName;
		this.firstName = firstName;
		this.gender = gender;
		this.dateOfBirth = dob;
	}

	public CustomerVO(String lastName, String firstName, LocalDate dateOfBirth) {
		this.setLastName(lastName);
		this.setFirstName(firstName);
		this.setDateOfBirth(dateOfBirth);
		this.setGender("M");
	}

	public CustomerVO() {
		this("Mistra", "Forest", LocalDate.of(2000, 6, 13));
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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
	// Siehe Uebung2 für die toString Methode
	public String dobToString() {
		return this.dateOfBirth.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
	}
	
}
