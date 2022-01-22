package com.baseware.eshop.catalog.core.logging;

import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

@Aspect
@Configuration
@RequiredArgsConstructor
public class LoggingConfig {
  private final Logger log = LoggerFactory.getLogger(this.getClass());

  private final Environment env;

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
  public void loggingPointcut() {

  }

  /**
   * Advice that logs methods throwing exceptions.
   *
   * @param joinPoint join point for advice
   * @param e exception
   */
  @AfterThrowing(pointcut = "loggingPointcut()", throwing = "e")
  public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
    log.error("Exception in {}.{}() with cause = {}", joinPoint.getSignature().getDeclaringTypeName(),
              joinPoint.getSignature().getName(), e.getCause() != null? e.getCause() : "NULL");
  }

  /**
   * Advice that logs when a method is entered and exited.
   *
   * @param joinPoint join point for advice
   * @return result
   * @throws Throwable throws IllegalArgumentException
   */
  @Around("loggingPointcut()")
  public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
    if (log.isDebugEnabled()) {
      log.debug("Enter: {}.{}() with argument[s] = {}", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
    }
    try {
      Object result = joinPoint.proceed();
      if (log.isDebugEnabled()) {
        log.debug("Exit: {}.{}() with result = {}", joinPoint.getSignature().getDeclaringTypeName(),
                  joinPoint.getSignature().getName(), result);
      }
      return result;
    } catch (IllegalArgumentException e) {
      log.error("Illegal argument: {} in {}.{}()", Arrays.toString(joinPoint.getArgs()),
                joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());

      throw e;
    }
  }
}
