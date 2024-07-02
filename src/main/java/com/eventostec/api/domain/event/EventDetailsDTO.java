package com.eventostec.api.domain.event;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public record EventDetailsDTO(UUID id, String title, String description, Date date, String state, String city, Boolean remote, String eventUrl, String imgUrl,
                              List<CouponDTO> coupons) {
    public record CouponDTO(String code, Integer details, Date validUntil) { }
}
