package com.leoalelui.ticketsystem.domain;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.leoalelui.ticketsystem.domain.dto.request.EvidenceCreateDTO;
import com.leoalelui.ticketsystem.domain.dto.response.CategoryResponseDTO;
import com.leoalelui.ticketsystem.domain.dto.response.EvidenceResponseDTO;
import com.leoalelui.ticketsystem.domain.dto.response.TicketResponseDTO;
import com.leoalelui.ticketsystem.domain.exception.ResourceNotFoundException;
import com.leoalelui.ticketsystem.domain.service.impl.EvidenceServiceImpl;
import com.leoalelui.ticketsystem.domain.service.impl.TicketServiceImpl;
import com.leoalelui.ticketsystem.persistence.dao.EvidenceDAO;
import com.leoalelui.ticketsystem.persistence.enums.Priority;
import com.leoalelui.ticketsystem.persistence.enums.State;

@ExtendWith(MockitoExtension.class)
@DisplayName("EvidenceService - Unit Test")
public class EvidenceServiceTest {

    @Mock
    private EvidenceDAO evidenceDAO;

    @Mock
    private TicketServiceImpl ticketService;

    @InjectMocks
    private EvidenceServiceImpl evidenceService;

    private EvidenceCreateDTO createDTO;
    private EvidenceResponseDTO responseDTO;
    private TicketResponseDTO ticketResponseDTO;
    private CategoryResponseDTO categoryResponseDTO;

    private Long validEvidenceId;
    private Long validTicketId;

    @BeforeEach
    void setUp() {
        validEvidenceId = 1L;
        validTicketId = 200L;

        createDTO = new EvidenceCreateDTO();
        createDTO.setTicketId(validTicketId);
        createDTO.setUrl("https://example.com/evidence1.png");

        categoryResponseDTO = new CategoryResponseDTO(1L, "Tecnología", "Categoría de tecnología");

        ticketResponseDTO = new TicketResponseDTO(
                validTicketId,
                100L,
                categoryResponseDTO,
                "Problema con el sistema",
                "Descripción del problema",
                Priority.ALTA,
                State.ABIERTO,
                LocalDateTime.now(),
                null
        );

        responseDTO = new EvidenceResponseDTO();
        responseDTO.setId(validEvidenceId);
        responseDTO.setTicketId(validTicketId);
        responseDTO.setUrl("https://example.com/evidence1.png");
    }

    // ==================== FIND BY ID TESTS ====================
    @Test
    @DisplayName("FIND BY ID - Debe retornar evidencia existente")
    void findById_ExistingId_ShouldReturnEvidence() {
        when(evidenceDAO.findById(validEvidenceId)).thenReturn(Optional.of(responseDTO));

        EvidenceResponseDTO result = evidenceService.findById(validEvidenceId);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(validEvidenceId);
        assertThat(result.getTicketId()).isEqualTo(validTicketId);
        assertThat(result.getUrl()).isEqualTo("https://example.com/evidence1.png");

        verify(evidenceDAO, times(1)).findById(validEvidenceId);
    }

    @Test
    @DisplayName("FIND BY ID - Debe lanzar ResourceNotFoundException si no se encuentra")
    void findById_NonExistentId_ShouldThrowException() {
        when(evidenceDAO.findById(validEvidenceId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> evidenceService.findById(validEvidenceId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("No se ha encontrado");

        verify(evidenceDAO, times(1)).findById(validEvidenceId);
    }

    // ==================== FIND ALL BY TICKET ID TESTS ====================
    @Test
    @DisplayName("FIND ALL BY TICKET ID - Debe retornar lista de evidencias del ticket")
    void findAllByTicketId_ExistingTicket_ShouldReturnEvidenceList() {
        EvidenceResponseDTO evidence2 = new EvidenceResponseDTO();
        evidence2.setId(2L);
        evidence2.setTicketId(validTicketId);
        evidence2.setUrl("https://example.com/evidence2.png");

        List<EvidenceResponseDTO> expectedList = List.of(responseDTO, evidence2);

        when(ticketService.getTicketById(validTicketId)).thenReturn(ticketResponseDTO);
        when(evidenceDAO.findAllByTicketId(validTicketId)).thenReturn(expectedList);

        List<EvidenceResponseDTO> result = evidenceService.findAllByTicketId(validTicketId);

        assertThat(result).hasSize(2);
        assertThat(result).isEqualTo(expectedList);
        assertThat(result.get(0).getTicketId()).isEqualTo(validTicketId);
        assertThat(result.get(1).getTicketId()).isEqualTo(validTicketId);

        verify(ticketService, times(1)).getTicketById(validTicketId);
        verify(evidenceDAO, times(1)).findAllByTicketId(validTicketId);
    }

    @Test
    @DisplayName("FIND ALL BY TICKET ID - Debe retornar lista vacía si no hay evidencias")
    void findAllByTicketId_NoEvidences_ShouldReturnEmptyList() {
        when(ticketService.getTicketById(validTicketId)).thenReturn(ticketResponseDTO);
        when(evidenceDAO.findAllByTicketId(validTicketId)).thenReturn(List.of());

        List<EvidenceResponseDTO> result = evidenceService.findAllByTicketId(validTicketId);

        assertThat(result).isEmpty();
        verify(ticketService, times(1)).getTicketById(validTicketId);
        verify(evidenceDAO, times(1)).findAllByTicketId(validTicketId);
    }

    @Test
    @DisplayName("FIND ALL BY TICKET ID - Debe lanzar ResourceNotFoundException si el ticket no existe")
    void findAllByTicketId_NonExistentTicket_ShouldThrowException() {
        when(ticketService.getTicketById(validTicketId))
                .thenThrow(new ResourceNotFoundException("Tiquete no encontrado con ID: " + validTicketId));

        assertThatThrownBy(() -> evidenceService.findAllByTicketId(validTicketId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Tiquete no encontrado con ID:");

        verify(ticketService, times(1)).getTicketById(validTicketId);
        verify(evidenceDAO, never()).findAllByTicketId(any());
    }

    // ==================== SAVE TESTS ====================
    @Test
    @DisplayName("SAVE - Debe guardar una evidencia cuando el ticket existe")
    void save_ValidTicket_ShouldSaveEvidence() {
        when(ticketService.getTicketById(validTicketId)).thenReturn(ticketResponseDTO);
        when(evidenceDAO.save(createDTO)).thenReturn(responseDTO);

        EvidenceResponseDTO result = evidenceService.save(createDTO);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(validEvidenceId);
        assertThat(result.getTicketId()).isEqualTo(validTicketId);
        assertThat(result.getUrl()).isEqualTo("https://example.com/evidence1.png");

        verify(ticketService, times(1)).getTicketById(validTicketId);
        verify(evidenceDAO, times(1)).save(createDTO);
    }

    @Test
    @DisplayName("SAVE - Debe lanzar ResourceNotFoundException si el ticket no existe")
    void save_NonExistentTicket_ShouldThrowException() {
        when(ticketService.getTicketById(validTicketId))
                .thenThrow(new ResourceNotFoundException("Tiquete no encontrado con ID: " + validTicketId));

        assertThatThrownBy(() -> evidenceService.save(createDTO))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Tiquete no encontrado con ID:");

        verify(ticketService, times(1)).getTicketById(validTicketId);
        verify(evidenceDAO, never()).save(any());
    }

    // ==================== DELETE TESTS ====================
    @Test
    @DisplayName("DELETE - Debe eliminar evidencia existente")
    void delete_ExistingId_ShouldDeleteEvidence() {
        when(evidenceDAO.findById(validEvidenceId)).thenReturn(Optional.of(responseDTO));

        evidenceService.delete(validEvidenceId);

        verify(evidenceDAO, times(1)).findById(validEvidenceId);
        verify(evidenceDAO, times(1)).delete(validEvidenceId);
    }

    @Test
    @DisplayName("DELETE - Debe lanzar ResourceNotFoundException si la evidencia no existe")
    void delete_NonExistentId_ShouldThrowException() {
        when(evidenceDAO.findById(validEvidenceId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> evidenceService.delete(validEvidenceId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("No se ha encontrado");

        verify(evidenceDAO, times(1)).findById(validEvidenceId);
        verify(evidenceDAO, never()).delete(any());
    }
}