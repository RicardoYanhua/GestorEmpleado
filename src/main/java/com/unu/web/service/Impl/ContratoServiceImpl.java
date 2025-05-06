package com.unu.web.service.Impl;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.unu.web.entity.Contrato;
import com.unu.web.entity.Contrato.Estado;
import com.unu.web.entity.Empleado;
import com.unu.web.repository.ContratoRepository;
import com.unu.web.service.ContratoService;

import jakarta.transaction.Transactional;

@Service("contratoService")

public class ContratoServiceImpl implements ContratoService {

	@Autowired
	@Qualifier("contratoRepository")
	private ContratoRepository contratoRepository;

	@Override
	public Page PaginaContrato(Pageable paginacion) {
		Page<Contrato> lista = contratoRepository.findAll(PageRequest.of(paginacion.getPageNumber(), 12));
		return lista;
	}

	@Override
	public List<Contrato> ListarContrato() {
		return contratoRepository.findAll();
	}

	@Override
	public List<Contrato> ListaContratoEmpleado(Empleado empleado) {
		return contratoRepository.ListaContratoPorEmpleado(empleado);
	}

	@Override
	public Contrato InsertarContrato(Contrato contrato) {
		return contratoRepository.save(contrato);
	}

	@Override
	public void ActualizarContrato(Contrato contrato) {
		contratoRepository.save(contrato);
	}

	@Override
	public Contrato ObtenerContrato(int idContrato) {
		return contratoRepository.findById(idContrato)
				.orElseThrow(() -> new IllegalArgumentException("No se encontro el Contrato con ID : " + idContrato));
	}

	@Override
	public boolean TieneContrato(Empleado emp) {
		return contratoRepository.TieneContrato(emp);
	}

	@Transactional
	public void ActualizarEstadosContratos() {
	    List<Contrato> contratos = contratoRepository.ObtenerContratosFechas();
	    LocalDate fechaActual = LocalDate.now();

	    for (Contrato contrato : contratos) {
	        LocalDate fechaInicio = contrato.getContratoFechaInicio();
	        LocalDate fechaFin = contrato.getContratoFechaFin();

	        if (fechaInicio == null) {
	            continue; // No se puede evaluar el estado sin fecha de inicio
	        }

	        if (fechaActual.isBefore(fechaInicio)) {
	            contrato.setContratoEstado(Estado.P); // Pendiente
	        } else if (fechaFin == null || fechaActual.isEqual(fechaInicio) || 
	                   (fechaActual.isAfter(fechaInicio) && fechaActual.isBefore(fechaFin))) {
	            contrato.setContratoEstado(Estado.V); // Vigente
	        } else if (fechaActual.isAfter(fechaFin)) {
	            contrato.setContratoEstado(Estado.C); // Caducado
	        }
	    }

	    contratoRepository.saveAll(contratos);
	}


	public Estado DeterminarEstadoDelContrato(Contrato contrato) {

		if (contrato.getContratoFechaInicio() == null) {
			return null;
		}

		if (contrato.getContratoFechaFin() != null && contrato.getContratoFechaFin().isBefore(contrato.getContratoFechaInicio())) {
			return null;
		}
		if (contrato.getContratoFechaFin() != null && contrato.getContratoFechaFin().isEqual(contrato.getContratoFechaInicio())) {
			return null;
		}

		LocalDate fechaActual = LocalDate.now();

		if (fechaActual.isBefore(contrato.getContratoFechaInicio())) {
			return Estado.P;
		} else if (fechaActual.isEqual(contrato.getContratoFechaInicio())
				|| (fechaActual.isAfter(contrato.getContratoFechaInicio()))) {
			return Estado.V;
		} else if (fechaActual.isEqual(contrato.getContratoFechaInicio())
				|| (fechaActual.isAfter(contrato.getContratoFechaInicio())
						&& fechaActual.isBefore(contrato.getContratoFechaFin()))) {
			return Estado.V;
		} else if (fechaActual.isAfter(contrato.getContratoFechaFin())) {
			return Estado.C;
		}

		return null;
	}

	@Override
	public Contrato ObtenerContrato(Empleado emp) {
		if(TieneContrato(emp)) {
			return contratoRepository.ObtenerContrato(emp);
		}
		return null;
	}

	@Override
	public List<Contrato> ListaContratoEmpleadoCaducado(Empleado empleado) {
		return contratoRepository.ListarContratosEmpleadoCaducados(empleado);
	}

}