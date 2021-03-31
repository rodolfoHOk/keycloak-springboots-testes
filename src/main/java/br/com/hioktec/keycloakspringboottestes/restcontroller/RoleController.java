package br.com.hioktec.keycloakspringboottestes.restcontroller;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.NotFoundException;

import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.hioktec.keycloakspringboottestes.service.RoleService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/roles")
@RolesAllowed("admin")
@RequiredArgsConstructor
public class RoleController {
	
	private final RoleService service;
	
	@GetMapping
	public List<RoleRepresentation> pegarTodos() {
		return service.encontrarTodos();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void criarRole(@RequestParam(name = "nome", required = true) String nome) {
		try {
			service.encontrarPorNome(nome);
		} catch (NotFoundException ex) {
			service.criar(nome);
			return;
		}
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Role já existe!");
	}
}
