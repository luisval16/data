package com.orasoft.userroleaccess.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.orasoft.userroleaccess.validators.UniqueEmailValidator;

@Documented
@Constraint(validatedBy = UniqueEmailValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueEmail {
	String message() default "The email is already registered, please use other!";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
