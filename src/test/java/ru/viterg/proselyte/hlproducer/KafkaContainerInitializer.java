package ru.viterg.proselyte.hlproducer;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.TimeZone;

class KafkaContainerInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static final String DOCKER_IMAGE_KAFKA = "confluentinc/cp-kafka:7.3.2";
    private static final KafkaContainer kafka = new KafkaContainer(DockerImageName.parse(DOCKER_IMAGE_KAFKA));

    static {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        kafka.start();
        Runtime.getRuntime().addShutdownHook(new Thread(kafka::stop));
    }

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        TestPropertyValues
                .of("spring.kafka.bootstrap-servers=" + kafka.getBootstrapServers())
                .applyTo(applicationContext.getEnvironment());
    }
}

