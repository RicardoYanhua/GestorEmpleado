package com.unu.web.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.unu.web.entity.Empleado;

public interface EmpleadoService {
	
	public abstract Page ListarEmpleado(Pageable paginacion, String busqueda, String filtro);
	public abstract Empleado InsertarEmpleado(Empleado empleado);
	public abstract void ActualizarEmpleado(Empleado empleado);
	public abstract Empleado ObtenerEmpleado(String codigoEmpleado);
	public abstract boolean CodigoEmpleadoExiste(String codigoEmpleado);
	public abstract boolean ValidarExistDni(String dni);
	
}
