package com.unu.web.pdf;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.element.LineSeparator;
@Service
public class PdfService {

	
public byte[] generatePdf(String content) throws IOException {
        
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        
        // Crear el escritor PDF
        PdfWriter writer = new PdfWriter(byteArrayOutputStream);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        // Agregar título
        Paragraph title = new Paragraph("Contrato de Pago Mensual y Gratificación")
                .setBold()
                .setFontSize(18)
                .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER);
        document.add(title);
        
        // Agregar una línea separadora (usando LineSeparator correctamente)
        document.add(new LineSeparator(null));

        // Agregar contenido principal
        Paragraph contractDetails = new Paragraph()
                .add(new Text("Por la presente, se detalla el acuerdo de pago correspondiente al contrato mensual:").setBold())
                .add("\n\n")
                .add("Monto mensual: S/. 2000.00")
                .add("\n")
                .add("Gratificación: S/. 1000.00 (Pagadero en julio y diciembre)")
                .add("\n\n")
                .add("El pago se efectuará a más tardar el día 5 de cada mes. Los pagos deben ser realizados mediante transferencia bancaria a la cuenta de la empresa.")
                .add("\n")
                .add("Este contrato entra en vigencia el 1 de mayo de 2025 y tiene una duración indefinida.");

        document.add(contractDetails);

        // Agregar otra línea separadora
        document.add(new LineSeparator(null));

        // Agregar firma
        Paragraph footer = new Paragraph("Firma del Contratante:")
                .setItalic()
                .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.RIGHT);
        document.add(footer);

        // Cerrar el documento
        document.close();
        
        // Retornar el PDF generado en un array de bytes
        return byteArrayOutputStream.toByteArray();
    }
}
