package com.unu.web.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.sql.SQLException;
import javax.sql.rowset.serial.SerialException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
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
import org.springframework.http.MediaType;

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
	
	@GetMapping("/Foto/{CodigoEmpleado}")
	@ResponseBody
	public ResponseEntity<byte[]> obtenerFoto(@PathVariable String CodigoEmpleado) {
	    Empleado emp = empleadoService.ObtenerEmpleado(CodigoEmpleado);
	    if (emp != null && emp.getEmpFoto() != null) {
	        byte[] foto = emp.getEmpFoto();
	        return ResponseEntity.ok()
	                .contentType(MediaType.IMAGE_JPEG) // o cualquier tipo de imagen que tengas
	                .body(foto);
	    }
	    return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/Lista")
	public ModelAndView Lista(@RequestParam(value = "Busqueda", required = false, defaultValue = "") String busqueda,
			@RequestParam(value = "Filtro", required = false, defaultValue = "") String filtro, Pageable paginacion) {
		ModelAndView modelAndView = new ModelAndView("Empleado/ListaEmpleado");
		Page Pagina = empleadoService.ListarEmpleado(paginacion, busqueda, filtro);
		modelAndView.addObject("ListaEmpleados", Pagina.getContent());
		modelAndView.addObject("page", Pagina);
		modelAndView.addObject("Busqueda", busqueda);
		modelAndView.addObject("Filtro", filtro);
		return modelAndView;
	}

	@GetMapping("/Editar/{CodigoEmpleado}")
	public ModelAndView Obtener(@PathVariable(name = "CodigoEmpleado") String codigoEmpleado) {
		ModelAndView modelAndView = new ModelAndView("Empleado/EditarEmpleado");
		Empleado emp = empleadoService.ObtenerEmpleado(codigoEmpleado);
		modelAndView.addObject("EditarEmpleado", emp);
		modelAndView.addObject("Bancos", bancoService.ListarBanco());
		return modelAndView;
	}

	@PostMapping("/Editar/{CodigoEmpleado}")
	public ModelAndView Actualizar(@PathVariable(name = "CodigoEmpleado") String codigoEmpleado,
			@Valid @ModelAttribute(name = "EditarEmpleado") Empleado empleado, BindingResult result) {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("Bancos", bancoService.ListarBanco());

		if (result.hasErrors()) {
			modelAndView.setViewName("Empleado/EditarEmpleado");
			return modelAndView;
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
	public ModelAndView Insertar(
			@RequestParam(name = "Foto") MultipartFile file,
			@Valid @ModelAttribute(name = "NuevoEmpleado") Empleado empleado, 
			BindingResult result)
			throws IOException, SerialException, SQLException {
		ModelAndView modelAndView = new ModelAndView();

		modelAndView.addObject("Bancos", bancoService.ListarBanco());

		
		
		
		boolean DniValidation = true;
		if (empleadoService.ValidarExistDni(empleado.getEmpDni()) && empleado.getEmpDni().length() == 8) {
			modelAndView.addObject("ErrorDni", "El DNI ya está registrado.");
			DniValidation = false;
		}

		if (result.hasErrors() || !DniValidation) {
			modelAndView.setViewName("Empleado/NuevoEmpleado");
			return modelAndView;
			
		}
		
		byte[] bytes =  file.getBytes();
		empleado.setEmpFoto(bytes);
		
		empleadoService.InsertarEmpleado(empleado);
		modelAndView.setViewName("redirect:/Empleado/Lista");
		return modelAndView;
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