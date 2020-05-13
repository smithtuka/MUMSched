package mum.swe.mumsched.view;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class ForgotPasswordView {

	@Email(message = "*Please provide a valid Email")
	@NotEmpty(message = "*Please provide an email")
    private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}	
}
