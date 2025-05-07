package com.unu.web.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.springframework.format.annotation.DateTimeFormat;
import jakarta.persistence.Column;

@Entity
@Table(name = "contrato")
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

	public enum Modalidad {
		PF, IN, OS, FA, SU
	}

	@NotNull(message = "La modalidad del contrato es obligatoria.")
	@Enumerated(EnumType.STRING)
	@Column(name = "contratoModalidad", nullable = false)
	private Modalidad contratoModalidad;
	@Lob
	@NotBlank(message = "Debe mencionar los detalles del contrato.")
	@Column(name = "contratoDetalle", nullable = false)
	private String contratoDetalle;

	public enum Jornada {
		TC, TP
	}

	@NotNull(message = "La jornada del contrato es obligatoria.")
	@Enumerated(EnumType.STRING)
	@Column(name = "contratoJornada", nullable = false)
	private Jornada contratoJornada;

	@NotNull(message = "La fecha de inicio del contrato es obligatoria.")
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	@Column(name = "contratoFechaInicio", nullable = false)
	private LocalDate contratoFechaInicio;

	@DateTimeFormat(pattern = "dd-MM-yyyy")
	@Column(name = "contratoFechaFin", nullable = true)
	private LocalDate contratoFechaFin;
	
	
	@Transient
	private BigDecimal contratoSalario;
	
	public BigDecimal getContratoSalario() {
		return contratoSalario;
	}
	public void setContratoSalario(BigDecimal contratoSalario) {
		this.contratoSalario = contratoSalario;
	}
	

	public enum Estado {
		P, V, C
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "contratoEstado", nullable = true)
	private Estado contratoEstado;

	public String getEstado() {
		if (contratoEstado == Estado.P) {
			return "Pendiente";
		} else if (contratoEstado == Estado.V) {
			return "Vigente";
		} else if (contratoEstado == Estado.C) {
			return "Caducado";
		} else {
			return "No especificado";
		}
	}

	public String getModalidad() {
		if (contratoModalidad == Modalidad.PF) {
			return "Plazo Fijo";
		} else if (contratoModalidad == Modalidad.IN) {
			return "Indefinido";
		} else if (contratoModalidad == Modalidad.OS) {
			return "Obra o Servicio";
		} else if (contratoModalidad == Modalidad.FA) {
			return "Formación o Aprendizaje";
		} else if (contratoModalidad == Modalidad.SU) {
			return "Suplencia";
		} else {
			return "No especificado";
		}
	}

	public String getJornada() {
		if (contratoJornada == Jornada.TC) {
			return "Tiempo Completo";
		} else if (contratoJornada == Jornada.TP) {
			return "Tiempo Parcial";
		} else {
			return "No especificado";
		}
	}

	public String getFechaFin() {
		return contratoFechaFin != null ? contratoFechaFin.toString() : "Indefinido";
	}

	public Contrato() {
		this.contratoId = 0;
		this.contratoEmpleadoId = null;
		this.contratoAreaId = null;
		this.contratoModalidad = null;
		this.contratoDetalle = null;
		this.contratoJornada = null;
		this.contratoFechaInicio = null;
		this.contratoFechaFin = null;
		this.contratoEstado = null;
	}

	public Contrato(Integer contratoId, Empleado contratoEmpleadoId, Area contratoAreaId, Modalidad contratoModalidad,
			String contratoDetalle, Jornada contratoJornada, BigDecimal contratoSalario, LocalDate contratoFechaInicio,
			LocalDate contratoFechaFin, Estado contratoEstado) {
		super();
		this.contratoId = contratoId;
		this.contratoEmpleadoId = contratoEmpleadoId;
		this.contratoAreaId = contratoAreaId;
		this.contratoModalidad = contratoModalidad;
		this.contratoDetalle = contratoDetalle;
		this.contratoJornada = contratoJornada;
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
	
	public String getFechaFinFormater() {
		if(contratoFechaFin == null) {
			return "";
		}
        LocalDate fecha = LocalDate.parse(contratoFechaFin.toString());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return fecha.format(formatter);
	}
	@Override
	public String toString() {
		return "Contrato [contratoId=" + contratoId + ", contratoEmpleadoId=" + contratoEmpleadoId + ", contratoAreaId="
				+ contratoAreaId + ", contratoModalidad=" + contratoModalidad + ", contratoDetalle=" + contratoDetalle
				+ ", contratoJornada=" + contratoJornada + ", contratoFechaInicio=" + contratoFechaInicio
				+ ", contratoFechaFin=" + contratoFechaFin + ", contratoSalario=" + contratoSalario
				+ ", contratoEstado=" + contratoEstado + "]";
	}
	
	

}