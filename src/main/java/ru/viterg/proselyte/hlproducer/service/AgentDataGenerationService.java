package ru.viterg.proselyte.hlproducer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import ru.viterg.proselyte.hlproducer.configuration.GenerationConfig;
import ru.viterg.proselyte.hlproducer.model.Agent;
import ru.viterg.proselyte.hlproducer.model.AgentInfo;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class AgentDataGenerationService {

    private final DataSender dataSender;
    private final AgentGenerator agentGenerator;
    private final GenerationConfig config;
    private final ObjectMapper objectMapper;

    @Scheduled(fixedRate = 1L, timeUnit = TimeUnit.MINUTES)
    public void initSendingNewData() {
        Flux.fromIterable(agentGenerator.getAgents())
                .flatMap(agent -> Flux.range(0, config.getCountPerMinute())
                        .flatMap(i -> dataSender.sendMessage(prepareMessage(agent)))
                        .doOnNext(result -> log.info("Message was sent, result: {}", result.recordMetadata()))
                )
                .subscribe();
    }

    private String prepareMessage(Agent agent) {
        String message = "";
        try {
            AgentInfo agentInfo = AgentInfo.withRandomData(agent.agentId());
            message = objectMapper.writeValueAsString(agentInfo);
        } catch (JsonProcessingException e) {
            log.warn("Can't prepare message: {}", e.getMessage());
        }
        return message;
    }
}
