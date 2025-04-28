package com.unu.web.pdf;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class PdfService {

	
	public byte[] generatePdf(String content) throws IOException {
		
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		PdfWriter writer = new PdfWriter(byteArrayOutputStream);
		PdfDocument pdf = new PdfDocument(writer);
		Document document = new Document(pdf);
		document.add(new Paragraph(content));
		document.close();
		return byteArrayOutputStream.toByteArray();
	}
}
