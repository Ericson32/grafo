package br.radixeng.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import br.radixeng.model.Grafo;

public interface GrafoRepository extends JpaRepository<Grafo, Serializable>{

}
