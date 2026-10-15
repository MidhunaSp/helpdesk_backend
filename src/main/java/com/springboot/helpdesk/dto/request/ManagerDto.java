package com.springboot.helpdesk.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record ManagerDto(
        @NotNull(message = "Name is required")
        @NotBlank(message = "Name is required")
        String name,
        @NotNull(message = "Username is required")
        @NotBlank(message = "Username is required")
        String username,
        @NotNull(message = "Password is required")
        @NotBlank(message = "Password is required")
        String password
) {
}