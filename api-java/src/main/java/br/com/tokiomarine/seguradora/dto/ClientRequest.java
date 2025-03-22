package br.com.tokiomarine.seguradora.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class ClientRequest {

    @NotBlank(message = "O primeiro nome é obrigatório.")
    @Size(max = 250, message = "O primeiro nome deve ter, no máximo, 250 caracteres.")
    String firstName;

    @Size(max = 250, message = "O último nome deve ter, no máximo, 250 caracteres.")
    String lastName;

    @NotBlank(message = "O E-mail é obrigatório.")
    @Email(message = "E-mail deve ter estrutura de e-mail.")
    @Size(max = 250, message = "O E-mail deve ter, no máximo, 250 caracteres.")
    String email;

}
