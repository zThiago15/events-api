package com.eventostec.api.domain.event;

import java.util.Date;
import java.util.UUID;

public record EventResponseDTO(UUID id, String title, String description, Date date, String state, String city, Boolean remote, String eventUrl, String imgUrl) { }
