package com.leoalelui.ticketsystem.domain.service.impl;

import com.leoalelui.ticketsystem.domain.dto.request.EmployeeCreateDTO;
import com.leoalelui.ticketsystem.domain.dto.request.EmployeeUpdateDTO;
import com.leoalelui.ticketsystem.domain.dto.response.EmployeeResponseDTO;
import com.leoalelui.ticketsystem.domain.exception.ResourceNotFoundException;
import com.leoalelui.ticketsystem.domain.service.EmployeeService;
import com.leoalelui.ticketsystem.persistence.dao.EmployeeDAO;
import com.leoalelui.ticketsystem.persistence.enums.Role;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final PasswordEncoder passwordEncoder;
    private final EmployeeDAO employeeDAO;

    @Override
    @Transactional
    public EmployeeResponseDTO createEmployee(EmployeeCreateDTO employeeCreateDTO) {
        validateEmailNotExists(employeeCreateDTO.getEmail());
        String encodedPassword = passwordEncoder.encode(employeeCreateDTO.getPassword());
        employeeCreateDTO.setPassword(encodedPassword);
        return employeeDAO.save(employeeCreateDTO);
    }

    @Override
    @Transactional
    public EmployeeResponseDTO updateEmployee(Long id, EmployeeUpdateDTO employeeUpdateDTO) {
        validateEmployeeExists(id);
        validateEmailNotExistsInOtherEmployee(employeeUpdateDTO.getEmail(), id);
        
        return employeeDAO.update(id, employeeUpdateDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado con ID: " + id));
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
                .orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado con ID: " + id));
    }

    @Override
    public List<EmployeeResponseDTO> getAllEmployees(Role role) {
        return employeeDAO.findAll(role);
    }

    @Override
    public EmployeeResponseDTO getEmployeeByEmail(String email) {
        return employeeDAO.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado con el email: " + email));
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
