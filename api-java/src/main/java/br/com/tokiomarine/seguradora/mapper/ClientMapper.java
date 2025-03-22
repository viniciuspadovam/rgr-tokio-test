package br.com.tokiomarine.seguradora.mapper;

import org.springframework.stereotype.Component;

import br.com.tokiomarine.seguradora.dto.ClientRequest;
import br.com.tokiomarine.seguradora.model.Client;

@Component
public class ClientMapper {

    public Client toEntity(ClientRequest dto) {
        Client entity = new Client();
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());
        return entity;
    }

}
