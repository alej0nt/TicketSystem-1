package com.leoalelui.ticketsystem.domain.service;

import com.leoalelui.ticketsystem.domain.dto.EmployeeCreateDTO;
import com.leoalelui.ticketsystem.domain.dto.EmployeeUpdateDTO;
import com.leoalelui.ticketsystem.domain.dto.EmployeeResponseDTO;

import java.util.List;

public interface EmployeeService {
    EmployeeResponseDTO createEmployee(EmployeeCreateDTO employeeCreateDTO);
    EmployeeResponseDTO updateEmployee(Long id, EmployeeUpdateDTO employeeUpdateDTO);
    void deleteEmployee(Long id);
    EmployeeResponseDTO getEmployeeById(Long id);
    List<EmployeeResponseDTO> getAllEmployees();
    EmployeeResponseDTO getEmployeeByEmail(String email);
    boolean validateCredentials(String email, String password);
}
