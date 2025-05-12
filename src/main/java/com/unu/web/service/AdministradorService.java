package com.unu.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.unu.web.entity.Administrador;
import com.unu.web.repository.AdministradorRepository;

import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Service
@Qualifier("administradorService")
public class AdministradorService implements UserDetailsService {

	@Autowired
	private AdministradorRepository administradorRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		 if (username == null || username.isEmpty()) {
		        throw new UsernameNotFoundException("Nombre de usuario no proporcionado");
		    }
		    Administrador adm = administradorRepository.BuscarNombreUsuario(username);
		    if (adm == null) {
		        throw new UsernameNotFoundException("Usuario no encontrado");
		    }
		    if (!adm.isAdmState()) {
	            throw new DisabledException("Cuenta deshabilitada");
	        }
		    return new User(adm.getUsername(), adm.getPassword(), adm.getAuthorities());
	}
	
	public Administrador getAdminUsuario(String username) {
		return administradorRepository.BuscarNombreUsuario(username);
	}
	
	public Administrador getAdminId(Integer id) {
		return administradorRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con ID: " + id));
	}
	
	public void EliminarUsuario(Integer id) {
		if (administradorRepository.existsById(id)) {
			administradorRepository.deleteById(id);
		}
	}
	
	public Page<Administrador> ListarAdministrador(Pageable pageable) {
		Page<Administrador> lista = administradorRepository.findAll(PageRequest.of(pageable.getPageNumber(),20));
		return lista;
	}
	
}