package com.springboot.helpdesk.dto.request;

import jakarta.validation.constraints.NotBlank;

public record AdminDto(
        @NotBlank(message = "Username is required")
        String username,
        @NotBlank(message = "Password is required")
        String password
) {
}
