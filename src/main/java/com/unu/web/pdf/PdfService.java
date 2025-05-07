package com.unu.web.pdf;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;

import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.unu.web.entity.Area;
import com.unu.web.entity.Contrato;
import com.unu.web.entity.Empleado;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.*;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;

@Service
public class PdfService {



	public byte[] generatePdf(String content, Contrato contrato, Empleado empleado, Area area) throws IOException {
	    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	    PdfWriter writer = new PdfWriter(byteArrayOutputStream);
	    PdfDocument pdf = new PdfDocument(writer);
	    Document document = new Document(pdf);
	    
	    LocalDate fechaActual = LocalDate.now();
	    //LocalDate fechaActual = LocalDate.of(2025,07,15);
	    boolean conGratificacion = (fechaActual.getMonthValue() == 7 || fechaActual.getMonthValue() == 12);

	    BigDecimal salario = contrato.getContratoAreaId().getAreaSalarioBase();
	    double gratificacion = conGratificacion ? 500.0 : 0.0;
	    double total = salario.doubleValue() + gratificacion;

	    // Título
	    Paragraph title = new Paragraph("Factura de Pago de Empleado")
	            .setBold()
	            .setFontSize(18)
	            .setTextAlignment(TextAlignment.CENTER);
	    document.add(title);

	    // Datos del empleado y contrato
	    document.add(new Paragraph("\nDatos del Empleado")
	            .setBold()
	            .setFontSize(12));
	    document.add(new Paragraph("Nombre completo: " + empleado.getNombreApellidos()));
	    document.add(new Paragraph("Área: " + area.getAreaNombre()));
	    document.add(new Paragraph("Fecha de emisión: " + fechaActual.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));

	    // Tabla de pagos
	    Table table = new Table(UnitValue.createPercentArray(new float[]{60, 40}))
	            .setWidth(UnitValue.createPercentValue(100))
	            .setMarginTop(15);

	    // Cabeceras
	    table.addHeaderCell(
	        new Cell().add(new Paragraph("Concepto"))
	                  .setBold()
	                  .setBackgroundColor(ColorConstants.LIGHT_GRAY)
	    );
	    table.addHeaderCell(
	        new Cell().add(new Paragraph("Monto (S/.)"))
	                  .setBold()
	                  .setBackgroundColor(ColorConstants.LIGHT_GRAY)
	    );

	    // Sueldo mensual
	    table.addCell(new Cell().add(new Paragraph("Sueldo mensual")));
	    table.addCell(new Cell().add(new Paragraph(String.format("S/. %.2f", salario))));

	    // Gratificación (si aplica)
	    if (conGratificacion) {
	        String mesGratificacion = (fechaActual.getMonthValue() == 7)
	            ? "Gratificación por el mes de julio"
	            : "Gratificación por el mes de diciembre";
	        
	        table.addCell(new Cell().add(new Paragraph(mesGratificacion)));
	        table.addCell(new Cell().add(new Paragraph(String.format("S/. %.2f", gratificacion))));
	    }

	    // Total
	    table.addCell(new Cell().add(new Paragraph("TOTAL")).setBold());
	    table.addCell(new Cell().add(new Paragraph(String.format("S/. %.2f", total))).setBold());

	    document.add(table);

	    // Firma
	    document.add(new Paragraph("\n\nFirma del Empleador: ____________________________")
	            .setTextAlignment(TextAlignment.RIGHT)
	            .setMarginTop(30));

	    document.add(new Paragraph("Firma del Empleado: ____________________________")
	            .setTextAlignment(TextAlignment.RIGHT));

	    // Pie de página
	    document.add(new Paragraph("\nEsta factura es válida como comprobante de pago interno.")
	            .setTextAlignment(TextAlignment.CENTER)
	            .setFontSize(10)
	            .setItalic());

	    // Cierre
	    document.close();
	    return byteArrayOutputStream.toByteArray();
	}


}
