package com.baseware.eshop.catalog.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.problem.jackson.ProblemModule;
import org.zalando.problem.violations.ConstraintViolationProblemModule;

@Configuration
public class JacksonConfiguration {

  @Bean
  public ObjectMapper objectMapper() {
     ObjectMapper objectMapper = new ObjectMapper();

     objectMapper.registerModule(new JavaTimeModule());
     objectMapper.registerModule(new ProblemModule());
     objectMapper.registerModule(new ConstraintViolationProblemModule());
     return objectMapper;
  }
}
