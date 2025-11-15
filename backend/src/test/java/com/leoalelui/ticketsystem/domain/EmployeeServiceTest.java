package com.leoalelui.ticketsystem.domain;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.leoalelui.ticketsystem.domain.dto.request.EmployeeCreateDTO;
import com.leoalelui.ticketsystem.domain.dto.request.EmployeeUpdateDTO;
import com.leoalelui.ticketsystem.domain.dto.response.EmployeeResponseDTO;
import com.leoalelui.ticketsystem.domain.exception.ResourceNotFoundException;
import com.leoalelui.ticketsystem.domain.service.impl.EmployeeServiceImpl;
import com.leoalelui.ticketsystem.persistence.dao.EmployeeDAO;
import com.leoalelui.ticketsystem.persistence.enums.Role;

@ExtendWith(MockitoExtension.class)
@DisplayName("EmployeeService - Unit Test")
public class EmployeeServiceTest {

    @Mock
    private EmployeeDAO employeeDAO;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private EmployeeCreateDTO createDTO;
    private EmployeeUpdateDTO updateDTO;
    private EmployeeResponseDTO responseDTO;
    private Long validId;
    private String validEmail;
    private String validPassword;
    private String encodedPassword;

    @BeforeEach
    void setUp() {
        validId = 1L;
        validEmail = "juan.perez@empresa.com";
        validPassword = "password123";
        encodedPassword = "$2a$10$encodedPassword";

        createDTO = new EmployeeCreateDTO(
                "Juan Pérez",
                validEmail,
                validPassword,
                "Soporte Técnico"
        );

        updateDTO = new EmployeeUpdateDTO(
                "Juan Pérez Actualizado",
                "juan.nuevo@empresa.com",
                Role.AGENT,
                "Desarrollo"
        );

        responseDTO = new EmployeeResponseDTO(
                validId,
                "Juan Pérez",
                validEmail,
                Role.USER,
                "Soporte Técnico"
        );
    }

    // ==================== CREATE EMPLOYEE TESTS ====================
    @Test
    @DisplayName("CREATE - Debe crear empleado cuando el email no existe")
    void createEmployee_ValidData_ShouldCreateEmployee() {
        when(employeeDAO.existsByEmail(createDTO.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(validPassword)).thenReturn(encodedPassword);
        when(employeeDAO.save(createDTO)).thenReturn(responseDTO);

        EmployeeResponseDTO result = employeeService.createEmployee(createDTO);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(validId);
        assertThat(result.getName()).isEqualTo("Juan Pérez");
        assertThat(result.getEmail()).isEqualTo(validEmail);
        assertThat(result.getRole()).isEqualTo(Role.USER);

        verify(employeeDAO, times(1)).existsByEmail(createDTO.getEmail());
        verify(passwordEncoder, times(1)).encode(validPassword);
        verify(employeeDAO, times(1)).save(createDTO);
    }

    @Test
    @DisplayName("CREATE - Debe lanzar RuntimeException si el email ya existe")
    void createEmployee_ExistingEmail_ShouldThrowException() {
        when(employeeDAO.existsByEmail(createDTO.getEmail())).thenReturn(true);

        assertThatThrownBy(() -> employeeService.createEmployee(createDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("El email ya existe");

        verify(employeeDAO, times(1)).existsByEmail(createDTO.getEmail());
        verify(passwordEncoder, never()).encode(anyString());
        verify(employeeDAO, never()).save(any());
    }

    // ==================== UPDATE EMPLOYEE TESTS ====================
    @Test
    @DisplayName("UPDATE - Debe actualizar empleado cuando ID existe y email no está tomado")
    void updateEmployee_ValidData_ShouldUpdateEmployee() {
        EmployeeResponseDTO updatedResponse = new EmployeeResponseDTO(
                validId,
                "Juan Pérez Actualizado",
                "juan.nuevo@empresa.com",
                Role.AGENT,
                "Desarrollo"
        );

        when(employeeDAO.existsById(validId)).thenReturn(true);
        when(employeeDAO.findByEmail(updateDTO.getEmail())).thenReturn(Optional.empty());
        when(employeeDAO.update(validId, updateDTO)).thenReturn(Optional.of(updatedResponse));

        EmployeeResponseDTO result = employeeService.updateEmployee(validId, updateDTO);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Juan Pérez Actualizado");
        assertThat(result.getEmail()).isEqualTo("juan.nuevo@empresa.com");
        assertThat(result.getRole()).isEqualTo(Role.AGENT);

        verify(employeeDAO, times(1)).existsById(validId);
        verify(employeeDAO, times(1)).findByEmail(updateDTO.getEmail());
        verify(employeeDAO, times(1)).update(validId, updateDTO);
    }

    @Test
    @DisplayName("UPDATE - Debe lanzar ResourceNotFoundException si el ID no existe")
    void updateEmployee_NonExistentId_ShouldThrowException() {
        when(employeeDAO.existsById(validId)).thenReturn(false);

        assertThatThrownBy(() -> employeeService.updateEmployee(validId, updateDTO))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Empleado no encontrado con ID");

        verify(employeeDAO, times(1)).existsById(validId);
        verify(employeeDAO, never()).findByEmail(anyString());
        verify(employeeDAO, never()).update(any(), any());
    }

    @Test
    @DisplayName("UPDATE - Debe lanzar RuntimeException si el email pertenece a otro empleado")
    void updateEmployee_EmailTakenByAnother_ShouldThrowException() {
        EmployeeResponseDTO anotherEmployee = new EmployeeResponseDTO(
                2L,
                "Otro Empleado",
                "juan.nuevo@empresa.com",
                Role.USER,
                "Ventas"
        );

        when(employeeDAO.existsById(validId)).thenReturn(true);
        when(employeeDAO.findByEmail(updateDTO.getEmail())).thenReturn(Optional.of(anotherEmployee));

        assertThatThrownBy(() -> employeeService.updateEmployee(validId, updateDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("El email ya existe en otro empleado");

        verify(employeeDAO, times(1)).existsById(validId);
        verify(employeeDAO, times(1)).findByEmail(updateDTO.getEmail());
        verify(employeeDAO, never()).update(any(), any());
    }

    @Test
    @DisplayName("UPDATE - Debe permitir actualizar con el mismo email del empleado")
    void updateEmployee_SameEmailSameEmployee_ShouldUpdateEmployee() {
        EmployeeResponseDTO sameEmployee = new EmployeeResponseDTO(
                validId,
                "Juan Pérez",
                "juan.nuevo@empresa.com",
                Role.USER,
                "Soporte"
        );

        EmployeeResponseDTO updatedResponse = new EmployeeResponseDTO(
                validId,
                "Juan Pérez Actualizado",
                "juan.nuevo@empresa.com",
                Role.AGENT,
                "Desarrollo"
        );

        when(employeeDAO.existsById(validId)).thenReturn(true);
        when(employeeDAO.findByEmail(updateDTO.getEmail())).thenReturn(Optional.of(sameEmployee));
        when(employeeDAO.update(validId, updateDTO)).thenReturn(Optional.of(updatedResponse));

        EmployeeResponseDTO result = employeeService.updateEmployee(validId, updateDTO);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(validId);
        verify(employeeDAO, times(1)).update(validId, updateDTO);
    }

    @Test
    @DisplayName("UPDATE - Debe lanzar ResourceNotFoundException si update retorna Optional.empty()")
    void updateEmployee_UpdateReturnsEmpty_ShouldThrowException() {
        when(employeeDAO.existsById(validId)).thenReturn(true);
        when(employeeDAO.findByEmail(updateDTO.getEmail())).thenReturn(Optional.empty());
        when(employeeDAO.update(validId, updateDTO)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> employeeService.updateEmployee(validId, updateDTO))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Empleado no encontrado con ID");

        verify(employeeDAO, times(1)).update(validId, updateDTO);
    }

    // ==================== DELETE EMPLOYEE TESTS ====================
    @Test
    @DisplayName("DELETE - Debe eliminar empleado existente")
    void deleteEmployee_ExistingId_ShouldDeleteEmployee() {
        when(employeeDAO.existsById(validId)).thenReturn(true);

        employeeService.deleteEmployee(validId);

        verify(employeeDAO, times(1)).existsById(validId);
        verify(employeeDAO, times(1)).deleteById(validId);
    }

    @Test
    @DisplayName("DELETE - Debe lanzar ResourceNotFoundException si el ID no existe")
    void deleteEmployee_NonExistentId_ShouldThrowException() {
        when(employeeDAO.existsById(validId)).thenReturn(false);

        assertThatThrownBy(() -> employeeService.deleteEmployee(validId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Empleado no encontrado con ID");

        verify(employeeDAO, times(1)).existsById(validId);
        verify(employeeDAO, never()).deleteById(any());
    }

    // ==================== GET EMPLOYEE BY ID TESTS ====================
    @Test
    @DisplayName("GET BY ID - Debe retornar empleado existente")
    void getEmployeeById_ExistingId_ShouldReturnEmployee() {
        when(employeeDAO.findById(validId)).thenReturn(Optional.of(responseDTO));

        EmployeeResponseDTO result = employeeService.getEmployeeById(validId);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(validId);
        assertThat(result.getName()).isEqualTo("Juan Pérez");
        assertThat(result.getEmail()).isEqualTo(validEmail);

        verify(employeeDAO, times(1)).findById(validId);
    }

    @Test
    @DisplayName("GET BY ID - Debe lanzar ResourceNotFoundException si no se encuentra")
    void getEmployeeById_NonExistentId_ShouldThrowException() {
        when(employeeDAO.findById(validId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> employeeService.getEmployeeById(validId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Empleado no encontrado con ID");

        verify(employeeDAO, times(1)).findById(validId);
    }

    // ==================== GET ALL EMPLOYEES TESTS ====================
    @Test
    @DisplayName("GET ALL - Debe retornar lista de empleados")
    void getAllEmployees_ShouldReturnEmployeeList() {
        EmployeeResponseDTO employee2 = new EmployeeResponseDTO(
                2L,
                "María López",
                "maria.lopez@empresa.com",
                Role.AGENT,
                "Ventas"
        );

        List<EmployeeResponseDTO> expectedList = List.of(responseDTO, employee2);
        when(employeeDAO.findAll(Role.AGENT)).thenReturn(expectedList);

        List<EmployeeResponseDTO> result = employeeService.getAllEmployees(Role.AGENT);

        assertThat(result).hasSize(2);
        assertThat(result).isEqualTo(expectedList);
        assertThat(result.get(0).getName()).isEqualTo("Juan Pérez");
        assertThat(result.get(1).getName()).isEqualTo("María López");

        verify(employeeDAO, times(1)).findAll(Role.AGENT);
    }

    @Test
    @DisplayName("GET ALL - Debe retornar lista vacía cuando no hay empleados")
    void getAllEmployees_NoEmployees_ShouldReturnEmptyList() {
        when(employeeDAO.findAll(Role.AGENT)).thenReturn(List.of());

        List<EmployeeResponseDTO> result = employeeService.getAllEmployees(Role.AGENT);

        assertThat(result).isEmpty();
        verify(employeeDAO, times(1)).findAll(Role.AGENT);
    }

    // ==================== GET EMPLOYEE BY EMAIL TESTS ====================
    @Test
    @DisplayName("GET BY EMAIL - Debe retornar empleado existente")
    void getEmployeeByEmail_ExistingEmail_ShouldReturnEmployee() {
        when(employeeDAO.findByEmail(validEmail)).thenReturn(Optional.of(responseDTO));

        EmployeeResponseDTO result = employeeService.getEmployeeByEmail(validEmail);

        assertThat(result).isNotNull();
        assertThat(result.getEmail()).isEqualTo(validEmail);
        assertThat(result.getName()).isEqualTo("Juan Pérez");

        verify(employeeDAO, times(1)).findByEmail(validEmail);
    }

    @Test
    @DisplayName("GET BY EMAIL - Debe lanzar ResourceNotFoundException si no se encuentra")
    void getEmployeeByEmail_NonExistentEmail_ShouldThrowException() {
        String nonExistentEmail = "noexiste@empresa.com";
        when(employeeDAO.findByEmail(nonExistentEmail)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> employeeService.getEmployeeByEmail(nonExistentEmail))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Empleado no encontrado con el email");

        verify(employeeDAO, times(1)).findByEmail(nonExistentEmail);
    }
}
