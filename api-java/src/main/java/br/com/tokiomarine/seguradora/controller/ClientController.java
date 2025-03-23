package br.com.tokiomarine.seguradora.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.tokiomarine.seguradora.dto.ClientRequest;
import br.com.tokiomarine.seguradora.mapper.ClientMapper;
import br.com.tokiomarine.seguradora.model.Client;
import br.com.tokiomarine.seguradora.service.ClientService;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/v1/client")
@RequiredArgsConstructor
public class ClientController {

    private final Logger LOGGER = LoggerFactory.getLogger(ClientController.class);

    private final ClientService clientService;
    private final ClientMapper clientMapper;

    @GetMapping
    public ResponseEntity<List<Client>> findAll() {
        LOGGER.info("Buscando clientes");
        return ResponseEntity.ok(clientService.findAll());
    }

    @PostMapping
    public ResponseEntity<Client> create(@RequestBody @Valid ClientRequest dto) {
        var client = clientMapper.requestToEntity(dto);
        var savedClient = clientService.create(client);
        LOGGER.info("Cliente {} salvo.", savedClient.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedClient);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Client> update(@PathVariable Long id, @RequestBody @Valid ClientRequest dto) {
        var updatedClientDto = clientMapper.requestToEntity(dto);
        var updatedClient = clientService.update(id, updatedClientDto);
        LOGGER.info("Cliente {} alterado.", updatedClient.getId());
        return ResponseEntity.ok(updatedClient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        clientService.delete(id);
        LOGGER.info("Cliente {} excluído.", id);
        return ResponseEntity.noContent().build();
    }

}
