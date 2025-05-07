package com.unu.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.unu.web.service.AdministradorService;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	@Qualifier("administradorService")
	private AdministradorService administradorService;

	@Bean
	public BCryptPasswordEncoder CodificadorDeContraseñas() {
		return new BCryptPasswordEncoder();
	}

	private final Redireccionamiento_AccesoDenegado redireccionamiento_AccesoDenegado;

	public SecurityConfig(Redireccionamiento_AccesoDenegado redireccionamiento_AccesoDenegado) {
		this.redireccionamiento_AccesoDenegado = redireccionamiento_AccesoDenegado;
	}

	@Bean
	public AuthenticationSuccessHandler Redireccionamiento_AutentificacionExitoso() {
		return new AuthenticationSuccessHandler() {
			@Override
			public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
					Authentication authentication) throws IOException, ServletException {
				response.sendRedirect("/Empleado");
			}
		};
	}

	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(administradorService).passwordEncoder(CodificadorDeContraseñas());
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http
			// Deshabilita CSRF para evitar problemas en pruebas (activar en producción si es necesario)
			.csrf().disable()

			// Configuración de autorización por rutas
			.authorizeHttpRequests(auth -> auth

				// ==== Rutas públicas ====
				.requestMatchers("/Login").permitAll()
				.requestMatchers(HttpMethod.GET, "/Register").permitAll()
				.requestMatchers(HttpMethod.POST, "/Register").permitAll()
				.requestMatchers("/Styles/**", "/Scripts/**", "/SVG/**").permitAll()

				// ==== Rutas protegidas (solo para ADMINISTRADOR) ====
				.requestMatchers("/Empleado/**").hasAnyRole("ADMINISTRADOR")
				.requestMatchers("/Area/**").hasAnyRole("ADMINISTRADOR")
				.requestMatchers("/Banco/**").hasAnyRole("ADMINISTRADOR")
				.requestMatchers("/Contrato/**").hasAnyRole("ADMINISTRADOR")

				// Cualquier otra ruta requiere autenticación
				.anyRequest().authenticated()
			)

			// Configuración del formulario de login
			.formLogin(form -> form
				.loginPage("/Login") // Página personalizada de login
				.successHandler(Redireccionamiento_AutentificacionExitoso()) // Redirección si el login es exitoso
				.failureUrl("/Login?Error=true") // Redirección si el login falla
			)

			// Manejador de acceso denegado personalizado
			.exceptionHandling().accessDeniedHandler(redireccionamiento_AccesoDenegado)

			// Configuración de logout
			.and()
			.logout(config -> config
				.logoutUrl("/Logout") // URL para cerrar sesión
				.logoutSuccessUrl("/Login") // Redirección tras logout
				.clearAuthentication(true) // Limpia la autenticación
				.invalidateHttpSession(true) // Invalida la sesión
			)

			// Construye la cadena de filtros de seguridad
			.build();
	}

}

@Component
class Redireccionamiento_AccesoDenegado implements AccessDeniedHandler {
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		response.sendRedirect("/Login");
	}
}
