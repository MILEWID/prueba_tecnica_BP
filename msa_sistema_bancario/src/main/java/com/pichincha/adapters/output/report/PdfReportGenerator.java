package com.pichincha.adapters.output.report;

import com.pichincha.domain.Movimiento;
import com.pichincha.ports.output.ReporteGeneratorPort;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.kernel.pdf.PdfDocument;
import org.springframework.stereotype.Component;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Base64;

@Component
public class PdfReportGenerator implements ReporteGeneratorPort {
    
    @Override
    public String generarReportePdf(List<Movimiento> movimientos) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);
            document.add(new Paragraph("Reporte de Movimientos"));
            for (Movimiento mov : movimientos) {
                document.add(new Paragraph(mov.getFecha() + " - " + mov.getTipoMovimiento() + " - " + mov.getValor() + " - " + mov.getSaldo()));
            }
            document.close();
            return Base64.getEncoder().encodeToString(baos.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException("Error generando PDF", e);
        }
    }
    
    public static String generateMovimientosReport(List<Movimiento> movimientos) {
        return new PdfReportGenerator().generarReportePdf(movimientos);
    }
}
