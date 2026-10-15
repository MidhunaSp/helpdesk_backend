package com.springboot.helpdesk.service;

import com.springboot.helpdesk.dto.request.TicketDto;
import com.springboot.helpdesk.dto.response.TicketInfoDto;
import com.springboot.helpdesk.enums.Priority;
import com.springboot.helpdesk.enums.Status;
import com.springboot.helpdesk.exception.ResourceNotFoundException;
import com.springboot.helpdesk.model.Customer;
import com.springboot.helpdesk.model.Ticket;
import com.springboot.helpdesk.repository.CustomerRepository;
import com.springboot.helpdesk.repository.TicketRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class) // Turns on Mockito: creates @Mock objects and injects them
public class TicketServiceTest {

    @InjectMocks
    private TicketService ticketService; // The REAL service being tested; Mockito puts the mocks below into it
    @Mock
    private TicketRepository ticketRepository; // fake repository (no database is touched)
    @Mock
    private CustomerRepository customerRepository; // fake repository (no database is touched)

    // ---- Valid Test----
    @Test
    public void getTicketByCustomerIdTest() {
        TicketInfoDto dto1 = new TicketInfoDto(1L,"test1", Instant.now(), Priority.LOW, Status.OPEN,"customer1","executive1","exec1@gmail.com");
        TicketInfoDto dto2 = new TicketInfoDto(2L,"test2", Instant.now(), Priority.HIGH, Status.OPEN,"customer2","executive2","exec2@gmail.com");
        TicketInfoDto dto3 = new TicketInfoDto(3L,"test3", Instant.now(), Priority.LOW, Status.OPEN,"customer3","executive3","exec3@gmail.com");
        Customer customer= new Customer();
        customer.setId(1L);

        Customer customer2= new Customer();
        customer2.setId(2L);

        // so even if we set customer.setId(2L) nothing happens ..and leaves customer2 with a null id. The test still passes because
        // the mocks don't check the entity's id..it only checks whether one customer obj is returned or not

        // Stub: "when findById(1) is called, return customer"
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(customerRepository.findById(2L)).thenReturn(Optional.of(customer2));

        // Stub: customer 1, page 0, size 5 -> 2 tickets
        when(ticketRepository.getTicketByCustomerId(1L, PageRequest.of(0, 5)))
                .thenReturn(List.of(dto1, dto2));
        // Stub: customer 2, page 0, size 5 -> 1 ticket
        when(ticketRepository.getTicketByCustomerId(2L, PageRequest.of(0, 5)))
                .thenReturn(List.of(dto3));
        // (PageRequest has equals(), so a new PageRequest.of(0,5) inside the service matches these stubs)

        // call the real service and check sizes
        assertEquals(2, ticketService.getTicketsByCustomerId(1L, 0, 5).size()); // customer 1 has 2 tickets
        assertEquals(1, ticketService.getTicketsByCustomerId(2L, 0, 5).size()); // customer 2 has 1 ticket

        // Verify: each mock method was called exactly once with these arguments (num of method invocations)
        verify(customerRepository,times(1)).findById(1L);
        verify(customerRepository,times(1)).findById(2L);

        verify(ticketRepository,times(1)).getTicketByCustomerId(1L, PageRequest.of(0, 5));
        verify(ticketRepository,times(1)).getTicketByCustomerId(2L, PageRequest.of(0, 5));

        // No more calls (only wantedNumberOfInvocations called) .. and this guards against unexpected extra DB calls
        verifyNoMoreInteractions(ticketRepository);
        verifyNoMoreInteractions(customerRepository);
    }

    // ---Invalid Test---
    @Test
    public void getTicketsByCustomerIdTest_Invalid(){
        // Stub: findById(7) blows up with ResourceNotFoundException
        // (note: realistically the repo returns Optional.empty() and the service throws, but this also works)
        when(customerRepository.findById(7L)).thenThrow(ResourceNotFoundException.class);

        // Assert: calling the service with id 7 must throw that exception
        assertThrows(ResourceNotFoundException.class,
                ()->ticketService.getTicketsByCustomerId(7L, 0, 5));

        // Verify: the customer lookup happened once
        verify(customerRepository,times(1)).findById(7L);

        // Verify: ticket repo was NEVER called (service stopped at validation).
        // times(0) equals never(); the arguments (1L) are arbitrary, so this check is weak.
        // verifyNoMoreInteractions below is the stronger guarantee.
        verify(ticketRepository,times(0)).getTicketByCustomerId(1L,
                PageRequest.of(0, 5));

        // No more calls
        verifyNoMoreInteractions(ticketRepository);
        verifyNoMoreInteractions(customerRepository);
    }

    @Test
    public void saveTest(){
        /*
         * To call add method of service i need 2 things
         * 1. customerUsername
         * 2. ticketDto
         * this info comes from postman/react app via controller.
         * here , we need to define it ourself
         *
         * */
        // mock
        String customerUsername = "john";
        TicketDto ticketDto = new TicketDto("testSubject", "testIssue",Priority.HIGH);
        // Before making actual call, lets mock the repositories in that add method
        Customer customer = new Customer();
        when(customerRepository.getByUsername(customerUsername)).thenReturn(customer);

        // when this save method is call, don't go to DB and save, just assume it saved and give back any ticket class
        when(ticketRepository.save(any(Ticket.class))).thenReturn(any(Ticket.class));

        // actual call
        ticketService.add(customerUsername,ticketDto);

        // Tip: once u mock - it won't go to db but it still make a call.
        // verify if repo calls are happening only once
        verify(customerRepository,times(1)).getByUsername(customerUsername);
        verify(ticketRepository,times(1)).save(any(Ticket.class));
    }
}
