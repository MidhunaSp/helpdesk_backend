package com.springboot.helpdesk.repository;

import com.springboot.helpdesk.dto.response.CustomerPlanRespDto;
import com.springboot.helpdesk.model.CustomerPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerPlanRepository extends JpaRepository<CustomerPlan,Long> {

    // cp = CustomerPlan, c = cp.customer, u = c.user, p = cp.plan
    @Query("""
    select new com.springboot.helpdesk.dto.response.CustomerPlanRespDto(
        cp.id, c.id, c.name, p.id, p.planType, p.price,
        cp.activationDate, cp.endDate, cp.coupon, cp.amountPaid)
    from CustomerPlan cp
    JOIN cp.customer c
    JOIN c.user u
    JOIN cp.plan p
    where u.username = ?1
    """)
    List<CustomerPlanRespDto> findByCustomerUsername(String username);
}
