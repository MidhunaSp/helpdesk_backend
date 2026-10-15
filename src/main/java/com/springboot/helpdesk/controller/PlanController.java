package com.springboot.helpdesk.controller;

import com.springboot.helpdesk.dto.request.PlanReqDto;
import com.springboot.helpdesk.service.PlanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PlanController {

    private final PlanService planService;

    /*  POST API to insert simple plans in DB */
    @PostMapping("/api/plan/add")
    public void addPlan(@Valid @RequestBody PlanReqDto planReqDto){
        planService.addPlan(planReqDto);
    }
}
