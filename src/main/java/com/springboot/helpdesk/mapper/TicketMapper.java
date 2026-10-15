package com.springboot.helpdesk.mapper;

import com.springboot.helpdesk.dto.request.TicketDto;
import com.springboot.helpdesk.dto.response.TicketRespDto;
import com.springboot.helpdesk.model.Ticket;
import org.springframework.stereotype.Component;

@Component
public class TicketMapper {

    public static Ticket convertDtoToEntity(TicketDto ticketDto) {
        Ticket ticket = new Ticket();
        ticket.setSubject(ticketDto.subject());
        ticket.setIssue(ticketDto.issue());
        ticket.setPriority(ticketDto.priority());
        return ticket;
    }

    public static TicketRespDto convertEntityToDto(Ticket ticket){
        return
                new TicketRespDto (
                        ticket.getId(),
                        ticket.getSubject(),
                        ticket.getCreatedAt(),
                        ticket.getPriority(),
                        ticket.getStatus()
                );
    }
}
