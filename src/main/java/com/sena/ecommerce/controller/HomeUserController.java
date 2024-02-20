package com.sena.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sena.ecommerce.service.ProductoService;

@Controller
@RequestMapping("/") // mapeamos a la raiz del proyecto
public class HomeUserController {

	// creamos un objeto privado con anotación autowired
	@Autowired
	private ProductoService productoService;

	@GetMapping("")
	public String home(Model model) {
		model.addAttribute("productos", productoService.findAll());
		return "usuario/home";
	}

}
