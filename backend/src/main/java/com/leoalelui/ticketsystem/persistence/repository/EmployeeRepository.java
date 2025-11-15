package com.leoalelui.ticketsystem.persistence.repository;

import com.leoalelui.ticketsystem.persistence.entity.EmployeeEntity;
import com.leoalelui.ticketsystem.persistence.enums.Role;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {
    boolean existsByEmail(String email);
    Optional<EmployeeEntity> findByEmail(String email);
    List<EmployeeEntity> findByRole(Role role);
}
