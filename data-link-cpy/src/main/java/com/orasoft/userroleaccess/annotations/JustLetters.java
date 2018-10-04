package com.orasoft.userroleaccess.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.orasoft.userroleaccess.validators.JustLettersValidator;



@Documented
@Constraint(validatedBy = JustLettersValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface JustLetters {
	String message() default "Must only contain letters!";
	Class <?>[] groups() default {};
	Class <? extends Payload>[] payload() default {};
}
