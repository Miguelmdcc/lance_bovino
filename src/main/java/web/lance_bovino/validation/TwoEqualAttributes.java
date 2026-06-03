package web.lance_bovino.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import web.lance_bovino.validation.validator.TwoEqualAttributesValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = TwoEqualAttributesValidator.class)
@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TwoEqualAttributes {
	String message() default "Os valores são diferentes";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
	
	String attribute1();
	String attribute2();

	@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	@interface List
	{
		TwoEqualAttributes[] value();
	}
}