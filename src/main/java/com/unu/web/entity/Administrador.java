package com.unu.web.entity;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "administrador")
public class Administrador implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "admCodigo", nullable = false)
	private Integer admCodigo;

	@NotBlank(message = "El nombre de usuario no puede estar vacío")
	@Size(max = 50, message = "El nombre de usuario no debe exceder 50 caracteres")
	@Column(name = "admNombreUsuario", nullable = false, unique = true, length = 50)
	private String admNombreUsuario;

	@Size(min = 8, max = 255, message = "La contraseña debe tener entre 8 y 255 caracteres")
	@Column(name = "admContrasenia", nullable = false, length = 255)
	private String admContrasenia;

	@NotBlank(message = "El nombre completo no puede estar vacío")
	@Size(max = 100, message = "El nombre completo no debe exceder 100 caracteres")
	@Column(name = "admNombreCompleto", length = 100)
	private String admNombreCompleto;

	

	public Administrador() {
		this.admCodigo = 0;
		this.admNombreUsuario = "";
		this.admContrasenia = "";
		this.admNombreCompleto = "";
	}

	public Administrador(Integer admCodigo, String admNombreUsuario, String admContrasenia, String admNombreCompleto, Boolean admActivo) {
		super();
		this.admCodigo = admCodigo;
		this.admNombreUsuario = admNombreUsuario;
		this.admContrasenia = admContrasenia;
		this.admNombreCompleto = admNombreCompleto;
	}

	public Integer getAdmCodigo() {
		return admCodigo;
	}

	public void setAdmCodigo(Integer admCodigo) {
		this.admCodigo = admCodigo;
	}

	public String getAdmNombreUsuario() {
		return admNombreUsuario;
	}

	public void setAdmNombreUsuario(String admNombreUsuario) {
		this.admNombreUsuario = admNombreUsuario;
	}

	public String getAdmContrasenia() {
		return admContrasenia;
	}

	public void setAdmContrasenia(String admContrasenia) {
		this.admContrasenia = admContrasenia;
	}

	public String getAdmNombreCompleto() {
		return admNombreCompleto;
	}

	public void setAdmNombreCompleto(String admNombreCompleto) {
		this.admNombreCompleto = admNombreCompleto;
	}

	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMINISTRADOR"));
	}

	@Override
	public String getPassword() {
		return admContrasenia;
	}

	@Override
	public String getUsername() {
		return admNombreUsuario;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}