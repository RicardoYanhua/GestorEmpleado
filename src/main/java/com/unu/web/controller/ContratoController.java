package com.unu.web.controller;

import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.unu.web.entity.Contrato;
import com.unu.web.entity.Contrato.Estado;
import com.unu.web.entity.Empleado;
import com.unu.web.service.AreaService;
import com.unu.web.service.ContratoService;
import com.unu.web.service.EmpleadoService;

import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;

@Controller
@RequestMapping("/Contrato")
public class ContratoController {

	@Autowired
	@Qualifier("contratoService")
	private ContratoService contratoService;

	@Autowired
	@Qualifier("empleadoService")
	private EmpleadoService empleadoService;

	@Autowired
	@Qualifier("areaService")
	private AreaService areaService;

	public boolean ActivarBoleta(LocalDate fechaInicio, LocalDate fechaFin) {
	    LocalDate fechaActual = LocalDate.now();

	    if (fechaInicio == null || fechaFin == null) {
	        return false;
	    }

	    if (fechaActual.isBefore(fechaInicio) || fechaActual.isAfter(fechaFin)) {
	        return false;
	    }

	    LocalDate iterador = fechaInicio.plusMonths(1);
	    while (!iterador.isAfter(fechaFin)) {
	        if (fechaActual.isEqual(iterador)) {
	            return true;
	        }
	        iterador = iterador.plusMonths(1);
	    }

	    return false;
	}


	public String MensajeBoleta(LocalDate fechaInicio, LocalDate fechaFin) {
	    LocalDate fechaActual = LocalDate.now();

	    if (ActivarBoleta(fechaInicio, fechaFin)) {
	        return "Realizar pago.";
	    } else {
	       
	        LocalDate siguientePago = fechaInicio.withDayOfMonth(fechaInicio.getDayOfMonth());

	       
	        if (!fechaActual.isBefore(siguientePago)) {
	            siguientePago = siguientePago.plusMonths(1);
	        }

	        if (fechaFin != null && siguientePago.isAfter(fechaFin)) {
	            return "Contrato vencido.";
	        }
	        long diasFaltantes = ChronoUnit.DAYS.between(fechaActual, siguientePago);
	        if (fechaActual.isBefore(fechaInicio)) {
	            return "";
	        } else {
	            return "Faltan " + (diasFaltantes) + " días.";
	        }
	    }
	}


	@GetMapping("/Lista")
	public ModelAndView verDetalles(@RequestParam("CodigoEmpleado") String empleado) {
		ModelAndView modelAndView = new ModelAndView("Contrato/ListaContrato");
		Empleado emp = empleadoService.ObtenerEmpleado(empleado);
		List<Contrato> listaContratos = contratoService.ListaContratoEmpleadoCaducado(emp);

		Contrato cont = contratoService.ObtenerContrato(emp);

		if (cont != null) {
			LocalDate fechaInicio = cont.getContratoFechaInicio();
			LocalDate fechaFin = cont.getContratoFechaFin();

			modelAndView.addObject("Boleta", ActivarBoleta(fechaInicio, fechaFin));
			modelAndView.addObject("MensajeBoleta", MensajeBoleta(fechaInicio, fechaFin));
		}

		modelAndView.addObject("ListaContratos", listaContratos);
		modelAndView.addObject("CodigoEmpleado", empleado);
		modelAndView.addObject("Empleado", emp);
		modelAndView.addObject("Contrato", cont);
		modelAndView.addObject("BotonAgregar", contratoService.TieneContrato(emp));
		return modelAndView;
	}

	@GetMapping("/Nuevo")
	public ModelAndView Nuevo(@RequestParam("Codigo") String empleado) {
		ModelAndView modelAndView = new ModelAndView("Contrato/NuevoContrato");

		Contrato nuevoContrato = new Contrato();
		Empleado emp = empleadoService.ObtenerEmpleado(empleado);
		nuevoContrato.setContratoEmpleadoId(emp);
		modelAndView.addObject("NuevoContrato", nuevoContrato);
		modelAndView.addObject("Areas", areaService.ListarArea());
		return modelAndView;
	}

	@PostMapping("/Nuevo")
	public ModelAndView Insertar(@Valid @ModelAttribute(name = "NuevoContrato") Contrato contrato,
			BindingResult result) {
		ModelAndView modelAndView = new ModelAndView();

		Estado estadoContrato = contratoService.DeterminarEstadoDelContrato(contrato);
		contrato.setContratoEstado(estadoContrato);
		
		System.out.println(contrato.toString());

		if (result.hasErrors()) {
			
			if (estadoContrato == null) {
				modelAndView.addObject("ErrorFechas", "El orden de las fechas seleccionadas no es válido.");
			}
			modelAndView.addObject("Areas", areaService.ListarArea());
			modelAndView.setViewName("Contrato/NuevoContrato");
			return modelAndView;
			
		}
		contratoService.InsertarContrato(contrato);
		modelAndView.setViewName(
				"redirect:/Contrato/Lista?CodigoEmpleado=" + contrato.getContratoEmpleadoId().getEmpCodigo());
		return modelAndView;
	}

	@GetMapping("/Editar/{CodigoContrato}")
	public ModelAndView Obtener(@PathVariable(name = "CodigoContrato") int codigoContrato) {
		ModelAndView modelAndView = new ModelAndView("Contrato/EditarContrato");
		Contrato contrato = contratoService.ObtenerContrato(codigoContrato);
		modelAndView.addObject("EditarContrato", contrato);
		modelAndView.addObject("Areas", areaService.ListarArea());
		return modelAndView;
	}

	@PostMapping("/Editar/{CodigoContrato}")
	public ModelAndView Editar(@PathVariable(name = "CodigoContrato") int codigoContrato,
			@Valid @ModelAttribute(name = "EditarContrato") Contrato contrato, BindingResult result) {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("Areas", areaService.ListarArea());
		Estado estadoContrato = contratoService.DeterminarEstadoDelContrato(contrato);
		contrato.setContratoEstado(estadoContrato);
		if (result.hasErrors()) {
			if (estadoContrato == null) {
				modelAndView.addObject("ErrorFechas", "El orden de las fechas seleccionadas no es válido.");
			}

			modelAndView.setViewName("Contrato/EditarContrato");
			return modelAndView;
		}
		contrato.setContratoId(codigoContrato);
		contratoService.ActualizarContrato(contrato);
		modelAndView.setViewName(
				"redirect:/Contrato/Lista?CodigoEmpleado=" + contrato.getContratoEmpleadoId().getEmpCodigo());
		return modelAndView;
	}

}