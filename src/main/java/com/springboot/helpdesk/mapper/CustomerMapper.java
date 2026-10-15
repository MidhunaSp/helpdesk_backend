package com.springboot.helpdesk.mapper;

import com.springboot.helpdesk.dto.request.CustomerReqDto;
import com.springboot.helpdesk.model.Customer;
import org.springframework.stereotype.Component;


@Component
public class CustomerMapper {

    public Customer mapDtoToEntity(CustomerReqDto dto) {
        Customer customer = new Customer();
        customer.setName(dto.name());
        customer.setCity(dto.city());
        customer.setEmail(dto.email());
        return customer;
    }
}