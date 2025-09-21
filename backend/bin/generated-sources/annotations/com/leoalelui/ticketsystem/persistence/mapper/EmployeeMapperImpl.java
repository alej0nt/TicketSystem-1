package com.leoalelui.ticketsystem.persistence.mapper;

import com.leoalelui.ticketsystem.domain.dto.request.EmployeeCreateDTO;
import com.leoalelui.ticketsystem.domain.dto.response.EmployeeResponseDTO;
import com.leoalelui.ticketsystem.persistence.entity.EmployeeEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-21T16:21:43-0500",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.43.0.v20250819-1513, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class EmployeeMapperImpl implements EmployeeMapper {

    @Override
    public EmployeeEntity toEntity(EmployeeCreateDTO employeeCreateDTO) {
        if ( employeeCreateDTO == null ) {
            return null;
        }

        EmployeeEntity employeeEntity = new EmployeeEntity();

        employeeEntity.setDepartment( employeeCreateDTO.getDepartment() );
        employeeEntity.setEmail( employeeCreateDTO.getEmail() );
        employeeEntity.setName( employeeCreateDTO.getName() );
        employeeEntity.setPassword( employeeCreateDTO.getPassword() );

        return employeeEntity;
    }

    @Override
    public EmployeeResponseDTO toResponseDTO(EmployeeEntity employeeEntity) {
        if ( employeeEntity == null ) {
            return null;
        }

        String department = null;
        String email = null;
        Long id = null;
        String name = null;
        String role = null;

        department = employeeEntity.getDepartment();
        email = employeeEntity.getEmail();
        id = employeeEntity.getId();
        name = employeeEntity.getName();
        if ( employeeEntity.getRole() != null ) {
            role = employeeEntity.getRole().name();
        }

        EmployeeResponseDTO employeeResponseDTO = new EmployeeResponseDTO( id, name, email, role, department );

        return employeeResponseDTO;
    }
}
