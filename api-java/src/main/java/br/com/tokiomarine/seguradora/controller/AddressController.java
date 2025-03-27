package br.com.tokiomarine.seguradora.controller;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.tokiomarine.seguradora.dto.AddressRequest;
import br.com.tokiomarine.seguradora.mapper.AddressMapper;
import br.com.tokiomarine.seguradora.model.Address;
import br.com.tokiomarine.seguradora.service.AddressService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/address")
@RequiredArgsConstructor
@CrossOrigin // to test locally with Angular
public class AddressController {

    private final Logger LOGGER = LoggerFactory.getLogger(AddressController.class);

    private final AddressService addressService;
    private final AddressMapper addressMapper;

    @PostMapping("/{clientId}")
    public ResponseEntity<Address> create(@PathVariable Long clientId, @RequestBody @Valid AddressRequest dto) {
        var address = addressMapper.requestToEntity(dto);
        var savedAddress = addressService.create(clientId, address);
        LOGGER.info("Endereço {} salvo.", savedAddress.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAddress);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Address> update(@PathVariable Long id, @RequestBody @Valid AddressRequest dto) {
        var address = addressMapper.requestToEntity(dto);
        var updatedAddress = addressService.update(id, address);
        LOGGER.info("Endereço {} alterado.", updatedAddress.getId());
        return ResponseEntity.ok(updatedAddress);
    }

    @DeleteMapping("/{clientId}")
    public ResponseEntity<Void> delete(@PathVariable Long clientId) {
        addressService.delete(clientId);
        LOGGER.info("Endereço do cliente {} excluído.", clientId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Address> getByCep(@PathParam("cep") String cep) {
        LOGGER.info("Buscando por CEP: {}", cep);
        return ResponseEntity.ok(addressService.getByCep(cep));
    }

}
