package com.baseware.eshop.catalog.core.monitoring;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.interceptor.PerformanceMonitorInterceptor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class PerformanceMonitoringConfig {

   @Bean
   public PerformanceMonitorInterceptor performanceMonitorInterceptor() {
      return new PerformanceMonitorInterceptor(true);
   }

   /**
    * Pointcut that matches all repositories, services and Web REST endpoints.
    */
   @Pointcut("within(@org.springframework.stereotype.Repository *)" +
       " || within(@org.springframework.stereotype.Service *)" +
       " || within(@org.springframework.web.bind.annotation.RestController *)")
   public void springBeanPointcut() {
      // Method is empty as this is just a Pointcut, the implementations are in the advices.
   }

   @Bean
   public Advisor performanceMonitorAdvisor() {
      AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
      pointcut.setExpression(PerformanceMonitoringConfig.class.getCanonicalName()
                                 .concat(".").concat("springBeanPointcut()"));
      return new DefaultPointcutAdvisor(pointcut, performanceMonitorInterceptor());
   }
}
