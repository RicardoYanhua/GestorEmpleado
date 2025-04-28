package com.unu.web.service.Impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.unu.web.entity.Banco;
import com.unu.web.repository.BancoRepository;
import com.unu.web.service.BancoService;

@Service("bancoService")

public class BancoServiceImpl implements BancoService {

	@Autowired
	@Qualifier("bancoRepository")
	private BancoRepository bancoRepository;

	@Override
	public Page PaginaBanco(Pageable paginacion) {
		Page<Banco> lista = bancoRepository.findAll(PageRequest.of(paginacion.getPageNumber(), 12));
		return lista;
	}
	
	@Override
	public List<Banco> ListarBanco() {
		return bancoRepository.findAll();
	}

	@Override
	public Banco InsertarBanco(Banco banco) {
		return bancoRepository.save(banco);
	}

	@Override
	public void ActualizarBanco(Banco banco) {
		bancoRepository.save(banco);
	}

	@Override
	public Banco ObtenerBanco(String idBanco) {
		return bancoRepository.findById(idBanco)
	            .orElseThrow(() -> new IllegalArgumentException("No se encontro el Banco con ID : " + idBanco));
	}

	
	
	
}