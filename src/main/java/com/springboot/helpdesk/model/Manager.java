package com.springboot.helpdesk.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Manager {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String name;
    @OneToOne
    private User user; //m.user u
}
