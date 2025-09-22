package com.leoalelui.ticketsystem.persistence.mapper;

import com.leoalelui.ticketsystem.domain.dto.request.EvidenceCreateDTO;
import com.leoalelui.ticketsystem.domain.dto.response.EvidenceResponseDTO;
import com.leoalelui.ticketsystem.persistence.entity.EvidenceEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EvidenceMapper {
    @Mapping(target = "id", ignore = true)
    EvidenceEntity toEntity(EvidenceCreateDTO dto);
    EvidenceResponseDTO toResponseDTO(EvidenceEntity entity);
    List<EvidenceResponseDTO> toDTOList(List<EvidenceEntity> entities);
}
