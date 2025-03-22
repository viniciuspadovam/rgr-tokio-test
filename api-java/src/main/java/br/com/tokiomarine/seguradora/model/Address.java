package br.com.tokiomarine.seguradora.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

@Entity
@Table(name = "addresses")
@Data
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String address;
    private String number;
    private String complement;
    @Column(name = "postal_code")
    private String postalCode;
    private String city;
    private String state;

    @OneToOne
    @JoinColumn(name = "id_client")
    @JsonBackReference
    private Client client;

}
