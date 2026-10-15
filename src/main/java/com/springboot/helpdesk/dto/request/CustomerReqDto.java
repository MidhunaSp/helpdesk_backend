package com.springboot.helpdesk.dto.request;

import jakarta.validation.constraints.*;

public record CustomerReqDto(
        @NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = "City is required")
        String city,

        @NotBlank(message = "Email is required")
        @Email(message = "Email seems invalid")
        String email,

        @NotBlank(message = "Username is required")
        @Size(min = 3, max = 15, message = "Username should be 3 to 15 characters")
        String username,

        @NotBlank(message = "Password is required")
        String password
) {
}
