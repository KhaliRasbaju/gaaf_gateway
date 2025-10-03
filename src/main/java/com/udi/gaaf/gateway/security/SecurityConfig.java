package com.udi.gaaf.gateway.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
	
	@Bean
	public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
        .csrf(csrf -> csrf.disable())
            .authorizeExchange(exchanges -> exchanges

   
                // Cualquier otra ruta
                .anyExchange().permitAll()
                
         
            )
            
            .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
            .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable) 
            ;
            // Habilitar validación de JWT en los headers
            
        return http.build();
    }

}
