package com.springboot.helpdesk.service;

import com.springboot.helpdesk.dto.request.CustomerReqDto;
import com.springboot.helpdesk.dto.request.CustomerUpdateDto;
import com.springboot.helpdesk.enums.Role;
import com.springboot.helpdesk.exception.InvalidCallException;
import com.springboot.helpdesk.exception.ResourceNotFoundException;
import com.springboot.helpdesk.mapper.CustomerMapper;
import com.springboot.helpdesk.model.Customer;
import com.springboot.helpdesk.model.User;
import com.springboot.helpdesk.repository.CustomerRepository;
import com.springboot.helpdesk.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final CustomerMapper customerMapper;

    public Customer insertCustomer(CustomerReqDto customerReqDto){
        // Step 1: Prepare user obj and save it in DB
        User user = userService.getUserObj(
                customerReqDto.username(),
                customerReqDto.password(),
                Role.CUSTOMER
        );

        // Step 1.5 : Encode the password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Map CustomerDto to Customer entity
        Customer customer = customerMapper.mapDtoToEntity(customerReqDto);

        // Attach user to Customer
        customer.setUser(user);

        // Save Customer entity
        return customerRepository.save(customer);
    }

    public Customer getById(long id) {
        Optional<Customer> optional = customerRepository.findById(id);
        if(optional.isEmpty())
            throw new ResourceNotFoundException("INVALID CUSTOMER ID");
        return optional.get();
    }

    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    public void deleteById(long id) {
        customerRepository.deleteById(id);
    }

    public void update(Long id, CustomerUpdateDto dto) {
        Customer customer = getById(id);
        boolean flag = false;

        if(!(dto.name()  == null) && !(dto.name().trim().isEmpty())) {    // If dto.name() is NOT(!) empty
            customer.setName(dto.name());
            flag = true;
        }

        if(!(dto.city() == null) && !(dto.city().trim().isEmpty())) {
            customer.setCity(dto.city());
            flag = true;
        }

        if(!flag)
            throw new InvalidCallException("Update op terminated");

        customerRepository.save(customer);

    }
}
