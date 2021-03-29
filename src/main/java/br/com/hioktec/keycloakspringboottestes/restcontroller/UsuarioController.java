package br.com.hioktec.keycloakspringboottestes.restcontroller;

import java.net.URI;
import java.util.List;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.hioktec.keycloakspringboottestes.dto.UsuarioRequest;
import br.com.hioktec.keycloakspringboottestes.service.RoleService;
import br.com.hioktec.keycloakspringboottestes.service.UsuarioService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
	
	private final UsuarioService service;
	
	private final RoleService roleService;
	
	@GetMapping
	public List<UserRepresentation> pegarTodos() {
		return service.encontrarTodos();
	}
	
	@GetMapping("/{id}")
	public UserRepresentation pegarPorId(@PathVariable("id") String id) {
		try {
			return service.encontrarPorId(id);
		} catch (NotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario não encontrado pelo o id informado!");
		}
	}
	
	@GetMapping("/nomeusuario/{username}")
	public List<UserRepresentation> pegarPorUsername(@PathVariable("username") String username) {
		return service.encontrarPorUsername(username);
	}
	
	@PostMapping
	public ResponseEntity<URI> criarUsuario(@RequestBody UsuarioRequest usuarioRequest) {
		service.encontrarPorUsername(usuarioRequest.getUsername())
			.forEach(usuario -> {
				System.out.println(usuario.getUsername());
				if(usuario.getUsername().equals(usuarioRequest.getUsername())) {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nome de usuário já existe!");
				}
		});
		
		Response response = service.criar(usuarioRequest);
		
		if(response.getStatus() != org.apache.http.HttpStatus.SC_CREATED) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Usuario não foi criado");
		}
		
		return new ResponseEntity<URI>(response.getLocation(), HttpStatus.CREATED);
	}
	
	@PostMapping("/{idUsuario}/grupos/{idGrupo}")
	public void atribuirGrupoAoUsuario(@PathVariable("idUsuario") String idUsuario, @PathVariable("idGrupo") String idGrupo) {
		try {
			service.encontrarPorId(idUsuario);
		} catch (NotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID de Usuário não existe!");
		}
		try {
		 service.atribuirGrupo(idUsuario, idGrupo);
		} catch (NotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID de Grupo não existe!");
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Não foi atribuido grupo ao usuário!");
		}
	}
	
	@PostMapping("/{idUsuario}/roles/{nomeRole}")
	public void atribuirRoleAoUsuario(@PathVariable("idUsuario") String idUsuario, @PathVariable("nomeRole") String nomeRole) {
		try {
			service.encontrarPorId(idUsuario);
		} catch (NotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID de Usuário não existe!");
		}
		try {
			RoleRepresentation role = roleService.encontrarPorNome(nomeRole);
			service.atribuirRole(idUsuario, role);
		} catch (NotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Função não existe!");
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Não foi atribuido função ao usuário!");
		}
	}
}
