package web.lance_bovino.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import web.lance_bovino.model.Papel;

public interface PapelRepository extends JpaRepository<Papel, Long> {
}
