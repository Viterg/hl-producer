package ru.viterg.proselyte.hlproducer;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(initializers = KafkaContainerInitializer.class)
@SpringBootTest(classes = GeneratorProducerApplication.class)
public abstract class AbstractIT {
}
