package es.juntadeandalucia.sigc.hc.core.interceptor.binding;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.enterprise.util.Nonbinding;
import javax.interceptor.InterceptorBinding;

@Retention(RUNTIME)
@Target({ TYPE, FIELD, METHOD, PARAMETER })
@InterceptorBinding
public @interface HasPermissions {
	@Nonbinding
	String[] value() default {};
}
