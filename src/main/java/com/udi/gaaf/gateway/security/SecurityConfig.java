package com.udi.gaaf.gateway.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

	@Autowired
    private  CustomAccessDeniedHandler accessDeniedHandler;
	
	@Autowired
    private  CustomAuthenticationEntryPoint authenticationEntryPoint;
	
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {

        http.csrf(ServerHttpSecurity.CsrfSpec::disable)
            .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
            .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
            .authorizeExchange(auth -> auth
            	.pathMatchers("/api/autentificacion/auth/registrar").permitAll()
                .pathMatchers("/api/autentificacion/auth/iniciar").permitAll()
                .pathMatchers("/api/autentificacion/usuario/**").permitAll()
                .pathMatchers("/api/inventario/ubicacion/**").permitAll()
                .pathMatchers("/api/inventario/bodega/**", 
                		"/api/inventario/transaccion/**",
                		"/api/inventario/inventario/**").hasAnyRole("JEFE_BODEGA")
                .pathMatchers("/api/inventario/reporte/producto-bodega",
                		"/api/inventario/reporte/inventario-movimiento").hasAnyRole("JEFE_BODEGA", "GERENTE")
                .pathMatchers("/api/inventario/producto/**",
                		"/api/inventario/proveedor/**",
                		"/api/inventario/pedido/**",
                		"/api/inventario/cuenta/**",
                		"/api/inventario/entidad-bancaria/**").hasRole("COORDINADOR_COMPRAS")
                .pathMatchers("/api/inventario/reporte/compra", "/api/inventario/reporte/pedido-proveedor")
                	.hasAnyRole("COORDINADOR_COMPRAS", "GERENTE")
                .anyExchange().authenticated()
            )
            .exceptionHandling(ex -> ex
                .accessDeniedHandler(accessDeniedHandler)
                .authenticationEntryPoint(authenticationEntryPoint)
            ).addFilterAt(jwtAuthenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION);
;

        return http.build();
    }
}
