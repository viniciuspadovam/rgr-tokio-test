package br.com.tokiomarine.seguradora.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import br.com.tokiomarine.seguradora.model.Client;
import br.com.tokiomarine.seguradora.repository.ClientRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final Logger LOGGER = LoggerFactory.getLogger(ClientService.class);

    private final ClientRepository clientRepository;

    public List<Client> findAll() {
        return clientRepository.findAll();
    }
    
    public List<Client> findByName(String name) {
    	return clientRepository.findByName(name);
    }

    public Client create(Client client) {
        return clientRepository.save(client);
    }

    public Client update(Long id, Client updatedClient) {
        return clientRepository.findById(id)
            .map(existingClient -> {
                BeanUtils.copyProperties(updatedClient, existingClient, "id", "address");
                return clientRepository.save(existingClient);
            })
            .orElseThrow(() -> {
                String message = "Cliente " + id + " não encontrado.";
                LOGGER.error(message, EntityNotFoundException.class);
                throw new EntityNotFoundException(message);
            });
    }
        
    public void delete(Long id) {
        if(!clientRepository.existsById(id)) {
            String message = "Cliente " + id + " não encontrado.";
            LOGGER.error(message, EntityNotFoundException.class);
            throw new EntityNotFoundException(message);
        }

        clientRepository.deleteById(id);
    }

}
