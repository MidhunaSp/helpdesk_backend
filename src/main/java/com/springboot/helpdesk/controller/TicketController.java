package com.springboot.helpdesk.controller;

import com.springboot.helpdesk.dto.request.TicketDto;
import com.springboot.helpdesk.dto.response.TicketDetailsDto;
import com.springboot.helpdesk.dto.response.TicketInfoDto;
import com.springboot.helpdesk.dto.response.TicketRespDto;
import com.springboot.helpdesk.dto.response.TicketStatusStatDto;
import com.springboot.helpdesk.enums.Priority;
import com.springboot.helpdesk.enums.Status;
import com.springboot.helpdesk.model.Ticket;
import com.springboot.helpdesk.service.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @PostMapping("/api/ticket/add")
    public void addTicket(Principal principal, @Valid @RequestBody TicketDto ticketDto) {
        ticketService.add(principal.getName(), ticketDto);
    }

    @PutMapping("/api/ticket/assign/{executiveId}/{ticketId}")
    public void assignExecutive(@PathVariable Long executiveId,
                                @PathVariable Long ticketId,
                                Principal principal) {
        ticketService.assignExecutive(executiveId, ticketId, principal.getName());
    }

    @GetMapping("/api/ticket/v1/{customerId}")
    public List<TicketInfoDto> getTicketsByCustomerId(@PathVariable Long customerId,
                                                      @RequestParam("page") int page,
                                                      @RequestParam("size") int size){
        return ticketService.getTicketsByCustomerId(customerId,page,size);
    }

    @GetMapping("/api/ticket/v2")
    public List<TicketInfoDto> getTicketsByCustomerUsername(@RequestParam("username") String customerUsername,
                                                            @RequestParam(name = "page" , required = false, defaultValue = "0") Integer page,
                                                            @RequestParam(name = "size", required = false, defaultValue = "20") Integer size){
        return ticketService.getTicketsByCustomerUsername(customerUsername,page,size);
    }
     /*
    Get tickets by executive username and display following info:
    ticketId
    executiveId
    executiveName
    ticketSubject
    ticketPriority
    ticketStatus
    ticketCreatedAt
    customerId
    customerName

    Make pagination optional
    * */

    @GetMapping("/api/ticket/v3")
    public List<TicketDetailsDto> getTicketsByExecutiveUsername(@RequestParam("username") String executiveUsername,
                                                                @RequestParam(name = "page" , required = false, defaultValue = "0") Integer page,
                                                                @RequestParam (name = "size", required = false, defaultValue = "20") Integer size){
        return ticketService.getTicketsByExecutiveUsername(executiveUsername,page,size);
    }

    /*
     Ticket Status  |  Num. of Customers
     OPEN               34
     IN_PROCESS         67
     CLOSED             123
    * */
    @GetMapping("/api/ticket/status/customers/stat")
    public List<TicketStatusStatDto> getTicketStatusStatWithNumCustomers(){
        return ticketService.getTicketStatusStatWithNumCustomers();
    }

    @GetMapping("/api/ticket/by-priority")
    public List<TicketRespDto> getTicketsByPriority(@RequestParam("priority") Priority priority){
        return ticketService.getTicketsByPriority(priority);
    }

    @GetMapping("/api/ticket/by-status")
    public List<TicketRespDto> getTicketsByStatus(@RequestParam("status") Status status) {
        return ticketService.getTicketsByStatus(status);
    }

    @GetMapping("/api/ticket/by-priority-and-status")
    public List<TicketRespDto> getTicketsByPriorityAndStatus(
            @RequestParam("priority") Priority priority,
            @RequestParam("status") Status status) {
        return ticketService.getTicketsByPriorityAndStatus(priority, status);
    }

    @GetMapping("/api/ticket/filter")
    public List<TicketRespDto> dynamicFilterTicket(@RequestParam(value = "priority", required = false) Priority priority,
                                                   @RequestParam(value = "status", required = false)Status status,
                                                   @RequestParam(value = "fromDate", required = false) LocalDate fromDate,
                                                   @RequestParam(value = "toDate", required = false)LocalDate toDate
    ){
        return ticketService.dynamicFilterTicket(priority,status, fromDate, toDate);
    }

    @GetMapping("/api/tickets/by-date")
    public List<Ticket> getTicketsByDate(@RequestParam LocalDate fromDate,
                                         @RequestParam LocalDate toDate){
        Instant fromInstant = fromDate.atStartOfDay(ZoneOffset.UTC)
                .toInstant();
        Instant toInstant = toDate.plusDays(1).atStartOfDay(ZoneOffset.UTC)
                .toInstant();
        return ticketService.getTicketsByDate(fromInstant,toInstant);
    }
}
/*
    OPEN tickets
    ------------
    ticket 1 - id [Assign] -- select executive [executiveId] :- ticketId, executiveId
    ticket 2 - id [Assign]
 */