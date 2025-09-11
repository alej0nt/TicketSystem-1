package com.leoalelui.ticketsystem.domain.dto.response;

import com.leoalelui.ticketsystem.persistence.entity.AssignmentEntity;
import com.leoalelui.ticketsystem.persistence.entity.CommentEntity;
import com.leoalelui.ticketsystem.persistence.entity.NotificationEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.AllArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
public class EmployeeResponseDTO {
    private Long id;
    private String name;
    private String email;
    private String role;
    private String department;
    //private List<AssignmentResponseDTO> assignments;
    //private List<CommentResponseDTO> comments;
    //private List<NotificationResponseDTO> notifications;
}
