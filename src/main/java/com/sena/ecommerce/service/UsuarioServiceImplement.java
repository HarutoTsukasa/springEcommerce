package com.sena.ecommerce.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sena.ecommerce.model.Usuario;
import com.sena.ecommerce.repository.UsuarioRepository;

@Service
public class UsuarioServiceImplement implements UsuarioService {

	// objeto de tipo private que va a ser del tipo repository
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public Usuario save(Usuario usuario) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Usuario> get(Integer id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public void update(Usuario usuario) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Optional<Usuario> findById(Integer id) {
		// TODO Auto-generated method stub
		return usuarioRepository.findById(id);
	}

}
