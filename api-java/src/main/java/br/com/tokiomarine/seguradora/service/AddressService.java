package br.com.tokiomarine.seguradora.service;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.tokiomarine.seguradora.dto.ViaCepResponse;
import br.com.tokiomarine.seguradora.mapper.AddressMapper;
import br.com.tokiomarine.seguradora.model.Address;
import br.com.tokiomarine.seguradora.repository.AddressRepository;
import br.com.tokiomarine.seguradora.repository.ClientRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final Logger LOGGER = LoggerFactory.getLogger(AddressService.class);

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final ClientRepository clientRepository;
    private final RestTemplate restTemplate;

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

    public Address getByCep(String cep) {
        ViaCepResponse response = restTemplate
            .getForObject("https://viacep.com.br/ws/" + cep + "/json/", ViaCepResponse.class);
        return addressMapper.fromApiResponse(response);
    }

}
