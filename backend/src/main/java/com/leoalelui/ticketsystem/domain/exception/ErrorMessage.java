package com.leoalelui.ticketsystem.domain.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class ErrorMessage {
    private String message;
}
