package br.com.generation.loja.games.controller;

import java.util.List;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.generation.loja.games.model.UsuarioModel;
import br.com.generation.loja.games.model.dtos.CredenciaisDTO;
import br.com.generation.loja.games.model.dtos.UsuarioLoginDTO;
import br.com.generation.loja.games.repository.UsuarioRepository;
import br.com.generation.loja.games.servicos.UsuarioServicos;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/v1/usuario")
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class UsuarioController {

	private @Autowired UsuarioRepository repositorio;
	private @Autowired UsuarioServicos servicos;

	@ApiOperation(value = "Busca lista de usuarios no sistema")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Retorna com Usuarios"),
			@ApiResponse(code = 204, message = "Retorno sem Usuario")
	})
	
	@GetMapping("/todes")
	public ResponseEntity<List<UsuarioModel>> pegarTodes() {
		List<UsuarioModel> objetoLista = repositorio.findAll();

		if (objetoLista.isEmpty()) {
			return ResponseEntity.status(204).build();
		} else {
			return ResponseEntity.status(200).body(objetoLista);
		}
	}

	@ApiOperation(value = "Busca usuario por nome")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Retorna usuario existente ou inexistente"),
			@ApiResponse(code = 204, message = "Retorno inexistente")
	})
	
	@GetMapping("/nome/{nome_usuario}")
	public ResponseEntity<List<UsuarioModel>> buscarPorNomeI(@PathVariable(value = "nome_usuario") String nome) {
		List<UsuarioModel> objetoLista = repositorio.findAllByNomeContainingIgnoreCase(nome);

		if (objetoLista.isEmpty()) {
			return ResponseEntity.status(204).build();
		} else {
			return ResponseEntity.status(200).body(objetoLista);
		}
	}

	@GetMapping("/pesquisa")
	public ResponseEntity<List<UsuarioModel>> buscarPorNomeII(@RequestParam(defaultValue = "") String nome) {
		List<UsuarioModel> objetoLista = repositorio.findAllByNomeContainingIgnoreCase(nome);

		if (objetoLista.isEmpty()) {
			return ResponseEntity.status(204).build();
		} else {
			return ResponseEntity.status(200).body(objetoLista);
		}
	}

	@ApiOperation(value = "Busca usuario por Id")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Retorna usuario existente ou inexistente"),
			@ApiResponse(code = 400, message = "Retorno inexistente")
	})
	
	@GetMapping("/{id_usuario}")
	public ResponseEntity<UsuarioModel> buscarPorId(@PathVariable(value = "id_usuario") Long idUsuario) {
		return repositorio.findById(idUsuario).map(resp -> ResponseEntity.status(200).body(resp))
				.orElseThrow(() -> {
					throw new ResponseStatusException(HttpStatus.NOT_FOUND,
							"ID inexistente, passe um ID valido para pesquisa!.");
				});

	}

	@ApiOperation(value = "Salva novo usuario no sistema")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Retorna usuario cadastrado"),
			@ApiResponse(code = 400, message = "Erro na requisição")
	})
	
	@PostMapping("/salvar")
	public ResponseEntity<Object> salvar(@Valid @RequestBody UsuarioModel novoUsuario) {
		return servicos.cadastrarUsuario(novoUsuario).map(resp -> ResponseEntity.status(201).body(resp))
				.orElseThrow(() -> {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
							"Email existente, cadastre outro email!.");
				});

	}
	
	@ApiOperation(value = "Autentica usuario no sistema")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Retorna credenciais de usuario"),
			@ApiResponse(code = 400, message = "Erro na requisição!")
	})
	
	@PutMapping("/credenciais")
	public ResponseEntity<CredenciaisDTO> credenciais(@Valid @RequestBody UsuarioLoginDTO usuarioParaAutenticar) {
		return servicos.pegarCredenciais(usuarioParaAutenticar);
	}

	@ApiOperation(value = "Deletar usuario existente")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Caso deletado!"),
			@ApiResponse(code = 400, message = "Id de usuario invalido")
	})

	@DeleteMapping("/deletar/{id_usuario}")
	public ResponseEntity<Object> deletar(@PathVariable(value = "id_usuario") Long idUsuario) {
		return repositorio.findById(idUsuario).map(resp -> {
			repositorio.deleteById(idUsuario);
			return ResponseEntity.status(200).build();
		}).orElseThrow(() -> {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					"ID inexistente, passe um ID valido para deletar!.");
		});
	}
}

