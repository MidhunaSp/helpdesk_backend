package com.springboot.helpdesk.dto.request;

import com.springboot.helpdesk.enums.PlanType;
import jakarta.validation.constraints.NotNull;

public record PlanReqDto(
        @NotNull
        PlanType planType,
        double price
) {
}
