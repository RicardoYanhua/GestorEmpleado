package com.unu.web.entity;

import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "empleado")
public class Empleado {

	@Id
	@NotBlank(message = "El código del empleado debe estar presente y no puede estar vacío.")
	@Size(min = 8, max = 8, message = "El código del empleado debe tener exactamente 8 caracteres.")
	@Column(name = "empCodigo", length = 8, nullable = false)
	private String empCodigo;

	@NotBlank(message = "El DNI del empleado es obligatorio y no puede estar vacío.")
	@Pattern(regexp = "\\d{8}", message = "El DNI debe consistir en exactamente 8 dígitos numéricos.")
	@Column(name = "empDni", length = 8, nullable = false, unique = true)
	private String empDni;

	@NotBlank(message = "El nombre del empleado no puede estar vacío.")
	@Size(max = 100, message = "El nombre del empleado no puede superar los 100 caracteres.")
	@Column(name = "empNombres", length = 100, nullable = false)
	private String empNombres;

	@NotBlank(message = "El apellido paterno del empleado es obligatorio y no puede estar vacío.")
	@Size(max = 50, message = "El apellido paterno no puede superar los 50 caracteres.")
	@Column(name = "empApellidoPaterno", length = 50, nullable = false)
	private String empApellidoPaterno;

	@NotBlank(message = "El apellido materno del empleado es obligatorio y no puede estar vacío.")
	@Size(max = 50, message = "El apellido materno no puede superar los 50 caracteres.")
	@Column(name = "empApellidoMaterno", length = 50, nullable = false)
	private String empApellidoMaterno;

	public enum Genero {
		M, F
	}

	@Enumerated(EnumType.STRING)
	@NotNull(message = "Debe seleccionar el género del empleado.")
	@Column(name = "empGenero", nullable = false)
	private Genero empGenero;

	public enum EstadoCivil {
		S, C, D, V
	}

	@Enumerated(EnumType.STRING)
	@NotNull(message = "Debe seleccionar el estado civil del empleado.")
	@Column(name = "empEstadoCivil", nullable = false)
	private EstadoCivil empEstadoCivil;

	@NotNull(message = "La fecha de nacimiento del empleado es obligatoria.")
	@Past(message = "La fecha de nacimiento debe ser una fecha en el pasado.")
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	@Column(name = "empFechaNacimiento", nullable = false)
	private LocalDate empFechaNacimiento;

	@NotNull(message = "Debe Seleccionar una Foto para el empleado")
	@Column(name = "empFoto", nullable = true)
	private String empFoto;

	@Lob
	@Transient
	private byte[] empFotoByte;

	public byte[] getEmpFotoByte() {
		return empFotoByte;
	}

	public void setEmpFotoByte(byte[] empFotoByte) {
		this.empFotoByte = empFotoByte;
	}

	@ManyToOne
	@JoinColumn(name = "empBancoId", nullable = false)
	@NotNull(message = "Debe seleccionar un banco.")
	private Banco empBancoId;

	@OneToMany(mappedBy = "contratoEmpleadoId", cascade = CascadeType.ALL)
	private List<Contrato> empContratoId;

	public enum Estado {
		A, I
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "empEstado", nullable = false)
	private Estado empEstado;

	@Column(name = "empFechaRegistro")
	private LocalDate empFechaRegistro;

	public String getEstado() {
		boolean contratoVigente = empContratoId.stream()
				.anyMatch(contrato -> contrato.getContratoEstado() == Contrato.Estado.V);

		if (contratoVigente) {
			this.empEstado = Estado.A;
			return "Activo";
		} else {
			this.empEstado = Estado.I;
			return "Inactivo";
		}
	}
	
	public String getGenero() {
	    if (empGenero == Genero.M) {
	        return "Masculino";
	    } else if (empGenero == Genero.F) {
	        return "Femenino";
	    } else {
	        return "No especificado";
	    }
	}
	
	public String getEstadoCivil() {
	    if (empEstadoCivil == EstadoCivil.S) {
	        return "Soltero";
	    } else if (empEstadoCivil == EstadoCivil.C) {
	        return "Casado";
	    }  else if (empEstadoCivil == EstadoCivil.D) {
	        return "Divorciado";
	    }   else if (empEstadoCivil == EstadoCivil.V) {
	        return "Viudo";
	    }else {
	        return "No especificado";
	    }
	}

	public String calcularAntiguedad() {

		if (empFechaRegistro == null) {
			return "Fecha no registrada";
		}

		LocalDate fechaActual = LocalDate.now();
		if (empFechaRegistro.isAfter(fechaActual)) {
			return "Fecha de ingreso inválida";
		}

		Period periodo = Period.between(empFechaRegistro, fechaActual);
		int años = periodo.getYears();
		int dias = periodo.getDays() + (periodo.getMonths() * 30);

		if (años == 0 && dias == 0) {
			return "Recién registrado";
		}

		StringBuilder resultado = new StringBuilder();

		if (años > 0) {
			resultado.append(años).append(" años");
		}

		if (dias > 0) {
			if (resultado.length() > 0) {
				resultado.append(" y ");
			}
			resultado.append(dias).append(" días");
		}

		return resultado.toString();
	}

	public int getEdad() {

		if (empFechaNacimiento == null) {
			throw new IllegalArgumentException("La fecha de nacimiento no puede ser nula.");
		}

		LocalDate fechaActual = LocalDate.now();
		if (empFechaNacimiento.isAfter(fechaActual)) {
			throw new IllegalArgumentException("La fecha de nacimiento no puede ser futura.");
		}

		return Period.between(empFechaNacimiento, fechaActual).getYears();
	}

	// Constructores

	public Empleado() {
		this.empCodigo = null;
		this.empDni = null;
		this.empNombres = null;
		this.empApellidoPaterno = null;
		this.empApellidoMaterno = null;
		this.empFechaNacimiento = null;
		this.empFoto = null;
		this.empGenero = null;
		this.empEstadoCivil = null;
		this.empEstado = Estado.I;
		this.empFechaRegistro = LocalDate.now();
	}

	public Empleado(String empCodigo, String empDni, String empNombres, String empApellidoPaterno,
			String empApellidoMaterno, Genero empGenero, EstadoCivil empEstadoCivil, LocalDate empFechaNacimiento,
			String empFoto, Banco empBancoId, Estado empEstado, LocalDate empFechaRegistro) {
		super();
		this.empCodigo = empCodigo;
		this.empDni = empDni;
		this.empNombres = empNombres;
		this.empApellidoPaterno = empApellidoPaterno;
		this.empApellidoMaterno = empApellidoMaterno;
		this.empGenero = empGenero;
		this.empEstadoCivil = empEstadoCivil;
		this.empFechaNacimiento = empFechaNacimiento;
		this.empFoto = empFoto;
		this.empBancoId = empBancoId;
		this.empEstado = empEstado;
		this.empFechaRegistro = empFechaRegistro;
	}

	public String getEmpCodigo() {
		return empCodigo;
	}

	public void setEmpCodigo(String empCodigo) {
		this.empCodigo = empCodigo;
	}

	public String getEmpDni() {
		return empDni;
	}

	public void setEmpDni(String empDni) {
		this.empDni = empDni;
	}

	public String getEmpNombres() {
		return empNombres;
	}

	public void setEmpNombres(String empNombres) {
		this.empNombres = empNombres;
	}

	public String getEmpApellidoPaterno() {
		return empApellidoPaterno;
	}

	public void setEmpApellidoPaterno(String empApellidoPaterno) {
		this.empApellidoPaterno = empApellidoPaterno;
	}

	public String getEmpApellidoMaterno() {
		return empApellidoMaterno;
	}

	public void setEmpApellidoMaterno(String empApellidoMaterno) {
		this.empApellidoMaterno = empApellidoMaterno;
	}
	
	public String getNombreApellidos() {
	    return capitalize(this.empNombres) + " " + capitalize(this.empApellidoPaterno) + " " + capitalize(this.empApellidoMaterno);
	}

	private String capitalize(String palabra) {
	    if (palabra == null || palabra.isEmpty()) {
	        return palabra;
	    }
	    return palabra.substring(0, 1).toUpperCase() + palabra.substring(1).toLowerCase();
	}

	public Genero getEmpGenero() {
		return empGenero;
	}

	public void setEmpGenero(Genero empGenero) {
		this.empGenero = empGenero;
	}
	
	

	public EstadoCivil getEmpEstadoCivil() {
		return empEstadoCivil;
	}

	public void setEmpEstadoCivil(EstadoCivil empEstadoCivil) {
		this.empEstadoCivil = empEstadoCivil;
	}

	public LocalDate getEmpFechaNacimiento() {
		return empFechaNacimiento;
	}

	public void setEmpFechaNacimiento(LocalDate empFechaNacimiento) {
		this.empFechaNacimiento = empFechaNacimiento;
	}

	public String getEmpFoto() {
		return empFoto;
	}

	public void setEmpFoto(String empFoto) {
		this.empFoto = empFoto;
	}

	public Banco getEmpBancoId() {
		return empBancoId;
	}

	public void setEmpBancoId(Banco empBancoId) {
		this.empBancoId = empBancoId;
	}

	public List<Contrato> getEmpContratoId() {
		return empContratoId;
	}

	public void setEmpContratoId(List<Contrato> empContratoId) {
		this.empContratoId = empContratoId;
	}

	public LocalDate getEmpFechaRegistro() {
		return empFechaRegistro;
	}

	public void setEmpFechaRegistro(LocalDate empFechaRegistro) {
		this.empFechaRegistro = empFechaRegistro;
	}

	@Override
	public String toString() {
		return String.format("________________________________________________________\n" + "Empleado {\n"
				+ "  empCodigo           : %s\n" + "  empDni              : %s\n" + "  empNombres          : %s\n"
				+ "  empApellidoPaterno  : %s\n" + "  empApellidoMaterno  : %s\n" + "  empGenero           : %s\n"
				+ "  empEstadoCivil      : %s\n" + "  empFechaNacimiento  : %s\n" + "  empFoto             : %s\n"
				+ "  empBancoId          : %s\n" + "  empEstado           : %s\n" + "  empFechaRegistro    : %s\n"
				+ "} \n" + "________________________________________________________\n", empCodigo, empDni, empNombres,
				empApellidoPaterno, empApellidoMaterno, empGenero, empEstadoCivil, empFechaNacimiento, empFoto,
				empBancoId, empEstado, empFechaRegistro);
	}

}
