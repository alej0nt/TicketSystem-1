package com.leoalelui.ticketsystem.persistence.dao;

import com.leoalelui.ticketsystem.persistence.entity.EmployeeEntity;
import com.leoalelui.ticketsystem.persistence.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class EmployeeDAO {

    private final EmployeeRepository employeeRepository;

    public EmployeeEntity save(EmployeeEntity employee) {
        return employeeRepository.save(employee);
    }

    public void deleteById(Long id) {
        employeeRepository.deleteById(id);
    }

    public Optional<EmployeeEntity> findById(Long id) {
        return employeeRepository.findById(id);
    }

    public Optional<EmployeeEntity> findByEmail(String email) {
        return employeeRepository.findByEmail(email);
    }

    public List<EmployeeEntity> findAll() {
        return employeeRepository.findAll();
    }
}

