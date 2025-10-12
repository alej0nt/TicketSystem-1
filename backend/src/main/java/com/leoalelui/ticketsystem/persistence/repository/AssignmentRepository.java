package com.leoalelui.ticketsystem.persistence.repository;

import com.leoalelui.ticketsystem.persistence.entity.AssignmentEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AssignmentRepository extends JpaRepository<AssignmentEntity, Long> {
    List<AssignmentEntity> findByEmployeeId(Long employeeId);

    AssignmentEntity findByTicketId(Long ticketId);

    @Query("""
            SELECT a FROM AssignmentEntity a
            JOIN a.ticket t
            JOIN t.category c
            WHERE a.employee.id = :employeeId
            AND (LOWER(t.title) LIKE LOWER(CONCAT('%', :query, '%'))
                OR LOWER(t.description) LIKE LOWER(CONCAT('%', :query, '%'))
                OR LOWER(CAST(t.state AS string)) LIKE LOWER(CONCAT('%', :query, '%'))
                OR LOWER(CAST(t.priority AS string)) LIKE LOWER(CONCAT('%', :query, '%'))
                OR LOWER(c.name) LIKE LOWER(CONCAT('%', :query, '%')))
            ORDER BY a.assignmentDate DESC
            """)
    List<AssignmentEntity> findByEmployeeIdAndQuery(
            @Param("employeeId") Long employeeId,
            @Param("query") String query);
}
