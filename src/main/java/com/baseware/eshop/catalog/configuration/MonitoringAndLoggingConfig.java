package com.baseware.eshop.catalog.configuration;

import com.baseware.eshop.catalog.core.logging.LoggingConfig;
import com.baseware.eshop.catalog.core.monitoring.PerformanceMonitoringConfig;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({LoggingConfig.class, PerformanceMonitoringConfig.class})
public class MonitoringAndLoggingConfig {
  @Bean
  MeterRegistryCustomizer<MeterRegistry> metricsCommonTags() {
    return registry -> registry.config().commonTags("application", "catalog", "instanceId", "catalog_1");
  }
}
