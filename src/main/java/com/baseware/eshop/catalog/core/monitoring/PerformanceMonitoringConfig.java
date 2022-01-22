package com.baseware.eshop.catalog.core.monitoring;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.interceptor.PerformanceMonitorInterceptor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

@Aspect
@Configuration
public class PerformanceMonitoringConfig {

   @Bean
   public PerformanceMonitorInterceptor performanceMonitorInterceptor() {
      return new PerformanceMonitorInterceptor(true);
   }

   /**
    * Pointcut for execution of methods on classes annotated with {@link RestController}
    * annotation
    */
   @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
   public void restControllerAnnotation() {
   }

   /**
    * Pointcut for execution of methods on classes annotated with {@link Service}
    * annotation
    */
   @Pointcut("within(@org.springframework.stereotype.Service *)")
   public void serviceAnnotation() {
   }

   /**
    * Pointcut for execution of methods on classes annotated with
    * {@link Repository} annotation
    */
   @Pointcut("within(@org.springframework.stereotype.Repository *)")
   public void repositoryAnnotation() {
   }

   @Pointcut("restControllerAnnotation() || serviceAnnotation() || repositoryAnnotation()")
   public void performanceMonitor() {
   }

   @Bean
   public Advisor performanceMonitorAdvisor() {
      AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
      pointcut.setExpression(PerformanceMonitoringConfig.class.getCanonicalName()
                                 .concat(".").concat("performanceMonitor()"));
      return new DefaultPointcutAdvisor(pointcut, performanceMonitorInterceptor());
   }
}
