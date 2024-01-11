package ru.viterg.proselyte.hlproducer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgentInfo {

    private UUID uuid;

    private UUID agentId; // unique identifier of the gadget (mobile phone, PC, etc.)

    private long previousMessageTime; // unix timestamp in the time range from now -1 week

    private String activeService; // netflix, youtube, etc.

    private int qualityScore; // value from 1 to 100
}
