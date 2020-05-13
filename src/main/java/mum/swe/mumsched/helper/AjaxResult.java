package mum.swe.mumsched.helper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Elsabeth

 * @date May 10, 2020
 */
public class AjaxResult{
	private boolean isSuccess;
	private String message;
	private Object object;
	private List<AjaxFieldError> fieldErrors;
	
	public AjaxResult(){
		
	}
	
	public AjaxResult(String message){
		this.message = message;
	}
	
	public AjaxResult(String messages, boolean isSuccess){
		this.message = messages;
		this.isSuccess = isSuccess;
	}
	
	public AjaxResult(String messages, boolean isSuccess, Object object){
		this.message = messages;
		this.object = object;
		this.isSuccess = isSuccess;
	}
	
	public static AjaxResult success() {
		return new AjaxResult(null, true);
	}
	
	public static AjaxResult success(String message) {
		return new AjaxResult(message, true);
	}
	
	public static AjaxResult fail() {
		return new AjaxResult(null, false);
	}
	
	public static AjaxResult fail(String message) {
		return new AjaxResult(message, false);
	}
	
	public void addFieldError(String fieldName, String message) {
		addFieldError(new AjaxFieldError(fieldName, message));
	}
	
	public void addFieldError(AjaxFieldError fieldError) {
		if(this.fieldErrors == null)
			this.fieldErrors = new ArrayList<AjaxFieldError>();
		
		this.fieldErrors.add(fieldError);
	}
	
	public boolean getIsSuccess() {
		return isSuccess;
	}
	public void setIsSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<AjaxFieldError> getFieldErrors() {
		return fieldErrors;
	}

	public void setFieldErrors(List<AjaxFieldError> fieldErrors) {
		this.fieldErrors = fieldErrors;
	}
}
