package com.leoalelui.ticketsystem.domain.service.impl;

import com.leoalelui.ticketsystem.domain.dto.request.LoginRequestDTO;
import com.leoalelui.ticketsystem.domain.dto.response.EmployeeResponseDTO;
import com.leoalelui.ticketsystem.domain.dto.response.JwtResponseDTO;
import com.leoalelui.ticketsystem.domain.service.AuthService;
import com.leoalelui.ticketsystem.domain.service.EmployeeService;
import com.leoalelui.ticketsystem.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final EmployeeService employeeService;

    @Override
    public JwtResponseDTO login(LoginRequestDTO loginRequestDTO) throws BadCredentialsException {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequestDTO.getEmail(), loginRequestDTO.getPassword())
        );
        String token = jwtUtil.generateToken(loginRequestDTO.getEmail());
        EmployeeResponseDTO employee = employeeService.getEmployeeByEmail(loginRequestDTO.getEmail());
        return new JwtResponseDTO(token, employee);
    }
}
