package com.springboot.helpdesk.service;

import com.springboot.helpdesk.dto.request.TicketDto;
import com.springboot.helpdesk.dto.response.TicketDetailsDto;
import com.springboot.helpdesk.dto.response.TicketInfoDto;
import com.springboot.helpdesk.dto.response.TicketRespDto;
import com.springboot.helpdesk.dto.response.TicketStatusStatDto;
import com.springboot.helpdesk.enums.Priority;
import com.springboot.helpdesk.enums.Status;
import com.springboot.helpdesk.exception.OwnershipException;
import com.springboot.helpdesk.exception.ResourceNotFoundException;
import com.springboot.helpdesk.mapper.TicketMapper;
import com.springboot.helpdesk.model.Customer;
import com.springboot.helpdesk.model.Executive;
import com.springboot.helpdesk.model.Ticket;
import com.springboot.helpdesk.repository.CustomerRepository;
import com.springboot.helpdesk.repository.ExecutiveRepository;
import com.springboot.helpdesk.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final CustomerRepository customerRepository;
    private final ExecutiveRepository executiveRepository;

    public void add(String customerUsername, TicketDto ticketDto) {

        // Step 1: Fetch Customer Object from DB using given customerId
        // Step 2: Map TicketDto to Ticket entity
        // Step 3: Attach customer to Ticket
        // Step 3.5: Assign Status as Open to Ticket
        // Step 4: Insert Ticket in DB

        Customer customer =  customerRepository.getByUsername(customerUsername);

        System.out.println("i am at line 46");
        Ticket ticket = TicketMapper.convertDtoToEntity(ticketDto);

        System.out.println("i am at line 49");
        ticket.setCustomer(customer);

        System.out.println("i am at line 52");
        ticket.setStatus(Status.OPEN);

        System.out.println("i am at line 55");
        ticketRepository.save(ticket);

    }

    public void assignExecutive(Long executiveId, Long ticketId, String managerUsername) {

        // Step 1: Fetch executive from DB using executiveId
        // Step 2: Fetch ticket from DB using ticketId
        // Step 3: Attach executive to ticket
        // Step 4: Save ticket

        Executive executive = executiveRepository.findById(executiveId)
                .orElseThrow(() -> new ResourceNotFoundException("Executive id invalid & Executive not found"));

        // Step 1.5 Does this executive belong to the team of logged-in manager?
        if(! (executive.getManager().getUser().getUsername().equals(managerUsername))){
            throw new OwnershipException("Operation not allowed! contact admin");
        }

        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(()-> new ResourceNotFoundException("Ticket not found"));

        ticket.setExecutive(executive);

        ticketRepository.save(ticket);
    }

    public List<TicketInfoDto> getTicketsByCustomerId(Long customerId, int page, int size) {
        //validate customer id
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer Id Invalid"));

        // Create an Instance of Pageable interface using page and size values thru PageRequest
        Pageable pageable =  PageRequest.of(page,size);
        return ticketRepository.getTicketByCustomerId(customerId,pageable);
    }

    public List<TicketInfoDto> getTicketsByCustomerUsername(String customerUsername, Integer page, Integer size) {
        Pageable pageable =  PageRequest.of(page,size);
        return ticketRepository.getTicketsByCustomerUsername(customerUsername,pageable);
    }

    public List<TicketDetailsDto> getTicketsByExecutiveUsername(String executiveUsername, Integer page, Integer size) {
        Pageable pageable =  PageRequest.of(page,size);
        return ticketRepository.getTicketsByExecutiveUsername(executiveUsername,pageable);
    }

    public List<TicketStatusStatDto> getTicketStatusStatWithNumCustomers() {
        return ticketRepository.getTicketStatusStatWithNumCustomers();
    }

    public List<TicketRespDto> getTicketsByPriority(Priority priority) {
        List<Ticket> list = ticketRepository.findByPriority(priority);
        // Map List<Ticket> to List<TicketDto> using Mapper
        return list
                .stream()
                .map(TicketMapper :: convertEntityToDto)
                .toList();
    }

    public List<TicketRespDto> getTicketsByStatus(Status status) {
        return ticketRepository.findByStatus(status)
                .stream()
                .map(TicketMapper::convertEntityToDto)
                .toList();
    }

    public List<TicketRespDto> getTicketsByPriorityAndStatus(Priority priority, Status status) {
        return ticketRepository.findByPriorityAndStatus(priority, status)
                .stream()
                .map(TicketMapper::convertEntityToDto)
                .toList();
    }

    public List<TicketRespDto> dynamicFilterTicket(Priority priority,
                                                   Status status,
                                                   LocalDate fromDate,
                                                   LocalDate toDate) {

        return ticketRepository.dynamicFilterTicket(priority,
                status,
                fromDate == null ? null: fromDate.atStartOfDay(ZoneOffset.UTC).toInstant(),
                toDate == null? null:toDate.atStartOfDay(ZoneOffset.UTC).toInstant());
    }
} // yyyy-MM-ddThh:mm:sec.nano

/*
void m1(){
    if(this == true)
        throw new ResourceNotFoundException("Customer id Invalid");
}

()->new ResourceNotFoundException("Customer id Invalid")
()->{
    if(this == true)
        throw new ResourceNotFoundException("Customer id Invalid");
}
* */