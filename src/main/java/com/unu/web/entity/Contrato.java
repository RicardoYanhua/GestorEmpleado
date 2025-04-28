package com.unu.web.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;

@Entity
public class Contrato {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "contratoId")
	private Integer contratoId;

	@ManyToOne
	@JoinColumn(name = "contratoEmpleadoId", nullable = false)
	private Empleado contratoEmpleadoId;
	
	@ManyToOne
	@NotNull(message = "Debe seleccionar el area para el empleado.")
	@JoinColumn(name = "contratoAreaId", nullable = false)
	private Area contratoAreaId;

	public enum Modalidad {PF, IN, OS, FA, SU }
	@NotNull(message = "La modalidad del contrato es obligatoria.")
	@Enumerated(EnumType.STRING)
	@Column(name = "contratoModalidad", nullable = false)
	private Modalidad contratoModalidad;
	
	@Lob
	@NotBlank(message = "Bebe mencionar los detalles del contrato.")
	@Column(name = "contratoDetalle", nullable = false)
	private String contratoDetalle;

	public enum Jornada {TC, TP}
	@NotNull(message = "La jornada del contrato es obligatoria.")
	@Enumerated(EnumType.STRING)
	@Column(name = "contratoJornada", nullable = false)
	private Jornada contratoJornada;

	@NotNull(message = "El salario del empleado es obligatorio.")
	@DecimalMin(value = "1200.00", inclusive = true, message = "El salario debe ser mayor o igual a 1200.00.")
	@Digits(integer = 8, fraction = 2, message = "El salario debe tener hasta 8 dígitos enteros y 2 decimales.")
	@Column(name = "contratoSalario", nullable = false, precision = 10, scale = 2)
	private BigDecimal contratoSalario;

	@NotNull(message = "La fecha de inicio del contrato es obligatoria.")
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	@Column(name = "contratoFechaInicio", nullable = false)
	private LocalDate contratoFechaInicio;

	@Future(message = "La fecha de finalización debe ser una fecha en el futuro.")
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	@Column(name = "contratoFechaFin")
	private LocalDate contratoFechaFin;
	
	public enum Estado {P, V, C}
	@Enumerated(EnumType.STRING)
	@Column(name = "contratoEstado", nullable = false)
	private Estado contratoEstado;
	
	public Contrato() {
		this.contratoId = 0;
		this.contratoEmpleadoId = null;
		this.contratoAreaId = null;
		this.contratoModalidad = null;
		this.contratoDetalle = null;
		this.contratoJornada = null;
		this.contratoSalario = null;
		this.contratoFechaInicio = null;
		this.contratoFechaFin = null;
		this.contratoEstado = null;
	}

	public Contrato(Integer contratoId, Empleado contratoEmpleadoId, Area contratoAreaId,
			Modalidad contratoModalidad, String contratoDetalle, Jornada contratoJornada,
			BigDecimal contratoSalario,
			LocalDate contratoFechaInicio, LocalDate contratoFechaFin, Estado contratoEstado) {
		super();
		this.contratoId = contratoId;
		this.contratoEmpleadoId = contratoEmpleadoId;
		this.contratoAreaId = contratoAreaId;
		this.contratoModalidad = contratoModalidad;
		this.contratoDetalle = contratoDetalle;
		this.contratoJornada = contratoJornada;
		this.contratoSalario = contratoSalario;
		this.contratoFechaInicio = contratoFechaInicio;
		this.contratoFechaFin = contratoFechaFin;
		this.contratoEstado = contratoEstado;
	}

	public Integer getContratoId() {
		return contratoId;
	}

	public void setContratoId(Integer contratoId) {
		this.contratoId = contratoId;
	}

	public Empleado getContratoEmpleadoId() {
		return contratoEmpleadoId;
	}

	public void setContratoEmpleadoId(Empleado contratoEmpleadoId) {
		this.contratoEmpleadoId = contratoEmpleadoId;
	}

	public Area getContratoAreaId() {
		return contratoAreaId;
	}

	public void setContratoAreaId(Area contratoAreaId) {
		this.contratoAreaId = contratoAreaId;
	}

	public Modalidad getContratoModalidad() {
		return contratoModalidad;
	}

	public void setContratoModalidad(Modalidad contratoModalidad) {
		this.contratoModalidad = contratoModalidad;
	}

	public String getContratoDetalle() {
		return contratoDetalle;
	}

	public void setContratoDetalle(String contratoDetalle) {
		this.contratoDetalle = contratoDetalle;
	}

	public Jornada getContratoJornada() {
		return contratoJornada;
	}

	public void setContratoJornada(Jornada contratoJornada) {
		this.contratoJornada = contratoJornada;
	}

	public BigDecimal getContratoSalario() {
		return contratoSalario;
	}

	public void setContratoSalario(BigDecimal contratoSalario) {
		this.contratoSalario = contratoSalario;
	}

	public LocalDate getContratoFechaInicio() {
		return contratoFechaInicio;
	}

	public void setContratoFechaInicio(LocalDate contratoFechaInicio) {
		this.contratoFechaInicio = contratoFechaInicio;
	}

	public LocalDate getContratoFechaFin() {
		return contratoFechaFin;
	}

	public void setContratoFechaFin(LocalDate contratoFechaFin) {
		this.contratoFechaFin = contratoFechaFin;
	}

	public Estado getContratoEstado() {
		return contratoEstado;
	}

	public void setContratoEstado(Estado contratoEstado) {
		this.contratoEstado = contratoEstado;
	}

	@Override
	public String toString() {
	    return String.format(
	        "Contrato {\n" +
	        "  contratoId         : %s\n" +
	        "  contratoEmpleadoId : %s\n" +
	        "  contratoAreaId     : %s\n" +
	        "  contratoModalidad  : %s\n" +
	        "  contratoDetalle    : %s\n" +
	        "  contratoJornada    : %s\n" +
	        "  contratoSalario    : %.2f\n" +
	        "  contratoFechaInicio: %s\n" +
	        "  contratoFechaFin   : %s\n" +
	        "  contratoEstado     : %s\n" +
	        "}",
	        contratoId, 
	        contratoEmpleadoId, 
	        contratoAreaId, 
	        contratoModalidad, 
	        contratoDetalle, 
	        contratoJornada, 
	        contratoSalario, 
	        contratoFechaInicio, 
	        contratoFechaFin, 
	        contratoEstado
	    );
	}


	
	
}