package ru.viterg.proselyte.hlproducer.configuration;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "application.generation")
public class GenerationConfig {

    @Positive
    @Max(value = 10L, message = "Count of messages per minute must not exceed 10")
    private int countPerMinute;

    @Positive
    @Max(value = 100_000L, message = "Count of agents must not exceed 100000")
    private int countOfAgents;
}
