package br.com.tokiomarine.seguradora.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import br.com.tokiomarine.seguradora.dto.ViaCepResponse;
import br.com.tokiomarine.seguradora.mapper.AddressMapper;
import br.com.tokiomarine.seguradora.model.Address;
import br.com.tokiomarine.seguradora.model.Client;
import br.com.tokiomarine.seguradora.repository.AddressRepository;
import br.com.tokiomarine.seguradora.repository.ClientRepository;

@ExtendWith(MockitoExtension.class)
class AddressServiceTest {

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private AddressMapper addressMapper;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private AddressService addressService;

    private Client client;
    private Address address;

    @BeforeEach
    void setUp() {
        client = new Client();
        client.setId(1L);
        address = new Address();
        address.setId(1L);
        address.setClient(client);
    }

    @Test
    void shouldCreateAddressSuccessfully() {
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(addressRepository.save(address)).thenReturn(address);
        
        Address result = addressService.create(1L, address);
        
        assertNotNull(result);
        assertEquals(client, result.getClient());
        verify(addressRepository).save(address);
    }

    @Test
    void shouldThrowExceptionWhenClientNotFoundOnCreate() {
        when(clientRepository.findById(1L)).thenReturn(Optional.empty());
        
        Exception exception = assertThrows(EntityNotFoundException.class, () -> addressService.create(1L, address));
        assertEquals("Cliente 1 não encontrado.", exception.getMessage());
    }

    @Test
    void shouldUpdateAddressSuccessfully() {
        Address updatedAddress = new Address();
        updatedAddress.setId(1L);
        
        when(addressRepository.findById(1L)).thenReturn(Optional.of(address));
        when(addressRepository.save(address)).thenReturn(address);
        
        Address result = addressService.update(1L, updatedAddress);
        
        assertNotNull(result);
        verify(addressRepository).save(address);
    }

    @Test
    void shouldThrowExceptionWhenAddressNotFoundOnUpdate() {
        when(addressRepository.findById(1L)).thenReturn(Optional.empty());
        
        Exception exception = assertThrows(EntityNotFoundException.class, () -> addressService.update(1L, address));
        assertEquals("Endereço 1 não encontrado.", exception.getMessage());
    }

    @Test
    void shouldDeleteAddressSuccessfully() {
        client.setAddress(address);
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        
        addressService.delete(1L);
        
        verify(addressRepository).delete(address);
        verify(clientRepository).save(client);
        assertNull(client.getAddress());
    }

    @Test
    void shouldThrowExceptionWhenClientNotFoundOnDelete() {
        when(clientRepository.findById(1L)).thenReturn(Optional.empty());
        
        Exception exception = assertThrows(EntityNotFoundException.class, () -> addressService.delete(1L));
        assertEquals("Cliente 1 não encontrado.", exception.getMessage());
    }

    @Test
    void shouldGetAddressByCepSuccessfully() {
        String cep = "01001000";
        ViaCepResponse response = new ViaCepResponse();
        Address mappedAddress = new Address();
        
        when(restTemplate.getForObject(anyString(), eq(ViaCepResponse.class))).thenReturn(response);
        when(addressMapper.fromApiResponse(response)).thenReturn(mappedAddress);
        
        Address result = addressService.getByCep(cep);
        
        assertNotNull(result);
        verify(restTemplate).getForObject(anyString(), eq(ViaCepResponse.class));
    }
}
