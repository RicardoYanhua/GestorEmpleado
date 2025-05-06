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
import org.springframework.security.core.GrantedAuthority;
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
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	private final CustomAccessDeniedHandler customAccessDeniedHandler;

	public SecurityConfig(CustomAccessDeniedHandler customAccessDeniedHandler) {
		this.customAccessDeniedHandler = customAccessDeniedHandler;
	}

	@Bean
	public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
		return new AuthenticationSuccessHandler() {
			@Override
			public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
					Authentication authentication) throws IOException, ServletException {
				response.sendRedirect("/Empleado");
			}
		};
	}

	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(administradorService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http.csrf().disable().authorizeHttpRequests(auth -> auth
				// Rutas públicas
				
				.requestMatchers("/Login").permitAll().requestMatchers(HttpMethod.GET, "/Register").permitAll()
				.requestMatchers(HttpMethod.POST, "/Register").permitAll()
				.requestMatchers("/Styles/**", "/Scripts/**", "/SVG/**").permitAll()
				.requestMatchers("/Empleado/**").hasAnyRole("ADMINISTRADOR").requestMatchers("/Area/**")
				.hasAnyRole("ADMINISTRADOR").requestMatchers("/Banco/**").hasAnyRole("ADMINISTRADOR")
				.requestMatchers("/Contrato/**").hasAnyRole("ADMINISTRADOR")
				.anyRequest().authenticated())
				.formLogin(form -> form.loginPage("/Login").successHandler(customAuthenticationSuccessHandler())
						.failureUrl("/Login?Error=true"))
				.exceptionHandling().accessDeniedHandler(customAccessDeniedHandler).and()
				.logout(config -> config.logoutUrl("/Logout").logoutSuccessUrl("/Login").clearAuthentication(true)
						.invalidateHttpSession(true))
				.build();
	}
}

@Component
class CustomAccessDeniedHandler implements AccessDeniedHandler {
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		response.sendRedirect("/Login");
	}
}
