package com.springboot.helpdesk.service;

import com.springboot.helpdesk.dto.request.CustomerPlanReqDto;
import com.springboot.helpdesk.dto.response.CustomerPlanRespDto;
import com.springboot.helpdesk.exception.ResourceNotFoundException;
import com.springboot.helpdesk.model.Customer;
import com.springboot.helpdesk.model.CustomerPlan;
import com.springboot.helpdesk.model.Plan;
import com.springboot.helpdesk.repository.CustomerPlanRepository;
import com.springboot.helpdesk.repository.CustomerRepository;
import com.springboot.helpdesk.repository.PlanRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerPlanService {

    private final CustomerPlanRepository customerPlanRepository;
    private final CustomerRepository customerRepository;
    private final PlanRepository planRepository;

    public void buyPlan(Long customerId, Long planId, @Valid CustomerPlanReqDto customerPlanReqDto) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new ResourceNotFoundException("Plan not found"));

        CustomerPlan customerPlan = new CustomerPlan();
        customerPlan.setCustomer(customer);
        customerPlan.setPlan(plan);
        customerPlan.setActivationDate(customerPlanReqDto.activationDate());
        customerPlan.setEndDate(customerPlanReqDto.endDate());
        customerPlan.setCoupon(customerPlanReqDto.coupon());
        customerPlan.setAmountPaid(customerPlanReqDto.amountPaid());
        customerPlanRepository.save(customerPlan);
    }

    public List<CustomerPlanRespDto> getPlansByCustomerUsername(String customerUsername) {
        return customerPlanRepository.findByCustomerUsername(customerUsername);
    }
}
