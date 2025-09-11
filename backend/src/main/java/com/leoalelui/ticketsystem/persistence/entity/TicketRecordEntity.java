package com.leoalelui.ticketsystem.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;

@Data
@Entity
@Table(name = "ticket_record")
public class TicketRecordEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "ticket_id", nullable = false)
    private TicketEntity ticket;

    @Column(name = "previous_state")
    private String previousState;

    @Column(name = "next_state")
    private String nextState;

    @CreationTimestamp
    @Column(name = "changed_date", nullable = false, updatable = false)
    private LocalDateTime changedDate;
}
