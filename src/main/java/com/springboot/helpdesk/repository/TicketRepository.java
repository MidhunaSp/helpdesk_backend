package com.springboot.helpdesk.repository;

import com.springboot.helpdesk.dto.response.*;
import com.springboot.helpdesk.enums.JobTitle;
import com.springboot.helpdesk.enums.Priority;
import com.springboot.helpdesk.enums.Status;
import com.springboot.helpdesk.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket,Long> {

    // spring creates a proxy object that implements ur interface
    // This is generated in memory, so you never see the source file
    // proxy performs every operation using pre created methods in jparepository that we can't see
    // proxy sees the Pageable parameter and adds (Sql's) LIMIT / OFFSET to the query automatically
    // Runs the query through JPA/Hibernate
    // Converts each row into a TicketInfoDto (because of select new ...) and returns the List

    // the @Query annotation tells Spring what to run, and Spring supplies the how

    @Query("""
            select new com.springboot.helpdesk.dto.response.TicketInfoDto(t.id,t.subject,
            t.createdAt,t.priority,t.status,c.name,e.name,e.email)
            from Ticket t
            JOIN t.customer c
            JOIN t.executive e
            JOIN c.user u
            where c.id = ?1
            """)

    List<TicketInfoDto> getTicketByCustomerId(Long customerId, Pageable pageable);

    @Query("""
            select new com.springboot.helpdesk.dto.response.TicketInfoDto(t.id,t.subject,
            t.createdAt,t.priority,t.status,c.name,e.name,e.email)
            from Ticket t
            LEFT JOIN t.customer c
            LEFT JOIN t.executive e
            LEFT JOIN c.user u
            where u.username = ?1
            """)

    List<TicketInfoDto> getTicketsByCustomerUsername(String customerUsername, Pageable pageable);


    @Query("""
            select new com.springboot.helpdesk.dto.response.TicketDetailsDto(t.id,e.id,
            e.name,t.subject,t.priority,t.status,t.createdAt,c.id,c.name)
            from Ticket t
            JOIN t.customer c
            JOIN t.executive e
            JOIN e.user u
            where u.username = ?1
            """)
    List<TicketDetailsDto> getTicketsByExecutiveUsername(String executiveUsername, Pageable pageable);

    @Query("""
            select t.status as status,count(c.id) as numberOfCustomers
            from Ticket t JOIN t.customer c
            group by t.status
            """)
    List<TicketStatusStatDto> getTicketStatusStatWithNumCustomers();

    // findBy -- Derived Query Methods
    // Id - findById

    List<Ticket> findByPriority(Priority priority); // select t from Ticket t where t.priority=?1
    List<Ticket> findByStatus(Status status); // select t from Ticket t where t.status=?1
    List<Ticket> findByPriorityAndStatus(Priority priority, Status status);
    // select t from Ticket t where t.priority=?1 and t.status=?2

    @Query("""
            select t.id as ticketId, t.subject as subject, t.createdAt as createdAt, t.priority as priority, t.status as status
            from Ticket t
            where (?1 IS NULL OR t.priority = ?1)
            AND  (?2 IS NULL OR t.status = ?2)
            AND (?3 IS NULL OR t.createdAt >= ?3)
            AND (?4 IS NULL OR t.createdAt <= ?4)
            """)
    List<TicketRespDto> dynamicFilterTicket(Priority priority,
                                            Status status,
                                            Instant fromDate,
                                            Instant toDate);

    // (commented out: it clashes with the @Query getTicketByExecutiveUsername below - same name & parameters)
    // List<TicketInfoDto> getTicketByExecutiveUsername(String executiveUsername, Pageable pageable);

    @Query("""
            select new com.springboot.helpdesk.dto.response.TicketInfoDto(t.id,t.subject,
            t.createdAt,t.priority,t.status,c.name,e.name,e.email)
            from Ticket t
            JOIN t.customer c
            JOIN t.executive e
            JOIN c.user u
            where u.username = ?1
            """)
    List<TicketInfoDto> getTicketByCustomerUsername(String customerUsername, Pageable pageable);

    @Query("""
        select new com.springboot.helpdesk.dto.response.ExecutiveTicketInfoDto(
        t.id,
        e.id,
        e.name,
        t.subject,
        t.priority,
        t.status,
        t.createdAt,
        c.id,
        c.name)
        from Ticket t
        JOIN t.executive e
        JOIN e.user u
        JOIN t.customer c
        JOIN e.manager m
        where u.username = ?1
        """)
    List<TicketInfoDto> getTicketByExecutiveUsername(
            String executiveUsername,
            Pageable pageable);

    @Query("""
        SELECT T.status AS status,
               COUNT(DISTINCT C.id) AS numberOfCustomers
        FROM Ticket T
        LEFT JOIN T.customer C
        GROUP BY T.status
        """)
    List<JobTitleExecutiveCountDto> getTicketStatusCustomerCount();

    List<Ticket> findByCreatedAtBetween(Instant fromInstant, Instant toInstant);
}


// by id
//@Query("""
//            select new com.springboot.helpdesk.dto.response.TicketInfoDto(t.id,t.subject,
//            t.createdAt,t.priority,t.status,c.name,e.name,e.email)
//            from Ticket t
//            JOIN t.customer c
//            JOIN t.executive e
//            JOIN c.user u
//            where u.username = ?1
//            """)


// It keeps your code to declarations only: you say what you want and Spring writes the boilerplate (the EntityManager calls, query creation, result mapping, transactions).
// The proxy is created using Java's dynamic proxy mechanism. That works on interfaces, which is why Spring Data repositories are interfaces.