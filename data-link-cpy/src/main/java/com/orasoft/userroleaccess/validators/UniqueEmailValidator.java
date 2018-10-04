package com.orasoft.userroleaccess.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.orasoft.data.link.models.service.IUserService;
import com.orasoft.userroleaccess.annotations.UniqueEmail;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

	@Autowired
	private IUserService userDao;

	@Override
	public boolean isValid(String email, ConstraintValidatorContext context) {
		// TODO Auto-generated method stub
		Long count = (long) 1;
		if (email != null) {
			if (this.userDao != null) {
				count = this.userDao.CountByEmailEqual(email);
			}else {
				count = (long) 0;
			}
		}

		return count == 0;

	}

}
