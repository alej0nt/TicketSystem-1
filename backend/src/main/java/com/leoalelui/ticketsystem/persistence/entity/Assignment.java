package com.leoalelui.ticketsystem.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Assignment {

    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ticket_id", nullable = false)
    private Ticket ticket;

    @ManyToOne()
    @JoinColumn(name = "employee_id", nullable = false)
    private EmployeeEntity employee;

    private String assignment_date;

}
