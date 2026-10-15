package com.springboot.helpdesk.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CustomerPlanReqDto(
        @NotNull
        LocalDate activationDate,
        @NotNull
        LocalDate endDate,
        String coupon,
        double amountPaid
) {
}