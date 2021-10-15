package br.com.generation.loja.games.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.generation.loja.games.model.ProdutoModel;
import br.com.generation.loja.games.repository.ProdutoRepository;

@RestController
@RequestMapping("/api/v1/produtos")
@CrossOrigin("*")
public class ProdutoController {

	@Autowired
	private ProdutoRepository repository;

	@GetMapping
	public ResponseEntity<List<ProdutoModel>> pegarTodos() {
		return ResponseEntity.ok(repository.findAll());
	}

	@GetMapping("/id {id}")
	public ResponseEntity<ProdutoModel> pegarId(@PathVariable Long id) {
		return repository.findById(id).map(produto -> ResponseEntity.ok(produto))
				.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/produto{nome}")
	public ResponseEntity<List<ProdutoModel>> pegarNomeProduto(@PathVariable String nome){
		return ResponseEntity.ok(repository.findAllByNomeProdutoContainingIgnoreCase(nome));
		
	}

	@PostMapping
	public ResponseEntity<ProdutoModel> postar (@RequestBody ProdutoModel produto){
		return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(produto));
		
	}
	
	@PutMapping
	public ResponseEntity<ProdutoModel> atualizar (@RequestBody ProdutoModel produto){
		return ResponseEntity.status(HttpStatus.OK).body(repository.save(produto));
		
	}

	@DeleteMapping("/{id_produto}")
	public void delete(@PathVariable Long id) {
		repository.deleteById(id);
	}
}
