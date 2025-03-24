package br.com.tokiomarine.seguradora;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import springfox.documentation.spring.web.plugins.Docket;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProvaTokioApplicationTest {

    @Autowired
    private Docket docket;

    @LocalServerPort
    private int port;

    @Test
    void contextLoads() {
        assertNotNull(docket, "Swagger Docket não deve ser nulo");
    }

    @Test
    void applicationStartsSuccessfully() {
        assertTrue(port > 0, "A aplicação deve iniciar em uma porta válida");
    }
}
