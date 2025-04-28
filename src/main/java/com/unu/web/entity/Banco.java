package com.unu.web.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "banco")
public class Banco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bancoId")
    private Integer bancoId;

    @NotBlank(message = "El nombre del banco no puede estar vacío.")
    @Size(max = 50, message = "El nombre del banco no puede exceder los 50 caracteres.")
    @Column(name = "bancoNombre", length = 50, nullable = false)
    private String bancoNombre;
    
	public Banco() {
		this.bancoId = 0;
		this.bancoNombre = null;
	}

	public Banco(Integer bancoId, String bancoNombre) {
		super();
		this.bancoId = bancoId;
		this.bancoNombre = bancoNombre;
	}

	public Integer getBancoId() {
		return bancoId;
	}

	public void setBancoId(Integer bancoId) {
		this.bancoId = bancoId;
	}

	public String getBancoNombre() {
		return bancoNombre;
	}

	public void setBancoNombre(String bancoNombre) {
		this.bancoNombre = bancoNombre;
	}
    
}