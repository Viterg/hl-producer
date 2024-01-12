package ru.viterg.proselyte.hlproducer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.SenderResult;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataSender {

    private final ReactiveKafkaProducerTemplate<String, String> kafkaTemplate;

    @Value("${application.kafka.topic}")
    private String topic;

    Mono<SenderResult<Void>> sendMessage(String message) {
        log.debug("Sending message [{}] to topic [{}]", message, topic);
        return kafkaTemplate.send(topic, message);
    }
}
