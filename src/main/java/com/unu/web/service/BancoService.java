package com.unu.web.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.unu.web.entity.Banco;

public interface BancoService {
	
	public abstract Page PaginaBanco(Pageable paginacion);
	public abstract List<Banco> ListarBanco();
	public abstract Banco InsertarBanco(Banco banco);
	public abstract void ActualizarBanco(Banco banco);
	public abstract Banco ObtenerBanco(String idBanco);
	
}
