package ru.viterg.proselyte.hlproducer.utils;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.awaitility.Awaitility;
import org.awaitility.core.ConditionTimeoutException;
import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONAssert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;

import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
public class TestConsumer {

    private static final Logger log = LoggerFactory.getLogger(TestConsumer.class);

    private final KafkaProperties kafkaProperties;

    public void verifyRecord(String topic, String key, String json) {
        try (Consumer<String, String> consumer = createConsumer(topic)) {
            Awaitility.await().untilAsserted(() -> {
                var records = consumer.poll(Duration.ofSeconds(10));
                Stream<ConsumerRecord<String, String>> stream = records.records(topic) instanceof Collection
                        ? ((Collection<ConsumerRecord<String, String>>) records).stream()
                        : StreamSupport.stream(records.spliterator(), false);
                stream.filter(record -> key.equals(record.key()))
                        .findFirst()
                        .ifPresent(record -> {
                            if (record.value() != null) {
                                log.info(record.value());
                                try {
                                    JSONAssert.assertEquals(record.value(), json, false);
                                } catch (JSONException e) {
                                    throw new AssertionError("JSON comparison failed", e);
                                }
                            }
                        });

            });
        } catch (ConditionTimeoutException e) {
            log.error("Timeout waiting for the record with key '{}' in topic '{}'", key, topic);
        }
    }

    private Consumer<String, String> createConsumer(String topic) {
        Map<String, Object> consumerProps = Map.of(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers(),
                ConsumerConfig.GROUP_ID_CONFIG, "tc-test-" + UUID.randomUUID(),
                ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"
        );

        Consumer<String, String> consumer = new KafkaConsumer<>(consumerProps,
                new StringDeserializer(),
                new StringDeserializer());
        consumer.subscribe(List.of(topic));
        return consumer;
    }
}
