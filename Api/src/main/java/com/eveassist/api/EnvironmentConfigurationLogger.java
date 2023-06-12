package com.eveassist.api;

import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.StreamSupport;

@Component
public class EnvironmentConfigurationLogger {

    private static final Logger LOGGER = LoggerFactory.getLogger(EnvironmentConfigurationLogger.class);

    @SuppressWarnings("rawtypes")
    @EventListener
    public void handleContextRefresh(@NotNull ContextRefreshedEvent event) {
        final Environment environment = event.getApplicationContext().getEnvironment();
        LOGGER.info("====== Environment and configuration ======");
        LOGGER.info("Active profiles: {}", Arrays.toString(environment.getActiveProfiles()));
        final MutablePropertySources sources = ((AbstractEnvironment) environment).getPropertySources();
        StreamSupport.stream(sources.spliterator(), false).filter(ps -> ps instanceof EnumerablePropertySource)
                .map(ps -> ((EnumerablePropertySource) ps).getPropertyNames()).flatMap(Arrays::stream).distinct()
                .forEach(prop -> LOGGER.info("[{}] - [{}]", prop,
                        prop.toUpperCase().contains("SECRET") || prop.toUpperCase().contains("PASSWORD") ? "*****"
                                : environment.getProperty(prop)));
        LOGGER.info("===========================================");
    }
}
