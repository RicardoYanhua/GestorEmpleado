package com.unu.web.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.unu.web.entity.Area;

public interface AreaService {
	
	public abstract Page PaginaArea(Pageable paginacion);
	public abstract List<Area> ListarArea();
	public abstract Area InsertarArea(Area area);
	public abstract void ActualizarArea(Area area);
	public abstract Area ObtenerArea(String idArea);
	
}
