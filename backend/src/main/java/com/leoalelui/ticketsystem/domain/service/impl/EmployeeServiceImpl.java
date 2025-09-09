package com.leoalelui.ticketsystem.domain.service.impl;

import com.leoalelui.ticketsystem.domain.dto.request.EmployeeCreateDTO;
import com.leoalelui.ticketsystem.domain.dto.request.EmployeeUpdateDTO;
import com.leoalelui.ticketsystem.domain.dto.response.EmployeeResponseDTO;
import com.leoalelui.ticketsystem.domain.service.EmployeeService;
import com.leoalelui.ticketsystem.persistence.dao.EmployeeDAO;
import com.leoalelui.ticketsystem.persistence.entity.EmployeeEntity;
import com.leoalelui.ticketsystem.persistence.mapper.EmployeeMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeDAO employeeDAO;
    private final EmployeeMapper employeeMapper;

    @Override
    public EmployeeResponseDTO createEmployee(EmployeeCreateDTO dto) {
        EmployeeEntity employee = employeeMapper.toEntity(dto);
        EmployeeEntity saved = employeeDAO.save(employee);
        return employeeMapper.toResponseDTO(saved);
    }

    @Override
    public EmployeeResponseDTO updateEmployee(Long id, EmployeeUpdateDTO dto) {
        EmployeeEntity employee = employeeDAO.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado"));

        employee.setName(dto.getName());
        employee.setEmail(dto.getEmail());
        employee.setRole(dto.getRole());
        employee.setDepartment(dto.getDepartment());

        EmployeeEntity updated = employeeDAO.save(employee);
        return employeeMapper.toResponseDTO(updated);
    }

    @Override
    public void deleteEmployee(Long id) {
        employeeDAO.deleteById(id);
    }

    @Override
    public EmployeeResponseDTO getEmployeeById(Long id) {
        EmployeeEntity employee = employeeDAO.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado"));
        return employeeMapper.toResponseDTO(employee);
    }

    @Override
    public List<EmployeeResponseDTO> getAllEmployees() {
        return employeeDAO.findAll()
                .stream()
                .map(employeeMapper::toResponseDTO)
                .toList();
    }

    @Override
    public EmployeeResponseDTO getEmployeeByEmail(String email) {
        EmployeeEntity employee = employeeDAO.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado"));
        return employeeMapper.toResponseDTO(employee);
    }

    /*@Override
    public boolean validateCredentials(String email, String password) {
        EmployeeEntity employee = employeeDAO.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Credenciales inválidas"));
        return passwordEncoder.matches(password, employee.getPassword());
    }*/
}
