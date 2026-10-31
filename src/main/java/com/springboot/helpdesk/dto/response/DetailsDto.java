package com.springboot.helpdesk.dto.response;

import com.springboot.helpdesk.enums.Role;

public record DetailsDto(
        String username,
        Boolean isActive,
        Role role
) {
}
