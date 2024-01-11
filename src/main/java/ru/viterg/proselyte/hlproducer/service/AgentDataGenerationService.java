package ru.viterg.proselyte.hlproducer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.viterg.proselyte.hlproducer.configuration.GenerationConfig;
import ru.viterg.proselyte.hlproducer.model.Agent;
import ru.viterg.proselyte.hlproducer.model.AgentInfo;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Set;

import static java.util.UUID.randomUUID;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

@Slf4j
@Service
@RequiredArgsConstructor
public class AgentDataGenerationService {

    private final DataSender dataSender;
    private final AgentGenerator agentGenerator;
    private final GenerationConfig config;
    private final ObjectMapper objectMapper;

    @PostConstruct
    public void initSendingNewData() {
        int messagesPerMinute = config.getCountPerMinute();
        Set<Agent> agents = agentGenerator.getAgents();
        for (Agent agent : agents) {
            try {
                sendData(agent);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void sendData(Agent agent) throws JsonProcessingException {
        AgentInfo agentInfo = new AgentInfo(randomUUID(), agent.agentId(),
                                            Instant.now().minus(1, ChronoUnit.WEEKS).toEpochMilli(),
                                            randomAlphabetic(4, 8),
                                            (int) (Math.random() * 100));
        String message = objectMapper.writeValueAsString(agentInfo);
        dataSender.sendMessage(message);
    }
}
