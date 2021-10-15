package br.com.generation.loja.games.seguranca;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.generation.loja.games.model.UsuarioModel;
import br.com.generation.loja.games.repository.UsuarioRepository;



@Service
public class UserDetailsServiceImplements implements UserDetailsService {
	
	private @Autowired UsuarioRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<UsuarioModel> objetoOptional = repository.findByEmail(username);
		
		if (objetoOptional.isPresent()) {
			return new UserDetailsImplements(objetoOptional.get());
		} else {
			throw new UsernameNotFoundException(username + " Não existe!");
		}
		
	}

}
