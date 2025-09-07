package com.pichincha.mappers;

import com.pichincha.domain.Cliente;
import com.pichincha.dto.ClienteDTO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-07T03:56:26-0500",
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
        clienteDTO.setClienteId( entity.getClienteId() );
        clienteDTO.setContrasena( entity.getContrasena() );
        clienteDTO.setEstado( entity.getEstado() );
        clienteDTO.setNombre( entity.getNombre() );
        clienteDTO.setGenero( entity.getGenero() );
        clienteDTO.setEdad( entity.getEdad() );
        clienteDTO.setIdentificacion( entity.getIdentificacion() );
        clienteDTO.setDireccion( entity.getDireccion() );
        clienteDTO.setTelefono( entity.getTelefono() );

        return clienteDTO;
    }

    @Override
    public Cliente toEntity(ClienteDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Cliente cliente = new Cliente();

        cliente.setId( dto.getId() );
        cliente.setClienteId( dto.getClienteId() );
        cliente.setContrasena( dto.getContrasena() );
        cliente.setEstado( dto.getEstado() );
        cliente.setNombre( dto.getNombre() );
        cliente.setGenero( dto.getGenero() );
        cliente.setEdad( dto.getEdad() );
        cliente.setIdentificacion( dto.getIdentificacion() );
        cliente.setDireccion( dto.getDireccion() );
        cliente.setTelefono( dto.getTelefono() );

        return cliente;
    }
}
