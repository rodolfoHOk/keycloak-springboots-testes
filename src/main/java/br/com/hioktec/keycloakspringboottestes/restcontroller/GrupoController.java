package br.com.hioktec.keycloakspringboottestes.restcontroller;

import java.util.List;

import org.keycloak.representations.idm.GroupRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.hioktec.keycloakspringboottestes.service.GrupoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/grupos")
@RequiredArgsConstructor
public class GrupoController {
	
	private final GrupoService service;
	
	@GetMapping
	public List<GroupRepresentation> pegarTodos() {
		return service.encontrarTodos();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void criarGrupo(@RequestParam(name = "nome", required = true) String nome) {
		service.criar(nome);
	}
}
