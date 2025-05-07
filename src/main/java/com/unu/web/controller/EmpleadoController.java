package com.unu.web.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import com.unu.web.entity.Empleado;
import com.unu.web.service.BancoService;
import com.unu.web.service.EmpleadoService;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import java.net.MalformedURLException;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@Controller
@RequestMapping("/Empleado")
public class EmpleadoController {

	@Autowired
	@Qualifier("empleadoService")
	private EmpleadoService empleadoService;

	@Autowired
	@Qualifier("bancoService")
	private BancoService bancoService;

	@GetMapping("/")
	public String Main() {
		return "redirect:/Empleado/Lista";
	}

	@GetMapping("")
	public String Main2() {
		return "redirect:/Empleado/Lista";
	}

	@GetMapping("/FotoEmpleado/{nombre}")
	@ResponseBody
	public ResponseEntity<Resource> verFoto(@PathVariable String nombre) throws MalformedURLException {
		Path rutaArchivo = Paths.get("C:", "uploads", "FotoEmpleados").resolve(nombre).toAbsolutePath();
		Resource recurso = new UrlResource(rutaArchivo.toUri());

		if (!recurso.exists() || !recurso.isReadable()) {
			throw new RuntimeException("No se puede cargar la imagen: " + nombre);
		}
		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(recurso);
	}

	@GetMapping("/Lista")
	public ModelAndView Lista(@RequestParam(value = "Busqueda", required = false, defaultValue = "") String busqueda,
			@RequestParam(value = "Filtro", required = false, defaultValue = "Todo") String filtro,
			Pageable paginacion) {
		ModelAndView modelAndView = new ModelAndView("Empleado/ListaEmpleado");
		Page Pagina = empleadoService.ListarEmpleado(paginacion, busqueda, filtro);
		modelAndView.addObject("Empleados", Pagina.getContent());
		modelAndView.addObject("Pagina", Pagina);
		modelAndView.addObject("Busqueda", busqueda);
		modelAndView.addObject("Filtro", filtro);
		return modelAndView;
	}

	@GetMapping("/Editar/{CodigoEmpleado}")
	public ModelAndView Obtener(@PathVariable(name = "CodigoEmpleado") String codigoEmpleado) {
		ModelAndView modelAndView = new ModelAndView("Empleado/EditarEmpleado");
		Empleado emp = empleadoService.ObtenerEmpleado(codigoEmpleado);
		modelAndView.addObject("Empleado", emp);
		modelAndView.addObject("Bancos", bancoService.ListarBanco());
		return modelAndView;
	}

	@PostMapping("/Editar/{CodigoEmpleado}")
	public ModelAndView Actualizar(@RequestParam(name = "empFotoFile") MultipartFile file,
			@PathVariable(name = "CodigoEmpleado") String codigoEmpleado,
			@Valid @ModelAttribute(name = "Empleado") Empleado empleado, BindingResult result) throws IOException {

		ModelAndView modelAndView = new ModelAndView();
		
		if (!file.isEmpty()) {
			empleado.setEmpFotoByte(file.getBytes());
			empleado.setEmpFoto(file.getOriginalFilename());
		}
		
		if (result.hasErrors()) {

			modelAndView.addObject("Bancos", bancoService.ListarBanco());
			modelAndView.setViewName("Empleado/EditarEmpleado");
			return modelAndView;
		}

		if (!file.isEmpty()) {
			GuardarFoto(empleado);
		}
		
		empleadoService.ActualizarEmpleado(empleado);
		modelAndView.setViewName("redirect:/Empleado/Lista");
		return modelAndView;
	}

	@GetMapping("/Nuevo")
	public ModelAndView Nuevo() {
		ModelAndView modelAndView = new ModelAndView("Empleado/NuevoEmpleado");
		Empleado nuevoEmpleado = new Empleado();
		nuevoEmpleado.setEmpCodigo(GenerarNuevoCodigoEmpleado());
		modelAndView.addObject("NuevoEmpleado", nuevoEmpleado);
		modelAndView.addObject("Bancos", bancoService.ListarBanco());
		return modelAndView;
	}

	@PostMapping("/Nuevo")
	public ModelAndView Insertar(@RequestParam(name = "empFotoFile") MultipartFile file,
			@Valid @ModelAttribute(name = "NuevoEmpleado") Empleado empleado, BindingResult result) throws IOException {

		ModelAndView modelAndView = new ModelAndView();
		boolean DniValidation = true;
		if (empleadoService.ValidarExistDni(empleado.getEmpDni())) {
			modelAndView.addObject("ErrorDni", "El DNI ya está registrado.");
			DniValidation = false;
		}

		if (!file.isEmpty()) {
			empleado.setEmpFotoByte(file.getBytes());
			empleado.setEmpFoto(file.getOriginalFilename());
		}
		boolean FotoValidation = true;

		if (empleado.getEmpFoto() == null || empleado.getEmpFoto().equals("")) {
			modelAndView.addObject("ErrorFoto", "Debe seleccionar una Foto para el empleado.");
			FotoValidation = false;
		} else {
			modelAndView.addObject("FotoSuccess", true);
		}
		
		if (result.hasErrors() || !DniValidation || !FotoValidation) {
			modelAndView.addObject("Bancos", bancoService.ListarBanco());
			modelAndView.setViewName("Empleado/NuevoEmpleado");
			return modelAndView;
		}
		
		GuardarFoto(empleado);
		empleadoService.InsertarEmpleado(empleado);

		modelAndView.setViewName("redirect:/Empleado/Lista");
		return modelAndView;
	}
	
	public void GuardarFoto(Empleado emp) throws IOException {
		Path directorioImagenes = Paths.get("C:", "uploads", "FotoEmpleados");
		if (!Files.exists(directorioImagenes)) {
			Files.createDirectories(directorioImagenes);
		}

		Path rutaCompleta = directorioImagenes.resolve(emp.getEmpFoto());
		Files.write(rutaCompleta, emp.getEmpFotoByte());
	}

	public String GenerarNuevoCodigoEmpleado() {
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		SecureRandom random = new SecureRandom();
		StringBuilder id = new StringBuilder(8);
		do {
			id.setLength(0);
			for (int i = 0; i < 8; i++) {
				int randomIndex = random.nextInt(characters.length());
				id.append(characters.charAt(randomIndex));
			}
		} while (empleadoService.CodigoEmpleadoExiste(id.toString()));

		return id.toString();
	}

}