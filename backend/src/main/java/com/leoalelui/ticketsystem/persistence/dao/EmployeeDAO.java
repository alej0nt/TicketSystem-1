package com.leoalelui.ticketsystem.persistence.dao;

import com.leoalelui.ticketsystem.domain.dto.request.EmployeeCreateDTO;
import com.leoalelui.ticketsystem.domain.dto.request.EmployeeUpdateDTO;
import com.leoalelui.ticketsystem.domain.dto.response.EmployeeResponseDTO;
import com.leoalelui.ticketsystem.persistence.entity.EmployeeEntity;
import com.leoalelui.ticketsystem.persistence.enums.Role;
import com.leoalelui.ticketsystem.persistence.mapper.EmployeeMapper;
import com.leoalelui.ticketsystem.persistence.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class EmployeeDAO {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    // Crear
    public EmployeeResponseDTO save(EmployeeCreateDTO createDTO) {
        EmployeeEntity entity = employeeMapper.toEntity(createDTO);
        entity.setRole(Role.USER); // valor por defecto
        return employeeMapper.toResponseDTO(employeeRepository.save(entity));
    }

    // Actualizar
    public Optional<EmployeeResponseDTO> update(Long id, EmployeeUpdateDTO updateDTO) {
        return employeeRepository.findById(id)
                .map(existingEntity -> {
                    existingEntity.setName(updateDTO.getName());
                    existingEntity.setEmail(updateDTO.getEmail());
                    existingEntity.setRole(updateDTO.getRole());
                    existingEntity.setDepartment(updateDTO.getDepartment());
                    return employeeMapper.toResponseDTO(employeeRepository.save(existingEntity));
                });
    }

    // Eliminar
    public void deleteById(Long id) {
        employeeRepository.deleteById(id);
    }

    // Buscar por ID
    public Optional<EmployeeResponseDTO> findById(Long id) {
        return employeeRepository.findById(id)
                .map(employeeMapper::toResponseDTO);
    }

    // Buscar por email
    public Optional<EmployeeResponseDTO> findByEmail(String email) {
        return employeeRepository.findByEmail(email)
                .map(employeeMapper::toResponseDTO);
    }

    // Listar todos
    public List<EmployeeResponseDTO> findAll() {
        return employeeRepository.findAll()
                .stream()
                .map(employeeMapper::toResponseDTO)
                .toList();
    }

    // Validaciones
    public boolean existsByEmail(String email) {
        return employeeRepository.existsByEmail(email);
    }

    public boolean existsById(Long id) {
        return employeeRepository.existsById(id);
    }
}
