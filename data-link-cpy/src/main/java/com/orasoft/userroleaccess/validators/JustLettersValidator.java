package com.orasoft.userroleaccess.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.orasoft.userroleaccess.annotations.JustLetters;



public class JustLettersValidator implements ConstraintValidator<JustLetters, String>{

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		// TODO Auto-generated method stub
		return value != null && value.matches("[a-zA-Z]+");
	}

}
