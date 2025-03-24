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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.tokiomarine.seguradora.dto.AddressRequest;
import br.com.tokiomarine.seguradora.mapper.AddressMapper;
import br.com.tokiomarine.seguradora.model.Address;
import br.com.tokiomarine.seguradora.service.AddressService;

public class AddressControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AddressService addressService;

    @Mock
    private AddressMapper addressMapper;

    @InjectMocks
    private AddressController addressController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(addressController).build();
        this.objectMapper = new ObjectMapper();
    }

    @Test
    void shouldCreateAddressSuccessfully() throws Exception {
        AddressRequest addressRequest = new AddressRequest();
        addressRequest.setAddress("Rua A");
        addressRequest.setNumber("123");
        addressRequest.setComplement("Apto 45");
        addressRequest.setPostalCode("12345-678");
        addressRequest.setCity("São Paulo");
        addressRequest.setState("SP");
        
        Address address = new Address();
        address.setId(1L);
        
        when(addressMapper.requestToEntity(any(AddressRequest.class))).thenReturn(address);
        when(addressService.create(eq(1L), any(Address.class))).thenReturn(address);
        
        mockMvc.perform(post("/api/v1/address/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(addressRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
        
        verify(addressService).create(eq(1L), any(Address.class));
    }
    
    @Test
    void shouldUpdateAddressSuccessfully() throws Exception {
        AddressRequest addressRequest = new AddressRequest();
        addressRequest.setAddress("Rua B");
        addressRequest.setNumber("456");
        addressRequest.setComplement("Casa");
        addressRequest.setPostalCode("98765-432");
        addressRequest.setCity("Rio de Janeiro");
        addressRequest.setState("RJ");
        
        Address address = new Address();
        address.setId(2L);
        
        when(addressMapper.requestToEntity(any(AddressRequest.class))).thenReturn(address);
        when(addressService.update(eq(2L), any(Address.class))).thenReturn(address);
        
        mockMvc.perform(put("/api/v1/address/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(addressRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2));
        
        verify(addressService).update(eq(2L), any(Address.class));
    }
    
    @Test
    void shouldDeleteAddressSuccessfully() throws Exception {
        doNothing().when(addressService).delete(3L);
        
        mockMvc.perform(delete("/api/v1/address/3"))
                .andExpect(status().isNoContent());
        
        verify(addressService).delete(3L);
    }
    
    @Test
    void shouldGetAddressByCepSuccessfully() throws Exception {
        Address address = new Address();
        address.setId(4L);
        
        when(addressService.getByCep("12345-678")).thenReturn(address);
        
        mockMvc.perform(get("/api/v1/address")
                .param("cep", "12345-678"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(4));
        
        verify(addressService).getByCep("12345-678");
    }
}
