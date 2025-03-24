package br.com.tokiomarine.seguradora.config;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
class RestTemplateConfigTest {

    @Mock
    private RestTemplateBuilder restTemplateBuilder;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private RestTemplateConfig restTemplateConfig;

    @Test
    void testRestTemplateBeanCreation() {
        when(restTemplateBuilder.build()).thenReturn(restTemplate);
        
        RestTemplate result = restTemplateConfig.restTemplate(restTemplateBuilder);
        
        assertNotNull(result);
        assertEquals(restTemplate, result);
        verify(restTemplateBuilder, times(1)).build();
    }
}
