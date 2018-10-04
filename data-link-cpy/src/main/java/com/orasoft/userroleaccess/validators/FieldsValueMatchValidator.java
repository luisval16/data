package com.orasoft.userroleaccess.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapperImpl;

import com.orasoft.userroleaccess.annotations.FieldsValueMatch;

public class FieldsValueMatchValidator 
  implements ConstraintValidator<FieldsValueMatch, Object>{

	private String field;
	private String fieldMatch;
	
	public void initialize(FieldsValueMatch constraintAnnotation) {
		this.field = constraintAnnotation.field();
		this.fieldMatch = constraintAnnotation.fieldMatch();
	}
	
	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		// TODO Auto-generated method stub
		
		Object fieldValue = new BeanWrapperImpl(value)
				.getPropertyValue(field);
		Object fieldMatchValue = new BeanWrapperImpl(value)
				.getPropertyValue(fieldMatch);
		
		if (fieldValue != null) {
			return fieldValue.equals(fieldMatchValue);
		}else {
			return fieldMatchValue == null;
		}
		
		
	}

}
