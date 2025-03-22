package br.com.tokiomarine.seguradora.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class AddressRequest {

    @NotBlank(message = "O endereço é obrigatório.")
    @Size(max = 250, message = "O endereço deve ter, no máximo, 250 caracteres.")
    private String address;

    @NotBlank(message = "O número é obrigatório.")
    @Size(max = 20, message = "O número deve ter, no máximo, 250 caracteres.")
    private String number;
    
    @NotBlank(message = "O complemento é obrigatório.")
    @Size(max = 250, message = "O complemento deve ter, no máximo, 250 caracteres.")
    private String complement;
    
    @NotBlank(message = "O CEP é obrigatório.")
    @Size(max = 10, message = "O CEP deve ter, no máximo, 250 caracteres.")
    private String postalCode;
    
    @NotBlank(message = "A cidade é obrigatório.")
    @Size(max = 250, message = "A cidade deve ter, no máximo, 250 caracteres.")
    private String city;
    
    @NotBlank(message = "O estado é obrigatório.")
    @Size(max = 250, message = "O estado deve ter, no máximo, 250 caracteres.")
    private String state;

}
