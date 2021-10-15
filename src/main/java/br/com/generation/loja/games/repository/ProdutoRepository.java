package br.com.generation.loja.games.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.generation.loja.games.model.ProdutoModel;

@Repository
public interface ProdutoRepository extends JpaRepository<ProdutoModel, Long> {

	public List<ProdutoModel> findAllByNomeProdutoContainingIgnoreCase(String produto);
}
