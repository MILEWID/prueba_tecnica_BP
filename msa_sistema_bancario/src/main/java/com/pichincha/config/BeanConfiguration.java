package com.pichincha.config;

import com.pichincha.ports.input.ClienteUseCase;
import com.pichincha.ports.input.CuentaUseCase;
import com.pichincha.ports.input.MovimientoUseCase;
import com.pichincha.ports.output.ClienteRepositoryPort;
import com.pichincha.ports.output.CuentaRepositoryPort;
import com.pichincha.ports.output.MovimientoRepositoryPort;
import com.pichincha.ports.output.ReporteGeneratorPort;
import com.pichincha.application.usecases.ClienteUseCaseImpl;
import com.pichincha.application.usecases.CuentaUseCaseImpl;
import com.pichincha.application.usecases.MovimientoUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public ClienteUseCase clienteUseCase(ClienteRepositoryPort clienteRepositoryPort) {
        return new ClienteUseCaseImpl(clienteRepositoryPort);
    }

    @Bean
    public CuentaUseCase cuentaUseCase(CuentaRepositoryPort cuentaRepositoryPort,
                                       ClienteRepositoryPort clienteRepositoryPort) {
        return new CuentaUseCaseImpl(cuentaRepositoryPort, clienteRepositoryPort);
    }

    @Bean
    public MovimientoUseCase movimientoUseCase(MovimientoRepositoryPort movimientoRepositoryPort,
                                              CuentaRepositoryPort cuentaRepositoryPort,
                                              ReporteGeneratorPort reporteGeneratorPort) {
        return new MovimientoUseCaseImpl(movimientoRepositoryPort, cuentaRepositoryPort, reporteGeneratorPort);
    }
}
