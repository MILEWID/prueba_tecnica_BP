package com.pichincha.application.output.port;

import com.pichincha.domain.Movimiento;
import java.util.List;

public interface ReporteOutputPort {
    String generarReportePdf(List<Movimiento> movimientos);
}
