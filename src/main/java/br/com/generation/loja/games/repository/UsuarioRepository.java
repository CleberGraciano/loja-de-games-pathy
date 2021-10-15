package br.com.generation.loja.games.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.generation.loja.games.model.UsuarioModel;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioModel, Long> {


	public Optional<UsuarioModel> findByEmail(String email);
	public List<UsuarioModel> findAllByNomeContainingIgnoreCase(String nome);

}