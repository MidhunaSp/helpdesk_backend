package com.springboot.helpdesk.repository;

import com.springboot.helpdesk.dto.response.TicketDetailsDto;
import com.springboot.helpdesk.model.Executive;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExecutiveRepository extends JpaRepository<Executive,Long> {


}
