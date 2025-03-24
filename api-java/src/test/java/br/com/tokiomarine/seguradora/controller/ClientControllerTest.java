package br.com.tokiomarine.seguradora.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.tokiomarine.seguradora.dto.ClientRequest;
import br.com.tokiomarine.seguradora.mapper.ClientMapper;
import br.com.tokiomarine.seguradora.model.Client;
import br.com.tokiomarine.seguradora.service.ClientService;

public class ClientControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ClientService clientService;

    @Mock
    private ClientMapper clientMapper;

    @InjectMocks
    private ClientController clientController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(clientController).build();
        this.objectMapper = new ObjectMapper();
    }

    @Test
    void shouldFindAllClientsSuccessfully() throws Exception {
        Client client1 = new Client();
        client1.setId(1L);
        Client client2 = new Client();
        client2.setId(2L);
        List<Client> clients = Arrays.asList(client1, client2);

        when(clientService.findAll()).thenReturn(clients);

        mockMvc.perform(get("/api/v1/client"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));

        verify(clientService).findAll();
    }

    @Test
    void shouldCreateClientSuccessfully() throws Exception {
        ClientRequest clientRequest = new ClientRequest();
        clientRequest.setFirstName("Jotaro");
        clientRequest.setLastName("Joestar");
        clientRequest.setEmail("jotaro@example.com");

        Client client = new Client();
        client.setId(1L);

        when(clientMapper.requestToEntity(any(ClientRequest.class))).thenReturn(client);
        when(clientService.create(any(Client.class))).thenReturn(client);

        mockMvc.perform(post("/api/v1/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clientRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));

        verify(clientMapper).requestToEntity(any(ClientRequest.class));
        verify(clientService).create(any(Client.class));
    }

    @Test
    void shouldUpdateClientSuccessfully() throws Exception {
        ClientRequest clientRequest = new ClientRequest();
        clientRequest.setFirstName("Josuke");
        clientRequest.setLastName("Higashikata");
        clientRequest.setEmail("josuke@example.com");

        Client client = new Client();
        client.setId(2L);

        when(clientMapper.requestToEntity(any(ClientRequest.class))).thenReturn(client);
        when(clientService.update(eq(2L), any(Client.class))).thenReturn(client);

        mockMvc.perform(put("/api/v1/client/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clientRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2));

        verify(clientMapper).requestToEntity(any(ClientRequest.class));
        verify(clientService).update(eq(2L), any(Client.class));
    }

    @Test
    void shouldDeleteClientSuccessfully() throws Exception {
        doNothing().when(clientService).delete(3L);

        mockMvc.perform(delete("/api/v1/client/3"))
                .andExpect(status().isNoContent());

        verify(clientService).delete(3L);
    }
}
