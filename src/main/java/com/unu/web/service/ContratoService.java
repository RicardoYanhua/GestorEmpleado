package com.unu.web.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.unu.web.entity.Contrato;
import com.unu.web.entity.Contrato.Estado;
import com.unu.web.entity.Empleado;

public interface ContratoService {
	
	public abstract Page PaginaContrato(Pageable paginacion);
	public abstract List<Contrato> ListarContrato();
	public abstract List<Contrato> ListaContratoEmpleado( Empleado empleado);
	public abstract Contrato InsertarContrato(Contrato contrato);
	public abstract void ActualizarContrato(Contrato contrato);
	public abstract Contrato ObtenerContrato(int idContrato);
	
	public abstract void ActualizarEstadosContratos();
	public abstract boolean TieneContrato(Empleado emp);
	
	public abstract Estado DeterminarEstadoDelContrato(Contrato contrato);
	
}
