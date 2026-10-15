package com.springboot.helpdesk.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Customer { //c

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String name;
    private String city;
    private String email;

    @OneToOne
    private User user; //c.user u

    @UpdateTimestamp
    private Instant updatedAt;
}
