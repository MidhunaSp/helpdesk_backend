package com.springboot.helpdesk.controller;

import com.springboot.helpdesk.dto.request.CustomerReqDto;
import com.springboot.helpdesk.dto.request.CustomerUpdateDto;
import com.springboot.helpdesk.model.Customer;
import com.springboot.helpdesk.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    // add/insert ops
    @PostMapping("/api/customer/add")
    public Customer insertCustomer(@Valid @RequestBody CustomerReqDto customerReqDto) {
        return customerService.insertCustomer(customerReqDto);
    }

    // fetch by id
    @GetMapping("/api/customer/{id}") ///api/customer/1
    public Customer getById(@PathVariable Long id) {
        return customerService.getById(id);
    }

    // fetch ops
    @GetMapping("/api/customer/all")
    public List<Customer> getAllCustomers() {
        return customerService.getAll();
    }

    // delete ops
    @DeleteMapping("/api/customer/{id}")
    public void deleteById(@PathVariable Long id) {
        // Check if id is valid
        getById(id);
        customerService.deleteById(id);
    }

    // update ops
    @PutMapping("/api/customer/{id}")
    public void update(@PathVariable Long id,
                       @RequestBody CustomerUpdateDto dto){
        customerService.update(id, dto);
    }
}
