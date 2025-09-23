package com.leoalelui.ticketsystem.domain.service;

import com.leoalelui.ticketsystem.domain.dto.request.LoginRequestDTO;
import com.leoalelui.ticketsystem.domain.dto.response.JwtResponseDTO;

public interface AuthService {
    JwtResponseDTO login(LoginRequestDTO loginRequestDTO);
}
