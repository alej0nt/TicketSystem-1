package com.leoalelui.ticketsystem.presentation.controller;

import com.leoalelui.ticketsystem.domain.dto.request.EmployeeCreateDTO;
import com.leoalelui.ticketsystem.domain.dto.request.EmployeeUpdateDTO;
import com.leoalelui.ticketsystem.domain.dto.response.EmployeeResponseDTO;
import com.leoalelui.ticketsystem.domain.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<EmployeeResponseDTO> createEmployee(@Valid @RequestBody EmployeeCreateDTO employeeCreateDTO) {
        EmployeeResponseDTO employeeCreated = employeeService.createEmployee(employeeCreateDTO);
        return ResponseEntity.ok(employeeCreated);
    }

    /*@PutMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> updateEmployee(
            @PathVariable Long id,
            @RequestBody EmployeeUpdateDTO employeeUpdateDTO
    ) {
        EmployeeResponseDTO updated = employeeService.updateEmployee(id, employeeUpdateDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> getEmployeeById(@PathVariable Long id) {
        EmployeeResponseDTO employee = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employee);
    }

    @GetMapping
    public ResponseEntity<List<EmployeeResponseDTO>> getAllEmployees() {
        List<EmployeeResponseDTO> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<EmployeeResponseDTO> getEmployeeByEmail(@PathVariable String email) {
        EmployeeResponseDTO employee = employeeService.getEmployeeByEmail(email);
        return ResponseEntity.ok(employee);
    }*/
}
