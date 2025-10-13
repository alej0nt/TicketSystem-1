package com.leoalelui.ticketsystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

/**
 * Configuración de CORS para permitir peticiones desde el frontend Angular.
 * 
 * @author Leonardo Argoty Roa
 */
@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true);
        //Permite el origen de Angular en Desarrollo
        config.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
        config.setAllowedHeaders(Arrays.asList("*"));
        config.setAllowedMethods(Arrays.asList("*"));

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}