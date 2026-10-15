package com.springboot.helpdesk.dto.response;

import com.springboot.helpdesk.enums.Status;

public record TicketStatusStatDto(
        Status status,
        long numberOfCustomers
) {
}