package com.springboot.helpdesk.repository;

import com.springboot.helpdesk.dto.response.JobTitleExecutiveCountDto;
import com.springboot.helpdesk.model.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository // since we're extending JpaRepository already @Repository annotation is optional
public interface ManagerRepository extends JpaRepository<Manager,Long> {

    @Query("""
            select e.jobTitle as jobTitle, count(e.id) as numberOfExecutives
            from Executive e
            where e.jobTitle IS NOT null
            group by e.jobTitle
            """)
    List<JobTitleExecutiveCountDto> getExecutiveStatWithJobTitleAndCount();

    @Query("""
        select m from Manager m
        join m.user u
        where u.username = ?1
        """)
    Manager getByUsername(String managerUsername);

}
