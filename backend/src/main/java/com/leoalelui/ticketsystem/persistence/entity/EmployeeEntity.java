package com.leoalelui.ticketsystem.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;
import com.leoalelui.ticketsystem.persistence.enums.Role;

import java.util.List;

@Data
@Entity
@Table(name = "employee")
public class EmployeeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    private String department;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TicketEntity> tickets;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AssignmentEntity> assignments;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentEntity> comments;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NotificationEntity> notifications;
}

