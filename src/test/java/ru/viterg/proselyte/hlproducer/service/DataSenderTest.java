package ru.viterg.proselyte.hlproducer.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import ru.viterg.proselyte.hlproducer.AbstractIT;
import ru.viterg.proselyte.hlproducer.utils.TestConsumer;

import static ru.viterg.proselyte.hlproducer.utils.FileUtils.readFile;

class DataSenderTest extends AbstractIT {

    private final TestConsumer testConsumer;

    @Autowired
    DataSenderTest(KafkaProperties kafkaProperties) {
        testConsumer = new TestConsumer(kafkaProperties);
    }

    @Test
    void test() {
        testConsumer.verifyRecord("agents-data-output-v1", "id", readFile("kafka-event.json"));
    }
}