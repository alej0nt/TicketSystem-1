package com.leoalelui.ticketsystem.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ticket_id", nullable = false)
    private Ticket ticket;

    @ManyToOne()
    @JoinColumn(name = "employee_id", nullable = false)
    private EmployeeEntity employee;

    private String text;

}
