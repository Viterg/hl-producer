package ru.viterg.proselyte.hlproducer.model;

import java.util.UUID;

public record Agent(
        UUID agentId,
        String manufacturer, // sony, panasonic, etc.
        String os // IOS, Windows 11, etc.
) {}
