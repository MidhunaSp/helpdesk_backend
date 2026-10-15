package com.springboot.helpdesk.controller;

import com.springboot.helpdesk.dto.request.CustomerPlanReqDto;
import com.springboot.helpdesk.dto.response.CustomerPlanRespDto;
import com.springboot.helpdesk.service.CustomerPlanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CustomerPlanController {

    private final CustomerPlanService customerPlanService;

    /*
    API to add customer buys plan record in customer_plan table with all relevant fields
    * */
    @PostMapping("/api/customerPlan/add/{customerId}/{planId}")
    public void buyPlan(@PathVariable Long customerId,
                        @PathVariable Long planId,
                        @Valid @RequestBody CustomerPlanReqDto customerPlanReqDto) {
        customerPlanService.buyPlan(customerId, planId, customerPlanReqDto);
    }

    /*
     * GET API : to fetch all plans based on customer username.
     * */
    @GetMapping("/api/customerPlan")
    public List<CustomerPlanRespDto> getPlansByCustomerUsername(@RequestParam("username") String customerUsername){
        return customerPlanService.getPlansByCustomerUsername(customerUsername);
    }
}
