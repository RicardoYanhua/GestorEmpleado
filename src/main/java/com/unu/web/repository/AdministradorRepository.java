package com.unu.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.unu.web.entity.Administrador;

@Repository("administradorRepository")
public interface AdministradorRepository extends JpaRepository<Administrador, Integer>{
	
	@Query("SELECT a FROM Administrador a WHERE a.admNombreUsuario = :usuario")
	public Administrador BuscarNombreUsuario(@Param("usuario") String NombreUsuario);
	
	
}



