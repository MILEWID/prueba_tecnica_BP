package com.pichincha.infrastructure.output.adapter.report;

import com.pichincha.domain.Movimiento;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PdfReportBuilder {

    private static final Color HEADER_COLOR = new DeviceRgb(52, 73, 94);
    private static final Color ACCENT_COLOR = new DeviceRgb(52, 152, 219);
    private static final Color SUCCESS_COLOR = new DeviceRgb(39, 174, 96);
    private static final Color WARNING_COLOR = new DeviceRgb(230, 126, 34);
    private static final DecimalFormat CURRENCY_FORMAT = new DecimalFormat("#,##0.00");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private final ByteArrayOutputStream baos;
    private final PdfWriter writer;
    private final PdfDocument pdf;
    private final Document document;
    private final PdfFont boldFont;
    private final PdfFont normalFont;

    private PdfReportBuilder() {
        try {
            this.baos = new ByteArrayOutputStream();
            this.writer = new PdfWriter(baos);
            this.pdf = new PdfDocument(writer);
            this.document = new Document(pdf);
            this.boldFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
            this.normalFont = PdfFontFactory.createFont(StandardFonts.HELVETICA);
        } catch (Exception e) {
            throw new RuntimeException("Error initializing PDF builder: " + e.getMessage(), e);
        }
    }

    public static PdfReportBuilder builder() {
        return new PdfReportBuilder();
    }

    public PdfReportBuilder withHeader() {
        Paragraph title = new Paragraph("ESTADO DE CUENTA BANCARIO")
                .setFont(boldFont)
                .setFontSize(18)
                .setFontColor(HEADER_COLOR)
                .setMarginBottom(5);
        document.add(title);

        Paragraph subtitle = new Paragraph("Banco Pichincha - Sistema de Gestión")
                .setFont(normalFont)
                .setFontSize(12)
                .setFontColor(ColorConstants.DARK_GRAY)
                .setMarginBottom(20);
        document.add(subtitle);
        return this;
    }

    public PdfReportBuilder withClientInfo(List<Movimiento> movimientos) {
        if (movimientos == null || movimientos.isEmpty()) return this;

        Movimiento firstMovement = movimientos.get(0);
        String clienteName = firstMovement.getCuenta().getCliente().getNombre();
        String clienteId = firstMovement.getCuenta().getCliente().getIdentificacion();
        String accountNumber = firstMovement.getCuenta().getNumeroCuenta();

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
        return this;
    }

    public PdfReportBuilder withSummary(List<Movimiento> movimientos) {
        double totalIngresos = 0;
        double totalEgresos = 0;
        double saldoFinal = 0;

        if (movimientos != null) {
            for (Movimiento mov : movimientos) {
                if (mov.getValor() > 0) {
                    totalIngresos += mov.getValor();
                } else {
                    totalEgresos += Math.abs(mov.getValor());
                }
                saldoFinal = mov.getSaldo();
            }
        }

        document.add(new Paragraph("RESUMEN EJECUTIVO")
                .setFont(boldFont)
                .setFontSize(14)
                .setFontColor(ACCENT_COLOR)
                .setMarginBottom(10));

        Table summaryTable = new Table(new float[]{2, 2, 2});
        summaryTable.setWidth(400);

        summaryTable.addHeaderCell(new Cell().add(new Paragraph("TOTAL INGRESOS")
                .setFont(boldFont).setFontColor(ColorConstants.WHITE))
                .setBackgroundColor(SUCCESS_COLOR));
        summaryTable.addHeaderCell(new Cell().add(new Paragraph("TOTAL EGRESOS")
                .setFont(boldFont).setFontColor(ColorConstants.WHITE))
                .setBackgroundColor(WARNING_COLOR));
        summaryTable.addHeaderCell(new Cell().add(new Paragraph("SALDO FINAL")
                .setFont(boldFont).setFontColor(ColorConstants.WHITE))
                .setBackgroundColor(HEADER_COLOR));

        summaryTable.addCell(new Cell().add(new Paragraph("$" + CURRENCY_FORMAT.format(totalIngresos))
                .setFont(normalFont).setFontColor(SUCCESS_COLOR).setFontSize(12)));
        summaryTable.addCell(new Cell().add(new Paragraph("$" + CURRENCY_FORMAT.format(totalEgresos))
                .setFont(normalFont).setFontColor(WARNING_COLOR).setFontSize(12)));
        summaryTable.addCell(new Cell().add(new Paragraph("$" + CURRENCY_FORMAT.format(saldoFinal))
                .setFont(normalFont).setFontColor(HEADER_COLOR).setFontSize(12)));

        document.add(summaryTable);
        document.add(new Paragraph("\n"));
        return this;
    }

    public PdfReportBuilder withMovementsTable(List<Movimiento> movimientos) {
        document.add(new Paragraph("DETALLE DE MOVIMIENTOS")
                .setFont(boldFont)
                .setFontSize(14)
                .setFontColor(ACCENT_COLOR)
                .setMarginBottom(10));

        if (movimientos == null || movimientos.isEmpty()) {
            document.add(new Paragraph("No hay movimientos registrados para el período seleccionado.")
                    .setFont(normalFont)
                    .setFontColor(ColorConstants.GRAY)
                    .setMarginBottom(20));
            return this;
        }

        Table movementsTable = new Table(new float[]{3, 2, 2, 2, 2, 2});
        movementsTable.setWidth(550);

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

            Color movementColor = mov.getValor() >= 0 ? SUCCESS_COLOR : WARNING_COLOR;
            String movementText = (mov.getValor() >= 0 ? "+" : "") + "$" + CURRENCY_FORMAT.format(mov.getValor());
            movementsTable.addCell(new Cell().add(new Paragraph(movementText)
                    .setFont(normalFont).setFontColor(movementColor).setFontSize(8))
                    .setBackgroundColor(rowColor));

            movementsTable.addCell(new Cell().add(new Paragraph("$" + CURRENCY_FORMAT.format(mov.getSaldo()))
                    .setFont(normalFont).setFontSize(8)).setBackgroundColor(rowColor));
        }

        document.add(movementsTable);

        document.add(new Paragraph("\nTotal de movimientos encontrados: " + movimientos.size())
                .setFont(normalFont)
                .setFontSize(10)
                .setFontColor(ColorConstants.GRAY));

        return this;
    }

    public PdfReportBuilder withFooter() {
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
        return this;
    }

    public byte[] build() {
        try {
            document.close();
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error building PDF: " + e.getMessage(), e);
        }
    }

    public static byte[] buildReport(List<Movimiento> movimientos) {
        return PdfReportBuilder.builder()
                .withHeader()
                .withClientInfo(movimientos)
                .withSummary(movimientos)
                .withMovementsTable(movimientos)
                .withFooter()
                .build();
    }
}
