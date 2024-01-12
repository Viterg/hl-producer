package ru.viterg.proselyte.hlproducer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgentInfo {

    private UUID uuid;

    private UUID agentId; // unique identifier of the gadget (mobile phone, PC, etc.)

    private long previousMessageTime; // unix timestamp in the time range from now -1 week

    private String activeService; // netflix, youtube, etc.

    private int qualityScore; // value from 1 to 100

    public static AgentInfo withRandomData(UUID agentId) {
        return new AgentInfo(randomUUID(), agentId,
                Instant.now().minus(7L, ChronoUnit.DAYS).toEpochMilli(),
                randomAlphabetic(4, 8),
                (int) (Math.random() * 100));
    }
}
