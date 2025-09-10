package com.pichincha.infrastructure.output.adapter.mapper;

import com.pichincha.domain.Cliente;
import com.pichincha.infrastructure.input.adapter.dto.ClienteDTO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-10T16:28:37-0500",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.1.jar, environment: Java 17.0.9 (GraalVM Community)"
)
@Component
public class ClienteMapperImpl implements ClienteMapper {

    @Override
    public ClienteDTO toDto(Cliente entity) {
        if ( entity == null ) {
            return null;
        }

        ClienteDTO clienteDTO = new ClienteDTO();

        clienteDTO.setId( entity.getId() );
        clienteDTO.setNombre( entity.getNombre() );
        clienteDTO.setGenero( entity.getGenero() );
        clienteDTO.setEdad( entity.getEdad() );
        clienteDTO.setIdentificacion( entity.getIdentificacion() );
        clienteDTO.setDireccion( entity.getDireccion() );
        clienteDTO.setTelefono( entity.getTelefono() );
        clienteDTO.setContrasena( entity.getContrasena() );
        clienteDTO.setEstado( entity.getEstado() );

        return clienteDTO;
    }

    @Override
    public Cliente toEntity(ClienteDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Cliente cliente = new Cliente();

        cliente.setId( dto.getId() );
        cliente.setNombre( dto.getNombre() );
        cliente.setGenero( dto.getGenero() );
        cliente.setEdad( dto.getEdad() );
        cliente.setIdentificacion( dto.getIdentificacion() );
        cliente.setDireccion( dto.getDireccion() );
        cliente.setTelefono( dto.getTelefono() );
        cliente.setContrasena( dto.getContrasena() );
        cliente.setEstado( dto.getEstado() );

        return cliente;
    }
}
