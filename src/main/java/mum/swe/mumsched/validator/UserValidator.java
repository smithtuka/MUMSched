package mum.swe.mumsched.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import mum.swe.mumsched.model.User;
import mum.swe.mumsched.service.UserService;

@Component
public class UserValidator implements Validator {

	 private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"  
	   + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";  
	 
    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "NotEmpty");
        

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
        
        if(!user.getUsername().trim().isEmpty())
        {
			Pattern pattern = Pattern.compile(EMAIL_PATTERN);  
			Matcher matcher = pattern.matcher(user.getUsername());  
			if (!matcher.matches()) {  
				errors.rejectValue("username", "form.signup.email.incorrect");  
			}else {
		        if (user.getId() == null && userService.findByUsername(user.getUsername()) != null) {
		            errors.rejectValue("username", "form.signup.userDuplicated");
		        }
			}
        }
		
        
        if(user.getId() == null)
        	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");

        if (!user.getPasswordConfirm().equals(user.getPassword())) {
            errors.rejectValue("passwordConfirm", "form.signup.passwordConfirm");
        }
    }
    

    public void validateAdmin(Object o, Errors errors) {
        User user = (User) o;

        //ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "NotEmpty");
        //ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "NotEmpty");

        if (!user.getPasswordConfirm().equals(user.getPassword())) {
            errors.rejectValue("passwordConfirm", "form.signup.passwordConfirm");
        }
    }
    public void validateStuden(Object o, Errors errors) {
        User user = (User) o;

        //ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "NotEmpty");
        //ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "NotEmpty");

        if (user.getPassword() != null && user.getPasswordConfirm() != null && !user.getPasswordConfirm().equals(user.getPassword())) {
            errors.rejectValue("user.passwordConfirm", "form.signup.passwordConfirm");
        }
    }
    public void validateFaculty(Object o, Errors errors) {
        User user = (User) o;

        //ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "NotEmpty");
        //ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "NotEmpty");

        if (user.getPassword() != null && user.getPasswordConfirm() != null && !user.getPasswordConfirm().equals(user.getPassword())) {
            errors.rejectValue("user.passwordConfirm", "form.signup.passwordConfirm");
        }
    }
}