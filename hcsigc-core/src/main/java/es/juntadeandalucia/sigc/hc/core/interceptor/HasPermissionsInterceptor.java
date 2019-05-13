package es.juntadeandalucia.sigc.hc.core.interceptor;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.picketlink.Identity;

import es.juntadeandalucia.sigc.hc.core.bo.UserBO;
import es.juntadeandalucia.sigc.hc.core.controller.impl.UserController;
import es.juntadeandalucia.sigc.hc.core.interceptor.binding.HasPermissions;
import es.juntadeandalucia.sigc.hc.core.interceptor.exception.UnauthorizedException;

@Interceptor
@HasPermissions
public class HasPermissionsInterceptor implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9088081588325287645L;
	
	@Inject
	private Identity identity;
	
	@Inject
	private UserController userController;
	
	@AroundInvoke
	public Object checkUSerAllowed(InvocationContext ctx) throws Exception {
		HasPermissions annotation = getHasPermissionsAnnotation(ctx.getMethod());
		if (annotation != null) {
    		String[] requiredPermissions = annotation.value();
    		UserBO userSession = ((UserBO) identity.getAccount());
    		
    		if (userController.hasPermissions(userSession, requiredPermissions)) {
    			return ctx.proceed();
    		}
    		else {
    			throw new UnauthorizedException("User does not have the required permissions");
    		}
		}
		throw new UnauthorizedException("User dont have required role");
	}

	private HasPermissions getHasPermissionsAnnotation(Method method) {
		HasPermissions annotation = null;
		for (Annotation a: method.getAnnotations()) {
            if (a instanceof HasPermissions) { 
            	annotation = (HasPermissions) a;
            	break;
            }
        }
		if (annotation == null) {
	        for (Annotation a: method.getDeclaringClass().getAnnotations()) {
	            if (a instanceof HasPermissions) { 
	            	annotation = (HasPermissions) a;
	            	break;
	            }
	        }
		}
        return annotation;
	}
}
