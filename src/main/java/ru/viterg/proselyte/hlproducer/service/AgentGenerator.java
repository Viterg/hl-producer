package ru.viterg.proselyte.hlproducer.service;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.viterg.proselyte.hlproducer.configuration.GenerationConfig;
import ru.viterg.proselyte.hlproducer.model.Agent;

import java.util.Set;
import java.util.UUID;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

@Slf4j
@Service
@RequiredArgsConstructor
public class AgentGenerator {

    private final GenerationConfig config;
    @Getter
    private final Set<Agent> agents;

    // TODO to spring bean factory
    @PostConstruct
    public void initSendingNewData() {
        int countOfAgents = config.getCountOfAgents();
        for (int i = 0; i < countOfAgents; i++) {
            agents.add(new Agent(UUID.randomUUID(), randomAlphabetic(3, 8), randomAlphanumeric(3, 12)));
        }
    }
}
