package com.leoalelui.ticketsystem.domain;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.leoalelui.ticketsystem.domain.dto.request.TicketRecordCreateDTO;
import com.leoalelui.ticketsystem.domain.dto.response.TicketRecordResponseDTO;
import com.leoalelui.ticketsystem.domain.exception.InvalidStateException;
import com.leoalelui.ticketsystem.domain.service.impl.TicketRecordServiceImpl;
import com.leoalelui.ticketsystem.persistence.dao.TicketRecordDAO;
import com.leoalelui.ticketsystem.persistence.enums.State;

@ExtendWith(MockitoExtension.class)
@DisplayName("TicketRecordService - Unit Tests")
public class TicketRecordServiceTest {

    @Mock
    private TicketRecordDAO ticketRecordDAO;

    @InjectMocks
    private TicketRecordServiceImpl ticketRecordService;

    private TicketRecordCreateDTO validTicketRecordCreateDTO;
    private TicketRecordResponseDTO validTicketRecordResponse;
    private Long validTicketId;
    private Long validRecordId;

    @BeforeEach
    public void setUp() {
        validTicketId = 1L;
        validRecordId = 1L;

        validTicketRecordCreateDTO = new TicketRecordCreateDTO(
                validTicketId,
                State.ABIERTO,
                State.EN_PROGRESO);

        validTicketRecordResponse = new TicketRecordResponseDTO(
                validRecordId, State.ABIERTO.toString(), State.EN_PROGRESO.toString(), LocalDateTime.now());
    }

    // ==================== CREATE TICKET RECORD TESTS ====================

    @Test
    @DisplayName("CREATE - Registro válido con estados diferentes debe crear registro")
    void createTicketRecord_ValidData_ShouldCreateRecord() {
        when(ticketRecordDAO.create(any(TicketRecordCreateDTO.class)))
                .thenReturn(validTicketRecordResponse);

        TicketRecordResponseDTO result = ticketRecordService.create(validTicketRecordCreateDTO);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(validRecordId);
        assertThat(result.getPreviousState()).isEqualTo(State.ABIERTO.toString());
        assertThat(result.getNextState()).isEqualTo(State.EN_PROGRESO.toString());

        verify(ticketRecordDAO, times(1)).create(any(TicketRecordCreateDTO.class));
    }

    @Test
    @DisplayName("CREATE - Estados iguales debe lanzar InvalidStateException")
    void createTicketRecord_SameStates_ShouldThrowInvalidStateException() {
        TicketRecordCreateDTO sameStateDTO = new TicketRecordCreateDTO(
                validTicketId,
                State.ABIERTO,
                State.ABIERTO);

        assertThatThrownBy(() -> ticketRecordService.create(sameStateDTO))
                .isInstanceOf(InvalidStateException.class)
                .hasMessageContaining("El ticket no puede cambiar al mismo estado que el anterior");

        verify(ticketRecordDAO, never()).create(any(TicketRecordCreateDTO.class));
    }

    // ==================== GET BY TICKET ID TESTS ====================

    @Test
    @DisplayName("GET BY TICKET ID - Debe retornar lista de registros del ticket")
    void getByTicketId_ValidTicketId_ShouldReturnRecords() {
        List<TicketRecordResponseDTO> expectedList = Arrays.asList(
                new TicketRecordResponseDTO(1L, State.ABIERTO.toString(), State.EN_PROGRESO.toString(), LocalDateTime.now()),
                new TicketRecordResponseDTO(2L, State.EN_PROGRESO.toString(), State.RESUELTO.toString(), LocalDateTime.now())
        );

        when(ticketRecordDAO.findTicketRecordByTicketId(validTicketId))
                .thenReturn(expectedList);

        List<TicketRecordResponseDTO> result = ticketRecordService.getByTicketId(validTicketId);

        assertThat(result).hasSize(2);
        verify(ticketRecordDAO, times(1)).findTicketRecordByTicketId(validTicketId);
    }

    // ==================== GET BY DATE RANGE TESTS ====================

    @Test
    @DisplayName("GET BY DATE RANGE - Ambas fechas null debe retornar todos los registros")
    void getByDateRange_BothDatesNull_ShouldReturnAllRecords() {
        List<TicketRecordResponseDTO> expectedList = Arrays.asList(
                new TicketRecordResponseDTO(1L, State.ABIERTO.toString(), State.EN_PROGRESO.toString(), LocalDateTime.now())
        );

        when(ticketRecordDAO.findTicketRecordByTicketId(validTicketId))
                .thenReturn(expectedList);

        List<TicketRecordResponseDTO> result = ticketRecordService.getByDateRange(validTicketId, null, null);

        assertThat(result).hasSize(1);
        verify(ticketRecordDAO, times(1)).findTicketRecordByTicketId(validTicketId);
        verify(ticketRecordDAO, never()).findTicketRecordByTicketIdAndDateRange(any(), any(), any());
    }

    @Test
    @DisplayName("GET BY DATE RANGE - Solo 'from' especificado debe usar fecha actual como 'to'")
    void getByDateRange_OnlyFromSpecified_ShouldUseCurrentDateAsTo() {
        LocalDate fromDate = LocalDate.of(2025, 1, 1);
        LocalDateTime expectedStart = fromDate.atStartOfDay();

        List<TicketRecordResponseDTO> expectedList = Arrays.asList(
                new TicketRecordResponseDTO(1L, State.ABIERTO.toString(), State.EN_PROGRESO.toString(), LocalDateTime.now())
        );

        when(ticketRecordDAO.findTicketRecordByTicketIdAndDateRange(
                eq(validTicketId), 
                eq(expectedStart), 
                any(LocalDateTime.class)))
                .thenReturn(expectedList);

        List<TicketRecordResponseDTO> result = ticketRecordService.getByDateRange(validTicketId, fromDate, null);

        assertThat(result).hasSize(1);
        verify(ticketRecordDAO, times(1)).findTicketRecordByTicketIdAndDateRange(
                eq(validTicketId), 
                eq(expectedStart), 
                any(LocalDateTime.class));
    }

    @Test
    @DisplayName("GET BY DATE RANGE - Rango válido debe retornar registros filtrados")
    void getByDateRange_ValidRange_ShouldReturnFilteredRecords() {
        LocalDate fromDate = LocalDate.of(2025, 1, 1);
        LocalDate toDate = LocalDate.of(2025, 12, 31);
        LocalDateTime expectedStart = fromDate.atStartOfDay();
        LocalDateTime expectedEnd = toDate.atTime(LocalTime.MAX);

        List<TicketRecordResponseDTO> expectedList = Arrays.asList(
                new TicketRecordResponseDTO(1L, State.ABIERTO.toString(), State.EN_PROGRESO.toString(), LocalDateTime.now())
        );

        when(ticketRecordDAO.findTicketRecordByTicketIdAndDateRange(validTicketId, expectedStart, expectedEnd))
                .thenReturn(expectedList);

        List<TicketRecordResponseDTO> result = ticketRecordService.getByDateRange(validTicketId, fromDate, toDate);

        assertThat(result).hasSize(1);
        verify(ticketRecordDAO, times(1)).findTicketRecordByTicketIdAndDateRange(validTicketId, expectedStart, expectedEnd);
    }

    @Test
    @DisplayName("GET BY DATE RANGE - 'from' posterior a 'to' debe lanzar InvalidStateException")
    void getByDateRange_FromAfterTo_ShouldThrowInvalidStateException() {
        LocalDate fromDate = LocalDate.of(2025, 12, 31);
        LocalDate toDate = LocalDate.of(2025, 1, 1);

        assertThatThrownBy(() -> ticketRecordService.getByDateRange(validTicketId, fromDate, toDate))
                .isInstanceOf(InvalidStateException.class)
                .hasMessageContaining("'from' no puede ser posterior a 'to'");

        verify(ticketRecordDAO, never()).findTicketRecordByTicketIdAndDateRange(any(), any(), any());
    }
}
