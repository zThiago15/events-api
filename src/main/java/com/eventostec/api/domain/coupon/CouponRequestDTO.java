package com.eventostec.api.domain.coupon;

import java.util.Date;
import java.util.UUID;

public record CouponRequestDTO(Integer discount, String code, Long valid) {
}
