package br.com.tokiomarine.seguradora.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.tokiomarine.seguradora.model.Client;
import br.com.tokiomarine.seguradora.repository.ClientRepository;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientService clientService;

    private Client client;

    @BeforeEach
    void setUp() {
        client = new Client();
        client.setId(1L);
        client.setFirstName("John");
    }

    @Test
    void shouldFindAllClientsSuccessfully() {
        List<Client> clients = Arrays.asList(client, new Client());
        when(clientRepository.findAll()).thenReturn(clients);

        List<Client> result = clientService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(clientRepository).findAll();
    }

    @Test
    void shouldCreateClientSuccessfully() {
        when(clientRepository.save(client)).thenReturn(client);

        Client result = clientService.create(client);

        assertNotNull(result);
        assertEquals(client.getFirstName(), result.getFirstName());
        verify(clientRepository).save(client);
    }

    @Test
    void shouldUpdateClientSuccessfully() {
        Client updatedClient = new Client();
        updatedClient.setFirstName("Jane");

        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(clientRepository.save(client)).thenReturn(client);

        Client result = clientService.update(1L, updatedClient);

        assertNotNull(result);
        assertEquals("Jane", result.getFirstName());
        verify(clientRepository).save(client);
    }

    @Test
    void shouldThrowExceptionWhenClientNotFoundOnUpdate() {
        when(clientRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> clientService.update(1L, client));
        assertEquals("Cliente 1 não encontrado.", exception.getMessage());
    }

    @Test
    void shouldDeleteClientSuccessfully() {
        when(clientRepository.existsById(1L)).thenReturn(true);
        doNothing().when(clientRepository).deleteById(1L);

        assertDoesNotThrow(() -> clientService.delete(1L));
        verify(clientRepository).deleteById(1L);
    }

    @Test
    void shouldThrowExceptionWhenClientNotFoundOnDelete() {
        when(clientRepository.existsById(1L)).thenReturn(false);

        Exception exception = assertThrows(EntityNotFoundException.class, () -> clientService.delete(1L));
        assertEquals("Cliente 1 não encontrado.", exception.getMessage());
    }
}
