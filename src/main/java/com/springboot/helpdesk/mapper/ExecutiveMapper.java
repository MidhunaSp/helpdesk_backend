package com.springboot.helpdesk.mapper;

import com.springboot.helpdesk.dto.request.ExecutiveReqDto;
import com.springboot.helpdesk.model.Executive;
import jakarta.validation.Valid;
import org.springframework.stereotype.Component;

@Component
public class ExecutiveMapper {
    public Executive mapDtoToEntity(@Valid ExecutiveReqDto executiveDto) {
        Executive executive = new Executive();
        executive.setName(executiveDto.name());
        executive.setEmail(executiveDto.email());
        executive.setContact(executiveDto.contact());
        executive.setJobTitle(executiveDto.jobTitle());
        return executive;
    }
}
