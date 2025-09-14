package com.leoalelui.ticketsystem.persistence.dao;

import com.leoalelui.ticketsystem.domain.dto.request.AssignmentCreateDTO;
import com.leoalelui.ticketsystem.domain.dto.response.AssignmentResponseDTO;
import com.leoalelui.ticketsystem.persistence.entity.AssignmentEntity;
import com.leoalelui.ticketsystem.persistence.mapper.AssignmentMapper;
import com.leoalelui.ticketsystem.persistence.repository.AssignmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * DAO para gestionar asignaciones en la capa de persistencia.
 * @author Leonardo
 */
@Repository
@RequiredArgsConstructor
public class AssignmentDAO {

    private final AssignmentRepository assignmentRepository;
    private final AssignmentMapper assignmentMapper;

    @Transactional
    public AssignmentResponseDTO save(AssignmentCreateDTO createDTO) {
        AssignmentEntity entity = assignmentMapper.toEntity(createDTO);
        AssignmentEntity saved = assignmentRepository.save(entity);
        return assignmentMapper.toDTO(saved);
    }

    @Transactional(readOnly = true)
    public List<AssignmentResponseDTO> getByEmployeeId(Long employeeId) {
        return assignmentMapper.toDTOList(assignmentRepository.findByEmployeeId(employeeId));
    }

    @Transactional(readOnly = true)
    public AssignmentResponseDTO getByTicketId(Long ticketId) {
        AssignmentEntity entity = assignmentRepository.findByTicketId(ticketId);
        return entity != null ? assignmentMapper.toDTO(entity) : null;
    }

    @Transactional(readOnly = true)
    public Optional<AssignmentEntity> findById(Long id) {
        return assignmentRepository.findById(id);
    }

    @Transactional
    public AssignmentResponseDTO update(AssignmentEntity entity) {
        AssignmentEntity updated = assignmentRepository.save(entity);
        return assignmentMapper.toDTO(updated);
    }
}
