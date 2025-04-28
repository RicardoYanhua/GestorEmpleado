package com.unu.web.repository;

import java.io.Serializable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.unu.web.entity.Empleado;

@Repository("empleadoRepository")
public interface EmpleadoRepository extends JpaRepository<Empleado, Serializable> {

	@Query("SELECT e FROM Empleado e WHERE "
			+ "e.empCodigo LIKE CONCAT( :busqueda, '%')")
	public Page<Empleado> ListarPorCodigo(Pageable pageable, @Param("busqueda") String busqueda);
	
	@Query("SELECT e FROM Empleado e WHERE e.empDni LIKE CONCAT(:busqueda, '%')")
	public Page<Empleado> listarPorDni(Pageable pageable, @Param("busqueda") String busqueda);

	@Query("SELECT e FROM Empleado e WHERE e.empNombres LIKE CONCAT(:busqueda, '%')")
	public Page<Empleado> listarPorNombres(Pageable pageable, @Param("busqueda") String busqueda);

	@Query("SELECT e FROM Empleado e WHERE e.empApellidoPaterno LIKE CONCAT(:busqueda, '%')")
	public Page<Empleado> listarPorApellidoPaterno(Pageable pageable, @Param("busqueda") String busqueda);
	
	@Query("SELECT e FROM Empleado e WHERE e.empApellidoMaterno LIKE CONCAT(:busqueda, '%')")
	public Page<Empleado> listarPorApellidoMaterno(Pageable pageable, @Param("busqueda") String busqueda);
	
	@Query("SELECT e FROM Empleado e WHERE " +
		       "(e.empCodigo LIKE CONCAT(:busqueda, '%')) OR " +
		       "(e.empDni LIKE CONCAT(:busqueda, '%')) OR " +
		       "(e.empNombres LIKE CONCAT(:busqueda, '%')) OR " +
		       "(e.empApellidoPaterno LIKE CONCAT(:busqueda, '%')) OR " +
		       "(e.empApellidoMaterno LIKE CONCAT(:busqueda, '%'))")
	public Page<Empleado> ListarPorTodo(Pageable pageable, @Param("busqueda") String busqueda);
	
	
	@Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM Empleado e WHERE e.empDni = :dni")
	public boolean ValidarExistDni(@Param("dni") String dni);
}
