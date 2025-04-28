package com.unu.web.entity;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "area")
public class Area {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "areaId")
    private Integer areaId;

    @NotBlank(message = "El nombre del área no puede estar vacío.")
    @Size(max = 50, message = "El nombre del área no puede exceder los 50 caracteres.")
    @Column(name = "areaNombre", length = 50, nullable = false)
    private String areaNombre;

    @NotNull(message = "El salario base no puede ser nulo.")
    @DecimalMin(value = "1200.00", inclusive = true, message = "El salario base no puede ser menor a 1200.")
    @DecimalMax(value = "999999.99", inclusive = true, message = "El salario base no puede ser mayor a 999999.99.")
    @Column(name = "areaSalarioBase", precision = 8, scale = 2, nullable = false)
    private BigDecimal areaSalarioBase;
    
    @OneToMany(mappedBy = "contratoAreaId")
    private List<Contrato> areaContratoId;

    public Area() {
		
		this.areaId = 0;
		this.areaNombre = null;
		this.areaSalarioBase = null;
	}
    
	public Area(Integer areaId, String areaNombre, BigDecimal areaSalarioBase) {
		super();
		this.areaId = areaId;
		this.areaNombre = areaNombre;
		this.areaSalarioBase = areaSalarioBase;
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public String getAreaNombre() {
		return areaNombre;
	}

	public void setAreaNombre(String areaNombre) {
		this.areaNombre = areaNombre;
	}

	public BigDecimal getAreaSalarioBase() {
		return areaSalarioBase;
	}

	public void setAreaSalarioBase(BigDecimal areaSalarioBase) {
		this.areaSalarioBase = areaSalarioBase;
	}

	public List<Contrato> getAreaContratoId() {
		return areaContratoId;
	}

	public void setAreaContratoId(List<Contrato> areaContratoId) {
		this.areaContratoId = areaContratoId;
	}

}
