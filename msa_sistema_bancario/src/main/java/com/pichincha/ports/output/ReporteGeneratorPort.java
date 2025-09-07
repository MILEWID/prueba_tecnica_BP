package com.pichincha.ports.output;

import com.pichincha.domain.Movimiento;
import java.util.List;

public interface ReporteGeneratorPort {
    String generarReportePdf(List<Movimiento> movimientos);
}
