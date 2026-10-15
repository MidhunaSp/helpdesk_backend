package com.springboot.helpdesk.dto.response;

import com.springboot.helpdesk.enums.PlanType;

import java.time.Instant;
import java.time.LocalDate;

public record CustomerPlanRespDto(

        Long customerPlanId,
        Long customerId,
        String customerName,
        Long planId,
        PlanType planType,
        double price,
        LocalDate activationDate,
        LocalDate endDate,
        String coupon,
        double amountPaid
) {
}
