package com.springboot.helpdesk.service;

import com.springboot.helpdesk.dto.request.ExecutiveReqDto;
import com.springboot.helpdesk.dto.response.TicketDetailsDto;
import com.springboot.helpdesk.dto.response.TicketInfoDto;
import com.springboot.helpdesk.enums.Role;
import com.springboot.helpdesk.exception.ResourceNotFoundException;
import com.springboot.helpdesk.mapper.ExecutiveMapper;
import com.springboot.helpdesk.model.Executive;
import com.springboot.helpdesk.model.Manager;
import com.springboot.helpdesk.model.User;
import com.springboot.helpdesk.repository.ExecutiveRepository;
import com.springboot.helpdesk.repository.ManagerRepository;
import com.springboot.helpdesk.repository.TicketRepository;
import com.springboot.helpdesk.repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExecutiveService {

    private final ExecutiveRepository executiveRepository;
    private final ExecutiveMapper executiveMapper;
    private final UserService userService;
    private final TicketRepository ticketRepository;
    private final ManagerRepository managerRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void add(@Valid ExecutiveReqDto executiveDto, String managerUsername) {
        // Step 0: Fetch manager from DB using managerId
//        Optional<Manager> optional =  managerRepository.findById(managerId);
//        if(optional.isEmpty())
//            throw new ResourceNotFoundException("Manager id Invalid");
//        Manager manager = optional.get();

//        Executive executive = ExecutiveMapper.mapDtoToEntity(executiveDto);
//        return executiveRepository.save(executive);
        // Step 1: Prepare User object and save it in DB
        /*
        Executive
            User user <-- User must go in DB first and then Executive next
        * */


            // Step 0: Fetch manager from DB using managerUsername
        Manager manager = managerRepository.getByUsername(managerUsername);

//        User user = new User();
//        user.setUsername(executiveDto.username());
//        user.setPassword(executiveDto.password());
//        user.setRole(Role.EXECUTIVE);
//        user = userRepository.save(user); // <-- this is a full created user obj with id

        User user = userService.getUserObj(  // <-- this is a full created user obj with id
                executiveDto.username(),
                executiveDto.password(),
                Role.EXECUTIVE
        );

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Step 2: Map ExecutiveDto to Executive entity
        Executive executive = executiveMapper.mapDtoToEntity(executiveDto);

        // Step 3: Attach the User & Manager to Executive
        executive.setUser(user);
        executive.setManager(manager);

        // Step 4: Save executive and return
        executiveRepository.save(executive);
    }

    public List<TicketInfoDto> getTicketsByExecutiveUsername(
            String executiveUsername,
            Integer page,
            Integer size) {

        Pageable pageable = PageRequest.of(page, size);

        return ticketRepository.getTicketByExecutiveUsername(
                executiveUsername,
                pageable
        );
    }
}
