package com.pichincha.adapters.output.report;

import com.pichincha.domain.Movimiento;
import com.pichincha.ports.output.ReporteGeneratorPort;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.font.PdfFont;
import org.springframework.stereotype.Component;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Base64;
import java.time.format.DateTimeFormatter;
import java.text.DecimalFormat;

@Component
public class PdfReportGenerator implements ReporteGeneratorPort {
    
    private static final Color HEADER_COLOR = new DeviceRgb(52, 73, 94);
    private static final Color ACCENT_COLOR = new DeviceRgb(52, 152, 219);
    private static final Color SUCCESS_COLOR = new DeviceRgb(39, 174, 96);
    private static final Color WARNING_COLOR = new DeviceRgb(230, 126, 34);
    private static final DecimalFormat CURRENCY_FORMAT = new DecimalFormat("#,##0.00");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    
    @Override
    public String generarReportePdf(List<Movimiento> movimientos) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);
            
            // Configurar fuentes
            PdfFont boldFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
            PdfFont normalFont = PdfFontFactory.createFont(StandardFonts.HELVETICA);
            
            // Agregar contenido del reporte
            addHeader(document, boldFont, normalFont);
            addClientInfo(document, boldFont, normalFont, movimientos);
            addSummarySection(document, boldFont, normalFont, movimientos);
            addMovementsTable(document, boldFont, normalFont, movimientos);
            addFooter(document, normalFont);
            
            document.close();
            return Base64.getEncoder().encodeToString(baos.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException("Error generando PDF: " + e.getMessage(), e);
        }
    }
    
    private void addHeader(Document document, PdfFont boldFont, PdfFont normalFont) {
        // Título principal
        Paragraph title = new Paragraph("ESTADO DE CUENTA BANCARIO")
                .setFont(boldFont)
                .setFontSize(18)
                .setFontColor(HEADER_COLOR)
                .setMarginBottom(5);
        document.add(title);
        
        // Subtítulo
        Paragraph subtitle = new Paragraph("Banco Pichincha - Sistema de Gestión")
                .setFont(normalFont)
                .setFontSize(12)
                .setFontColor(ColorConstants.DARK_GRAY)
                .setMarginBottom(20);
        document.add(subtitle);
    }
    
    private void addClientInfo(Document document, PdfFont boldFont, PdfFont normalFont, List<Movimiento> movimientos) {
        if (movimientos.isEmpty()) return;
        
        Movimiento firstMovement = movimientos.get(0);
        String clienteName = firstMovement.getCuenta().getCliente().getNombre();
        String clienteId = firstMovement.getCuenta().getCliente().getIdentificacion();
        String accountNumber = firstMovement.getCuenta().getNumeroCuenta();
        
        // Información del cliente
        document.add(new Paragraph("INFORMACIÓN DEL CLIENTE")
                .setFont(boldFont)
                .setFontSize(14)
                .setFontColor(ACCENT_COLOR)
                .setMarginTop(15)
                .setMarginBottom(10));
        
        document.add(new Paragraph("Cliente: " + clienteName).setFont(normalFont));
        document.add(new Paragraph("Identificación: " + clienteId).setFont(normalFont));
        document.add(new Paragraph("Número de Cuenta: " + accountNumber).setFont(normalFont));
        document.add(new Paragraph("Fecha de Generación: " + 
                java.time.LocalDateTime.now().format(DATE_FORMATTER)).setFont(normalFont));
        
        document.add(new Paragraph("\n"));
    }
    
    private void addSummarySection(Document document, PdfFont boldFont, PdfFont normalFont, List<Movimiento> movimientos) {
        // Calcular resumen
        double totalIngresos = 0;
        double totalEgresos = 0;
        double saldoFinal = 0;
        
        for (Movimiento mov : movimientos) {
            if (mov.getValor() > 0) {
                totalIngresos += mov.getValor();
            } else {
                totalEgresos += Math.abs(mov.getValor());
            }
            saldoFinal = mov.getSaldo(); // El último saldo
        }
        
        // Título de resumen
        document.add(new Paragraph("RESUMEN EJECUTIVO")
                .setFont(boldFont)
                .setFontSize(14)
                .setFontColor(ACCENT_COLOR)
                .setMarginBottom(10));
        
        // Tabla de resumen usando layout simple
        Table summaryTable = new Table(new float[]{2, 2, 2});
        summaryTable.setWidth(400);
        
        // Encabezados
        summaryTable.addHeaderCell(new Cell().add(new Paragraph("TOTAL INGRESOS")
                .setFont(boldFont).setFontColor(ColorConstants.WHITE))
                .setBackgroundColor(SUCCESS_COLOR));
        summaryTable.addHeaderCell(new Cell().add(new Paragraph("TOTAL EGRESOS")
                .setFont(boldFont).setFontColor(ColorConstants.WHITE))
                .setBackgroundColor(WARNING_COLOR));
        summaryTable.addHeaderCell(new Cell().add(new Paragraph("SALDO FINAL")
                .setFont(boldFont).setFontColor(ColorConstants.WHITE))
                .setBackgroundColor(HEADER_COLOR));
        
        // Valores
        summaryTable.addCell(new Cell().add(new Paragraph("$" + CURRENCY_FORMAT.format(totalIngresos))
                .setFont(normalFont).setFontColor(SUCCESS_COLOR).setFontSize(12)));
        summaryTable.addCell(new Cell().add(new Paragraph("$" + CURRENCY_FORMAT.format(totalEgresos))
                .setFont(normalFont).setFontColor(WARNING_COLOR).setFontSize(12)));
        summaryTable.addCell(new Cell().add(new Paragraph("$" + CURRENCY_FORMAT.format(saldoFinal))
                .setFont(normalFont).setFontColor(HEADER_COLOR).setFontSize(12)));
        
        document.add(summaryTable);
        document.add(new Paragraph("\n"));
    }
    
    private void addMovementsTable(Document document, PdfFont boldFont, PdfFont normalFont, List<Movimiento> movimientos) {
        // Título de movimientos
        document.add(new Paragraph("DETALLE DE MOVIMIENTOS")
                .setFont(boldFont)
                .setFontSize(14)
                .setFontColor(ACCENT_COLOR)
                .setMarginBottom(10));
        
        if (movimientos.isEmpty()) {
            document.add(new Paragraph("No hay movimientos registrados para el período seleccionado.")
                    .setFont(normalFont)
                    .setFontColor(ColorConstants.GRAY)
                    .setMarginBottom(20));
            return;
        }
        
        // Tabla de movimientos
        Table movementsTable = new Table(new float[]{3, 2, 2, 2, 2, 2});
        movementsTable.setWidth(550);
        
        // Encabezados de la tabla
        movementsTable.addHeaderCell(new Cell().add(new Paragraph("FECHA")
                .setFont(boldFont).setFontColor(ColorConstants.WHITE))
                .setBackgroundColor(HEADER_COLOR));
        movementsTable.addHeaderCell(new Cell().add(new Paragraph("CUENTA")
                .setFont(boldFont).setFontColor(ColorConstants.WHITE))
                .setBackgroundColor(HEADER_COLOR));
        movementsTable.addHeaderCell(new Cell().add(new Paragraph("TIPO")
                .setFont(boldFont).setFontColor(ColorConstants.WHITE))
                .setBackgroundColor(HEADER_COLOR));
        movementsTable.addHeaderCell(new Cell().add(new Paragraph("SALDO INICIAL")
                .setFont(boldFont).setFontColor(ColorConstants.WHITE))
                .setBackgroundColor(HEADER_COLOR));
        movementsTable.addHeaderCell(new Cell().add(new Paragraph("MOVIMIENTO")
                .setFont(boldFont).setFontColor(ColorConstants.WHITE))
                .setBackgroundColor(HEADER_COLOR));
        movementsTable.addHeaderCell(new Cell().add(new Paragraph("SALDO FINAL")
                .setFont(boldFont).setFontColor(ColorConstants.WHITE))
                .setBackgroundColor(HEADER_COLOR));
        
        // Agregar filas de movimientos
        for (int i = 0; i < movimientos.size(); i++) {
            Movimiento mov = movimientos.get(i);
            Color rowColor = i % 2 == 0 ? ColorConstants.WHITE : new DeviceRgb(248, 249, 250);
            
            movementsTable.addCell(new Cell().add(new Paragraph(mov.getFecha().format(DATE_FORMATTER))
                    .setFont(normalFont).setFontSize(8)).setBackgroundColor(rowColor));
            movementsTable.addCell(new Cell().add(new Paragraph(mov.getCuenta().getNumeroCuenta())
                    .setFont(normalFont).setFontSize(8)).setBackgroundColor(rowColor));
            movementsTable.addCell(new Cell().add(new Paragraph(mov.getTipoMovimiento())
                    .setFont(normalFont).setFontSize(8)).setBackgroundColor(rowColor));
            movementsTable.addCell(new Cell().add(new Paragraph("$" + CURRENCY_FORMAT.format(mov.getSaldoInicial()))
                    .setFont(normalFont).setFontSize(8)).setBackgroundColor(rowColor));
            
            // Celda de movimiento con color según el tipo
            Color movementColor = mov.getValor() >= 0 ? SUCCESS_COLOR : WARNING_COLOR;
            String movementText = (mov.getValor() >= 0 ? "+" : "") + "$" + CURRENCY_FORMAT.format(mov.getValor());
            movementsTable.addCell(new Cell().add(new Paragraph(movementText)
                    .setFont(normalFont).setFontColor(movementColor).setFontSize(8))
                    .setBackgroundColor(rowColor));
            
            movementsTable.addCell(new Cell().add(new Paragraph("$" + CURRENCY_FORMAT.format(mov.getSaldo()))
                    .setFont(normalFont).setFontSize(8)).setBackgroundColor(rowColor));
        }
        
        document.add(movementsTable);
        
        // Información adicional
        document.add(new Paragraph("\nTotal de movimientos encontrados: " + movimientos.size())
                .setFont(normalFont)
                .setFontSize(10)
                .setFontColor(ColorConstants.GRAY));
    }
    
    private void addFooter(Document document, PdfFont normalFont) {
        // Pie de página
        document.add(new Paragraph("Este documento fue generado automáticamente por el Sistema de Gestión Bancaria")
                .setFont(normalFont)
                .setFontSize(8)
                .setFontColor(ColorConstants.GRAY)
                .setMarginTop(20));
        
        document.add(new Paragraph("Generado el: " + java.time.LocalDateTime.now().format(DATE_FORMATTER))
                .setFont(normalFont)
                .setFontSize(8)
                .setFontColor(ColorConstants.GRAY));
        
        document.add(new Paragraph("© 2025 Banco Pichincha - Todos los derechos reservados")
                .setFont(normalFont)
                .setFontSize(8)
                .setFontColor(ColorConstants.GRAY));
    }
    
    public static String generateMovimientosReport(List<Movimiento> movimientos) {
        return new PdfReportGenerator().generarReportePdf(movimientos);
    }
}
