package br.com.tokiomarine.seguradora.repository;

import br.com.tokiomarine.seguradora.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
}
