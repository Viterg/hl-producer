package ru.viterg.proselyte.hlproducer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataSender {

    @Value("${application.kafka.topic}")
    private final String topic;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Async
    public Mono<Void> sendMessage(String message) {
        log.debug("Sending message [{}] to topic [{}]", message, topic);
        return Mono.fromFuture(kafkaTemplate.send(topic, message)
                .whenComplete((result, ex) -> log.debug(
                        ex == null
                        ? "Sent message=[" + message + "] with offset=[" + result.getRecordMetadata().offset() + "]"
                        : "Unable to send message=[" + message + "] due to : " + ex.getMessage()
                ))).then();
    }
}
