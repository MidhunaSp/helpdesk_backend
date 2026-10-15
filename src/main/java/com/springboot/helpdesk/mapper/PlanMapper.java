package com.springboot.helpdesk.mapper;

import com.springboot.helpdesk.dto.request.PlanDto;
import com.springboot.helpdesk.model.Plan;

public class PlanMapper {

    private PlanMapper() {
    }

    public static Plan mapDtoToEntity(PlanDto dto){
        Plan plan = new Plan();
        plan.setPlanType(dto.planType());
        plan.setPrice(dto.price());
        plan.setPlanName(dto.planName());
        return plan;
    }
}