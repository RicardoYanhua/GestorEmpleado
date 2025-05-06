package com.unu.web.repository;

import java.io.Serializable;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.unu.web.entity.Contrato;
import com.unu.web.entity.Empleado;

@Repository("contratoRepository")
public interface ContratoRepository extends JpaRepository<Contrato, Serializable> {

	@Query("SELECT c FROM Contrato c WHERE c.contratoEmpleadoId = :emp")
	public List<Contrato> ListaContratoPorEmpleado(@Param("emp") Empleado empleado);
	
	@Query("SELECT c FROM Contrato c")
	public List<Contrato> ObtenerContratosFechas();
	
	@Query("SELECT COUNT(c) > 0 FROM Contrato c WHERE c.contratoEstado IN ('V', 'P') AND c.contratoEmpleadoId = :emp")
    public boolean TieneContrato(@Param("emp") Empleado empleadoId);
	
	@Query("SELECT c FROM Contrato c WHERE c.contratoEstado IN ('C') AND c.contratoEmpleadoId = :emp")
	public List<Contrato> ListarContratosEmpleadoCaducados(@Param("emp") Empleado empleadoId);
	
	@Query("SELECT c FROM Contrato c WHERE c.contratoEstado IN ('V', 'P') AND c.contratoEmpleadoId = :emp")
	
    public Contrato ObtenerContrato(@Param("emp") Empleado empleadoId);
	
}
