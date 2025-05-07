package com.unu.web.pdf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.unu.web.entity.Area;
import com.unu.web.entity.Contrato;
import com.unu.web.entity.Empleado;
import com.unu.web.service.AreaService;
import com.unu.web.service.ContratoService;
import com.unu.web.service.EmpleadoService;

import java.io.IOException;

@RestController
public class PdfController {

    private final PdfService pdfService;
    
    @Autowired
   	@Qualifier("empleadoService")
   	private EmpleadoService empleadoService;
       
    @Autowired
   	@Qualifier("contratoService")
   	private ContratoService contratoService;
    
    @Autowired
   	@Qualifier("areaService")
   	private AreaService areaService;

    public PdfController(PdfService pdfService) {
        this.pdfService = pdfService;
    }

    @GetMapping("/GenerarBoleta")
    public ResponseEntity<byte[]> downloadPdf(
            @RequestParam Integer CodigoContrato,  
            @RequestParam String content) throws IOException {

        
        Contrato contrato = contratoService.ObtenerContrato(CodigoContrato);
        Empleado emp = empleadoService.ObtenerEmpleado(contrato.getContratoEmpleadoId().getEmpCodigo());
        Area area = areaService.ObtenerArea(contrato.getContratoAreaId().getAreaId());
        
        if (contrato == null) {
            return ResponseEntity.notFound().build();
        }

       
        byte[] pdfBytes = pdfService.generatePdf(content,contrato,emp,area);

       
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=boleta_contrato_" + CodigoContrato + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }
}
