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
	public Contrato ObtenerContrato(int idContrato) {
		return contratoRepository.findById(idContrato)
				.orElseThrow(() -> new IllegalArgumentException("No se encontro el Contrato con ID : " + idContrato));
	}

	@Override
	public boolean TieneContrato(Empleado emp) {
		return contratoRepository.TieneContrato(emp);
	}

	@Override
	public void ActualizarContrato(Contrato contrato) {
		contratoRepository.save(contrato);
	}

	@Override
	public void ActualizarEstadosContratos() {
		List<Contrato> contratos = contratoRepository.findAll();
		LocalDate fechaActual = LocalDate.now();

		for (Contrato contrato : contratos) {

			Estado estado = DeterminarEstadoDelContrato(contrato);
			if (estado == null) {
				System.err.println("Estado nulo para contrato ID: " + contrato.getContratoId());
				continue;
			}

			try {
				contrato.setContratoEstado(estado);
				ActualizarContrato(contrato);

			} catch (Exception e) {
			    Throwable t = e;
			    while (t.getCause() != null) {
			        t = t.getCause();
			    }
			    System.err.println("Error raíz al guardar contrato ID " + contrato.getContratoId() + ": " + t.getMessage());
			    t.printStackTrace();
			}
		}
	}

	@Override
	public Estado DeterminarEstadoDelContrato(Contrato contrato) {
		LocalDate fechaInicio = contrato.getContratoFechaInicio();
		LocalDate fechaFin = contrato.getContratoFechaFin();
		LocalDate fechaActual = LocalDate.now();

		if (fechaInicio == null) {
			return null;
		}
		
		if (fechaActual.isBefore(fechaInicio)) {
			return Estado.P; // Pendiente
		}
		
		// Validación lógica de fechas incorrectas
		if (fechaFin != null && (fechaFin.isBefore(fechaInicio) || fechaFin.isEqual(fechaInicio))) {
			return null;
		}

		

		if ((fechaFin == null && fechaActual.isAfter(fechaInicio))
				|| (fechaFin == null && fechaActual.isEqual(fechaInicio))) {
			return Estado.V; // Vigente sin fecha de fin
		}

		if ((fechaActual.isEqual(fechaInicio) || fechaActual.isAfter(fechaInicio))
				&& (fechaActual.isBefore(fechaFin) || fechaActual.isEqual(fechaFin))) {
			return Estado.V; // Vigente entre fechas
		}

		if (fechaFin != null && fechaActual.isAfter(fechaFin)) {
			return Estado.C; // Caducado
		}

		return null; // Estado indeterminado
	}

	@Override
	public Contrato ObtenerContrato(Empleado emp) {
		if (TieneContrato(emp)) {
			return contratoRepository.ObtenerContrato(emp);
		}
		return null;
	}

	@Override
	public List<Contrato> ListaContratoEmpleadoCaducado(Empleado empleado) {
		return contratoRepository.ListarContratosEmpleadoCaducados(empleado);
	}

	

}