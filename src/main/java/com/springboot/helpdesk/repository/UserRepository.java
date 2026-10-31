package com.springboot.helpdesk.repository;

import com.springboot.helpdesk.dto.response.DetailsDto;
import com.springboot.helpdesk.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    @Query("""
            select u
            from User u
            where u.username=?1 
            """)
    User loadUserByUsername(String username);

    @Query("""
           select u.username,u.isActive,u.role
           from User u
           where u.username=?1
           """)
    DetailsDto getUserDetails(String loggedInUsername);
}
