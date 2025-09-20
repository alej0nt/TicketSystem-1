package com.leoalelui.ticketsystem.domain.service.impl;

import com.leoalelui.ticketsystem.domain.dto.request.EmployeeCreateDTO;
import com.leoalelui.ticketsystem.domain.dto.request.EmployeeUpdateDTO;
import com.leoalelui.ticketsystem.domain.dto.response.EmployeeResponseDTO;
import com.leoalelui.ticketsystem.domain.exception.ResourceNotFoundException;
import com.leoalelui.ticketsystem.domain.service.EmployeeService;
import com.leoalelui.ticketsystem.persistence.dao.EmployeeDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeDAO employeeDAO;

    @Override
    public EmployeeResponseDTO createEmployee(EmployeeCreateDTO employeeCreateDTO) {
        if (employeeDAO.existsByEmail(employeeCreateDTO.getEmail())) {
            throw new RuntimeException("El email ya existe.");
        }
        return employeeDAO.save(employeeCreateDTO);
    }

    @Override
    public EmployeeResponseDTO updateEmployee(Long id, EmployeeUpdateDTO employeeUpdateDTO) {
        EmployeeResponseDTO existingEmployee = employeeDAO.findByEmail(employeeUpdateDTO.getEmail())
                .orElse(null);
        
        if (existingEmployee != null && !existingEmployee.getId().equals(id)) {
            throw new RuntimeException("El email ya existe en otro empleado.");
        }
        
        return employeeDAO.update(id, employeeUpdateDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado."));
    }

    @Override
    public void deleteEmployee(Long id) {
        if (!employeeDAO.existsById(id)) {
            throw new ResourceNotFoundException("Empleado no encontrado.");
        }
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
}
