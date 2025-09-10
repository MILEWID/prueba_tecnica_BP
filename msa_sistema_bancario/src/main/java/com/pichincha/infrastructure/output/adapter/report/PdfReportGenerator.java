package com.pichincha.infrastructure.output.adapter.report;

import com.pichincha.domain.Movimiento;
import com.pichincha.application.output.port.ReporteOutputPort;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Base64;

@Component
public class PdfReportGenerator implements ReporteOutputPort {

    @Override
    public String generarReportePdf(List<Movimiento> movimientos) {
        try {
            byte[] pdfBytes = PdfReportBuilder.buildReport(movimientos);
            return Base64.getEncoder().encodeToString(pdfBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error generando PDF: " + e.getMessage(), e);
        }
    }

    public static String generateMovimientosReport(List<Movimiento> movimientos) {
        return new PdfReportGenerator().generarReportePdf(movimientos);
    }
}
