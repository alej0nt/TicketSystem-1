package com.leoalelui.ticketsystem.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "employee_id", nullable = false)
    private EmployeeEntity employee;

    private String tittle;

    private String description;

    private String priority;

    private String state;
}
