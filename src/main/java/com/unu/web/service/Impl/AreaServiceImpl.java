package com.unu.web.service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.unu.web.entity.Area;
import com.unu.web.entity.Banco;
import com.unu.web.repository.AreaRepository;
import com.unu.web.repository.BancoRepository;
import com.unu.web.service.AreaService;
import com.unu.web.service.BancoService;

@Service("areaService")

public class AreaServiceImpl implements AreaService {

	@Autowired
	@Qualifier("areaRepository")
	private AreaRepository areaRepository;

	@Override
	public Page PaginaArea(Pageable paginacion) {
		Page<Area> lista = areaRepository.findAll(PageRequest.of(paginacion.getPageNumber(), 12));
		return lista;
	}

	@Override
	public List<Area> ListarArea() {
		return areaRepository.findAll();
	}

	@Override
	public Area InsertarArea(Area area) {
		return areaRepository.save(area);
	}

	@Override
	public void ActualizarArea(Area area) {
		areaRepository.save(area);
	}

	@Override
	public Area ObtenerArea(String idArea) {
		return areaRepository.findById(idArea)
	            .orElseThrow(() -> new IllegalArgumentException("No se encontro el Area con ID : " + idArea));
	}

	
	
	
}