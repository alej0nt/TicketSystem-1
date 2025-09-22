package com.leoalelui.ticketsystem.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.leoalelui.ticketsystem.persistence.entity.EmployeeEntity;
import com.leoalelui.ticketsystem.persistence.repository.EmployeeRepository;

import io.jsonwebtoken.lang.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private EmployeeRepository employeeRepository;

    @Autowired
    public CustomUserDetailsService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<EmployeeEntity> employee = employeeRepository.findByEmail(email);
        if (employee.isEmpty()) {
            throw new UsernameNotFoundException("Empleado no encontrado");
        }
        EmployeeEntity employeeEntity = employee.get();
        return new User(employeeEntity.getEmail(), employeeEntity.getPassword(), Collections.emptyList());
    }

}
