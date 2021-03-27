package br.com.hioktec.keycloakspringboottestes.restcontroller;

import javax.annotation.security.RolesAllowed;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/testes")
public class TesteController {
	
	@GetMapping("/anonimo")
	public ResponseEntity<String> getAnonimo() {
		return ResponseEntity.ok("Olá Anômino!");
	}
	
	@RolesAllowed("user")
	@GetMapping("/usuario")
	public ResponseEntity<String> getUsuario() {
		return ResponseEntity.ok("Olá Usuário!");
	}
	
	@RolesAllowed("admin")
	@GetMapping("/admin")
	public ResponseEntity<String> getAdmin() {
		return ResponseEntity.ok("Olá Administrador!");
	}
	
	@RolesAllowed({"admin", "user"})
	@GetMapping("/todos-usuarios")
	public ResponseEntity<String> getTodosUsuarios() {
		return ResponseEntity.ok("Olá Pesssoa Logada!");
	}
}
