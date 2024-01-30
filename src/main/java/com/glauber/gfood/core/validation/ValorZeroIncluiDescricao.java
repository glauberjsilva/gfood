package com.glauber.gfood.core.validation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = { ValorZeroIncluiDescricaoValidator.class })
public @interface ValorZeroIncluiDescricao {

	String message() default "descriação obrigatória inválida";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	String valorField();

	String descricaoField();

	String descricaoObrigatoria();
}
