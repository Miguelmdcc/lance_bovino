package com.example.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.model.Gado;
import com.example.model.Status;
import com.example.repository.queries.gado.GadoQueries;

public interface GadoRepository extends JpaRepository<Gado, Long>, GadoQueries {

    Optional<Gado> findByCodigoAndStatus(long codigo, Status status);

}
