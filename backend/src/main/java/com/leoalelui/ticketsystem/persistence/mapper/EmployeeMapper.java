package com.leoalelui.ticketsystem.persistence.mapper;

import com.leoalelui.ticketsystem.domain.dto.request.EmployeeCreateDTO;
import com.leoalelui.ticketsystem.domain.dto.response.EmployeeResponseDTO;
import com.leoalelui.ticketsystem.domain.dto.request.EmployeeUpdateDTO;
import com.leoalelui.ticketsystem.persistence.entity.EmployeeEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    EmployeeEntity toEntity(EmployeeCreateDTO employeeCreateDTO);

    EmployeeResponseDTO toResponseDTO(EmployeeEntity employeeEntity);

    @Named("createEmployeeEntityFromId")
    default EmployeeEntity createEmployeeEntityFromId(Long employeeId) {
        if (employeeId == null) {
            return null;
        }
        EmployeeEntity employee = new EmployeeEntity();
        employee.setId(employeeId);
        return employee;
    }
}