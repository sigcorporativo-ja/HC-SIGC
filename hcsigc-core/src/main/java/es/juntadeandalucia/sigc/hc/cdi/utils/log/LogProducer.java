package es.juntadeandalucia.sigc.hc.cdi.utils.log;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LogProducer {

   @Produces
   public Log createLog(InjectionPoint injectionPoint) {
      return LogFactory.getLog(injectionPoint.getMember().getDeclaringClass().getName());
   }
}
