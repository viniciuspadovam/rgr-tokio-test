package br.com.tokiomarine.seguradora.service;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import br.com.tokiomarine.seguradora.model.Address;
import br.com.tokiomarine.seguradora.repository.AddressRepository;
import br.com.tokiomarine.seguradora.repository.ClientRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final Logger LOGGER = LoggerFactory.getLogger(AddressService.class);

    private final AddressRepository addressRepository;
    private final ClientRepository clientRepository;

    public Address create(Long clientId, Address address) {
        var client = clientRepository.findById(clientId)
            .orElseThrow(() -> {
                String message = "Cliente " + clientId + " não encontrado.";
                LOGGER.error(message, EntityNotFoundException.class);
                throw new EntityNotFoundException(message);
    }       );
        address.setClient(client);
        return addressRepository.save(address);
    }

    public Address update(Long id, Address updatedAddress) {
        return addressRepository.findById(id)
            .map(existingAddress -> {
                BeanUtils.copyProperties(updatedAddress, existingAddress, "id", "client");
                return addressRepository.save(existingAddress);
            })
            .orElseThrow(() -> new EntityNotFoundException("Endereço " + id + " não encontrado."));
    }

    public void delete(Long id) {
        if(!addressRepository.existsById(id)) {
            throw new EntityNotFoundException("Endereço " + id + " não encontrado.");
        }

        addressRepository.deleteById(id);
    }

}
