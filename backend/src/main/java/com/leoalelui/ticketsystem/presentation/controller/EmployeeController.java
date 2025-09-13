package com.leoalelui.ticketsystem.presentation.controller;

import com.leoalelui.ticketsystem.domain.dto.request.EmployeeCreateDTO;
import com.leoalelui.ticketsystem.domain.dto.request.EmployeeUpdateDTO;
import com.leoalelui.ticketsystem.domain.dto.response.EmployeeResponseDTO;
import com.leoalelui.ticketsystem.domain.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/employees")
@Tag(name = "Empleados", description = "API para la gestión de empleados.")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Operation(summary = "Crear un nuevo empleado", description = "Registra un nuevo empleado en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Empleado creado exitosamente."),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos.")
    })
    @PostMapping
    public ResponseEntity<EmployeeResponseDTO> createEmployee(
            @Valid @RequestBody EmployeeCreateDTO employeeCreateDTO) {
        EmployeeResponseDTO employeeCreated = employeeService.createEmployee(employeeCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeCreated); // 201 Created
    }

    @Operation(summary = "Actualizar un empleado", description = "Modifica los datos de un empleado existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empleado actualizado exitosamente."),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos."),
            @ApiResponse(responseCode = "404", description = "Empleado no encontrado.")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeUpdateDTO employeeUpdateDTO
    ) {
        EmployeeResponseDTO employeeUpdated = employeeService.updateEmployee(id, employeeUpdateDTO);
        return ResponseEntity.ok(employeeUpdated); // 200 OK
    }

    @Operation(summary = "Eliminar un empleado", description = "Elimina un empleado por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Empleado eliminado exitosamente."),
            @ApiResponse(responseCode = "404", description = "Empleado no encontrado.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    @Operation(summary = "Buscar un empleado por ID", description = "Obtiene los datos de un empleado específico mediante su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empleado encontrado."),
            @ApiResponse(responseCode = "404", description = "Empleado no encontrado.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> getEmployeeById(@PathVariable Long id) {
        EmployeeResponseDTO employeeFound = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employeeFound); // 200 OK
    }

    @Operation(summary = "Listar todos los empleados", description = "Obtiene una lista con todos los empleados registrados en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente.")
    })
    @GetMapping
    public ResponseEntity<List<EmployeeResponseDTO>> getAllEmployees() {
        List<EmployeeResponseDTO> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees); // 200 OK
    }

    @Operation(summary = "Buscar un empleado por email", description = "Obtiene los datos de un empleado a partir de su correo electrónico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empleado encontrado."),
            @ApiResponse(responseCode = "404", description = "Empleado no encontrado.")
    })
    @GetMapping("/email/{email}")
    public ResponseEntity<EmployeeResponseDTO> getEmployeeByEmail(@PathVariable String email) {
        EmployeeResponseDTO employeeFound = employeeService.getEmployeeByEmail(email);
        return ResponseEntity.ok(employeeFound); // 200 OK
    }
}
