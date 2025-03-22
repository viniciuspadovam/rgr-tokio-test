package br.com.tokiomarine.seguradora.mapper;

import org.springframework.stereotype.Component;

import br.com.tokiomarine.seguradora.dto.AddressRequest;
import br.com.tokiomarine.seguradora.model.Address;

@Component
public class AddressMapper {

    public Address toEntity(AddressRequest dto) {
        Address entity = new Address();
        entity.setAddress(dto.getAddress());
        entity.setNumber(dto.getNumber());
        entity.setComplement(dto.getComplement());
        entity.setPostalCode(dto.getPostalCode());
        entity.setCity(dto.getCity());
        entity.setState(dto.getState());
        return entity;
    }

}
