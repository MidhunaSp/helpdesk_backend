package com.springboot.helpdesk.dto.request;

import com.springboot.helpdesk.enums.PlanType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PlanDto(
        @NotBlank(message = "Plan name is required")
        @NotNull
        String planName,
        PlanType planType,
        @NotNull(message = "plan price is mandatory")
        Double price
) {
}
