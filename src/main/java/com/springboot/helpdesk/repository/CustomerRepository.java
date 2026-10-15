package com.springboot.helpdesk.repository;

import com.springboot.helpdesk.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {
    @Query("""
            select c from Customer c
            JOIN c.user u
            where u.username=?1
            """)
    Customer getByUsername(String customerUsername);

}
// JpaRepository : spring data jpa
// pre created methods are there inside this interface
// save(T) , findAll() : List<T> , findById(id) : void,delete(T) : void,deleteById(id) : void  (inside JpaRepository Interface)

// if this CustomerRepository is a cls then implements so code has to written by us
// if this CustomerRepository is an interface then extends interface gets all the pre created methods by itself

// since id is Long and is needed for the methods inside here so <Customer,Long> is given