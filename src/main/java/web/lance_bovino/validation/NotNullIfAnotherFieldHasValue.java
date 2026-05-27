package web.lance_bovino.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import web.lance_bovino.validation.validator.NotNullIfAnotherFieldHasValueValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
 * Validates that field {@code dependFieldName} is not null if field
 * {@code fieldName} has value {@code fieldValue}.
 */
@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotNullIfAnotherFieldHasValueValidator.class)
@Documented
public @interface NotNullIfAnotherFieldHasValue {
	
	String fieldName();
	String fieldValue();
	String dependFieldName();

	String message() default "{NotNullIfAnotherFieldHasValue.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	@interface List {
		NotNullIfAnotherFieldHasValue[] value();
	}
}