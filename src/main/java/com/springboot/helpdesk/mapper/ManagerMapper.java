package com.springboot.helpdesk.mapper;

import com.springboot.helpdesk.dto.request.ManagerDto;
import com.springboot.helpdesk.model.Manager;
import org.springframework.stereotype.Component;

@Component
public class ManagerMapper {

    public static Manager convertDtoToEntity(ManagerDto dto){
        Manager manager = new Manager();
        manager.setName(dto.name());
        return manager;
    }
}