package com.leoalelui.ticketsystem.domain.service.impl;

import com.leoalelui.ticketsystem.domain.dto.request.EmployeeCreateDTO;
import com.leoalelui.ticketsystem.domain.dto.request.EmployeeUpdateDTO;
import com.leoalelui.ticketsystem.domain.dto.response.EmployeeResponseDTO;
import com.leoalelui.ticketsystem.domain.exception.ResourceNotFoundException;
import com.leoalelui.ticketsystem.domain.service.EmployeeService;
import com.leoalelui.ticketsystem.persistence.dao.EmployeeDAO;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeDAO employeeDAO;

    @Override
    @Transactional
    public EmployeeResponseDTO createEmployee(EmployeeCreateDTO employeeCreateDTO) {
        validateEmailNotExists(employeeCreateDTO.getEmail());
        return employeeDAO.save(employeeCreateDTO);
    }

    @Override
    @Transactional
    public EmployeeResponseDTO updateEmployee(Long id, EmployeeUpdateDTO employeeUpdateDTO) {
        validateEmployeeExists(id);
        validateEmailNotExistsInOtherEmployee(employeeUpdateDTO.getEmail(), id);
        
        return employeeDAO.update(id, employeeUpdateDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado."));
    }

    @Override
    @Transactional
    public void deleteEmployee(Long id) {
        validateEmployeeExists(id);
        employeeDAO.deleteById(id);
    }

    @Override
    public EmployeeResponseDTO getEmployeeById(Long id) {
        return employeeDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado."));
    }

    @Override
    public List<EmployeeResponseDTO> getAllEmployees() {
        return employeeDAO.findAll();
    }

    @Override
    public EmployeeResponseDTO getEmployeeByEmail(String email) {
        return employeeDAO.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado."));
    }

    private void validateEmployeeExists(Long id) {
        if (!employeeDAO.existsById(id)) {
            throw new ResourceNotFoundException("Empleado no encontrado con ID: " + id);
        }
    }

    private void validateEmailNotExists(String email) {
        if (employeeDAO.existsByEmail(email)) {
            throw new RuntimeException("El email ya existe.");
        }
    }

    private void validateEmailNotExistsInOtherEmployee(String email, Long currentEmployeeId) {
        EmployeeResponseDTO existingEmployee = employeeDAO.findByEmail(email)
                .orElse(null);
        
        if (existingEmployee != null && !existingEmployee.getId().equals(currentEmployeeId)) {
            throw new RuntimeException("El email ya existe en otro empleado.");
        }
    }
}
