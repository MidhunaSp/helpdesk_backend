package com.springboot.helpdesk.service;

import com.springboot.helpdesk.dto.request.PlanReqDto;
import com.springboot.helpdesk.model.Plan;
import com.springboot.helpdesk.repository.PlanRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlanService {

    private final PlanRepository planRepository;

    public void addPlan(PlanReqDto planReqDto) {
        Plan plan = new Plan();
        plan.setPlanType(planReqDto.planType());
        plan.setPrice(planReqDto.price());
        planRepository.save(plan);
    }
}
