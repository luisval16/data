package com.orasoft.userroleaccess.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.orasoft.userroleaccess.annotations.ContactNumberConstraint;

public class ContactNumberValidator implements ConstraintValidator<ContactNumberConstraint, String>{

	@Override
	public boolean isValid(String contactField,
			ConstraintValidatorContext cxt) {
		// TODO Auto-generated method stub
		return contactField != null && contactField.matches("[0-9]+")
				&& (contactField.length() > 7) && (contactField.length() < 14);
	}
	

}
