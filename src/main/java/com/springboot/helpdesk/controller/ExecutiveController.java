package com.springboot.helpdesk.controller;

import com.springboot.helpdesk.dto.request.ExecutiveReqDto;
import com.springboot.helpdesk.service.ExecutiveService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class ExecutiveController {

    private final ExecutiveService executiveService;

    // steps tht has to be followed heree
     /*
     DTO (name,email,contact)
     validation
     Map DTO to Entity
     Insert Entity IN Db
     * */

    @PostMapping("/api/executive/add")
    public void insertExecutive(@Valid @RequestBody ExecutiveReqDto executiveDto, Principal principal) {
        executiveService.add(executiveDto,principal.getName());
    }

}
