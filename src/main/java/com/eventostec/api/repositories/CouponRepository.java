package com.eventostec.api.repositories;

import com.eventostec.api.domain.coupon.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface CouponRepository extends JpaRepository<Coupon, UUID> {

    public List<Coupon> findByEventIdAndValidAfter(UUID eventId, Date validAfter);
}
