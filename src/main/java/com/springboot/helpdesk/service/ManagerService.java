package com.springboot.helpdesk.service;

import com.springboot.helpdesk.dto.request.ManagerDto;
import com.springboot.helpdesk.dto.response.JobTitleExecutiveCountDto;
import com.springboot.helpdesk.enums.JobTitle;
import com.springboot.helpdesk.enums.Role;
import com.springboot.helpdesk.exception.ResourceNotFoundException;
import com.springboot.helpdesk.mapper.ManagerMapper;
import com.springboot.helpdesk.model.Executive;
import com.springboot.helpdesk.model.Manager;
import com.springboot.helpdesk.model.User;
import com.springboot.helpdesk.repository.ExecutiveRepository;
import com.springboot.helpdesk.repository.ManagerRepository;
import com.springboot.helpdesk.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ManagerService {
    private final ManagerRepository managerRepository;
    private final UserRepository userRepository;
    private final ExecutiveRepository  executiveRepository;
    private final PasswordEncoder passwordEncoder;

    public void add(@Valid ManagerDto managerDto) {
        // Step 1: Prepare user object and save it in DB
        User user = new User();
        user.setUsername(managerDto.username());
        user.setPassword(passwordEncoder.encode(managerDto.password()));
        user.setRole(Role.MANAGER);
        user = userRepository.save(user); //<-- This user now has an id attached

        user.setPassword(passwordEncoder.encode(managerDto.password()));

        // Step 2: Map Dto to Entity
        Manager manager = ManagerMapper.convertDtoToEntity(managerDto);

        // Step 3: Attach User to Manager
        manager.setUser(user);

        // Step 4: save manager
        managerRepository.save(manager);
    }

    public List<JobTitleExecutiveCountDto> getExecutiveStatWithJobTitleAndCount() {
        return managerRepository.getExecutiveStatWithJobTitleAndCount();
    }

    public void updateJobTitle(long executiveId, JobTitle jobTitle) {
        // Step 1: Fetch executive from DB using executiveId
        Executive executive = executiveRepository.findById(executiveId)
                .orElseThrow(()-> new ResourceNotFoundException("Executive Not Found"));

        // Step 2: Update job title
        executive.setJobTitle(jobTitle);

        // Step 3: Save in DB
        executiveRepository.save(executive);

    }

}