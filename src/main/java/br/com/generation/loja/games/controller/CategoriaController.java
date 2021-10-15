package br.com.generation.loja.games.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.generation.loja.games.model.CategoriaModel;
import br.com.generation.loja.games.repository.CategoriaRepository;

@RestController
@RequestMapping("/api/v1/categoria")
@CrossOrigin ("*")
public class CategoriaController {
	
	private @Autowired CategoriaRepository repository;
	
	@GetMapping
	public ResponseEntity<List<CategoriaModel>> pegarTodos() {
		return ResponseEntity.ok(repository.findAll());
		
	}
	
	@GetMapping("/{id_categoria}")
	public ResponseEntity<CategoriaModel> pegarPorId (@PathVariable long idCategoria){
		return repository.findById(idCategoria)
				.map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.notFound().build());
		
	}
	
	@PostMapping
	public ResponseEntity<CategoriaModel> post (@RequestBody CategoriaModel categoria) {
		return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(categoria));
	}

}