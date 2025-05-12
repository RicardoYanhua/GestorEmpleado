package com.unu.web.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.unu.web.entity.Empleado;
import com.unu.web.repository.EmpleadoRepository;
import com.unu.web.service.EmpleadoService;

@Service("empleadoService")
public class EmpleadoServiceImpl implements EmpleadoService {

	@Autowired
	@Qualifier("empleadoRepository")
	private EmpleadoRepository empleadoRepository;
	
	@Override
	public Page<Empleado> ListarEmpleado(Pageable paginacion, String busqueda, String filtro) {
	    Page<Empleado> lista = null;
	    int tamañoDePagina = 6;

	    switch (filtro) {
	        case "Todo":
	            lista = empleadoRepository.ListarPorTodo(PageRequest.of(paginacion.getPageNumber(), tamañoDePagina), busqueda);
	            break;
	        case "Codigo":
	            lista = empleadoRepository.ListarPorCodigo(PageRequest.of(paginacion.getPageNumber(), tamañoDePagina), busqueda);
	            break;
	        case "DNI":
	            lista = empleadoRepository.listarPorDni(PageRequest.of(paginacion.getPageNumber(), tamañoDePagina), busqueda);
	            break;
	        case "Nombres":
	            lista = empleadoRepository.listarPorNombres(PageRequest.of(paginacion.getPageNumber(), tamañoDePagina), busqueda);
	            break;
	        case "A.Paterno":
	            lista = empleadoRepository.listarPorApellidoPaterno(PageRequest.of(paginacion.getPageNumber(), tamañoDePagina), busqueda);
	            break;
	        case "A.Materno":
	            lista = empleadoRepository.listarPorApellidoMaterno(PageRequest.of(paginacion.getPageNumber(), tamañoDePagina), busqueda);
	            break;
	        default:
	            lista = empleadoRepository.findAll(PageRequest.of(paginacion.getPageNumber(), tamañoDePagina));
	            break;
	    }

	    return lista;
	}
	
	@Override
	public Empleado InsertarEmpleado(Empleado empleado) {
		return empleadoRepository.save(empleado);
	}

	@Override
	public void ActualizarEmpleado(Empleado empleado) {
		empleadoRepository.save(empleado);
	}

	@Override
	public Empleado ObtenerEmpleado(String CodigoEmpleado) {
		return empleadoRepository.findById(CodigoEmpleado).orElseThrow(
				() -> new IllegalArgumentException("No se encontro el Empleado con ID : " + CodigoEmpleado));
	}

	public boolean CodigoEmpleadoExiste(String codigoEmpleado) {
		return empleadoRepository.existsById(codigoEmpleado);
	}

	@Override
	public boolean ValidarExistDni(String dni) {
		return empleadoRepository.ValidarExistDni(dni);
	}

}
