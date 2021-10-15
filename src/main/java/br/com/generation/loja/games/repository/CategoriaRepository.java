package br.com.generation.loja.games.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.generation.loja.games.model.CategoriaModel;

public interface CategoriaRepository extends JpaRepository<CategoriaModel, Long> {
	
	public List<CategoriaModel> findAllByCategoriaContainingIgnoreCase (String categoria);


}