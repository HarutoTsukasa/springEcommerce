package com.sena.ecommerce.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sena.ecommerce.model.Orden;
import com.sena.ecommerce.model.Usuario;
import com.sena.ecommerce.service.IOrdenService;
import com.sena.ecommerce.service.IUsuarioService;

import ch.qos.logback.classic.Logger;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

	private final Logger LOGGER = (Logger) LoggerFactory.getLogger(UsuarioController.class);

	@Autowired
	private IUsuarioService usuarioService;

	@Autowired
	private IOrdenService ordenService;

	// nuevo objeto encriptador
	BCryptPasswordEncoder passEncode = new BCryptPasswordEncoder();

	@GetMapping("/registro")
	public String createUser() {
		return "usuario/registro";
	}

	@PostMapping("/save")
	public String save(Usuario usuario, Model model) {
		LOGGER.info("Usuario a registrar: {}", usuario);
		usuario.setTipo("USER");
		// encriptado contraseña
		usuario.setPassword(passEncode.encode(usuario.getPassword()));
		usuarioService.save(usuario);
		return "redirect:/";
	}

	@GetMapping("/login")
	public String login() {
		return "usuario/login";
	}

	// metodo de autenticacion 1 con postmapping sin spring security en post
	// metodo con sping security en get
	@GetMapping("/acceder")
	public String acceder(Usuario usuario, HttpSession session) {
		LOGGER.info("Accesos: {}", usuario);
		// acceder a la db para validar los datos
		// Optional<Usuario> user = usuarioService.findByEmail(usuario.getEmail());
		// cambiamos la validacion de email por la del usuario logeado en la session
		Optional<Usuario> user = usuarioService
				.findById(Integer.parseInt(session.getAttribute("idUsuario").toString()));
		// momentaneo sin spring security
		if (user.isPresent()) {
			// id de el usuario con la sesion logeada
			session.setAttribute("idUsuario", user.get().getId());
			// validacion tipo usuario
			if (user.get().getTipo().equals("ADMIN")) {
				return "redirect:/administrador";
			} else {
				return "redirect:/";
			}
		} else {
			LOGGER.info("Usuario no existe en DB");
		}
		return "redirect:/";
	}

	// obtener compras
	@GetMapping("/compras")
	public String compras(HttpSession session, Model model) {
		model.addAttribute("sesion", session.getAttribute("idUsuario"));
		Usuario usuario = usuarioService.findById(Integer.parseInt(session.getAttribute("idUsuario").toString())).get();
		List<Orden> ordenes = ordenService.findByUsuario(usuario);
		model.addAttribute("ordenes", ordenes);
		return "usuario/compras";
	}

	// obtener detalles de las compras con id usuario
	@GetMapping("/detalle/{id}")
	public String detalleCompra(@PathVariable Integer id, HttpSession session, Model model) {
		// session
		model.addAttribute("sesion", session.getAttribute("idUsuario"));
		LOGGER.info("Id de la orden: {}", id);
		Optional<Orden> orden = ordenService.findById(id);
		model.addAttribute("detalles", orden.get().getDetalle());
		return "usuario/detallecompra";
	}

	// metodo cierre de sesion
	@GetMapping("/cerrar")
	public String cerrarSesion(HttpSession session) {
		session.removeAttribute("idUsuario");
		return "redirect:/";
	}

}
