package com.springboot.helpdesk.model;

import com.springboot.helpdesk.enums.JobTitle;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Executive { //e

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String contact;
    @Enumerated(EnumType.STRING)
    private JobTitle jobTitle;
    @OneToOne
    private User user; // e.user u

    @ManyToOne
    @JoinColumn(name = "manager_id", nullable = false)
    private Manager manager;  //e.manager m
}
