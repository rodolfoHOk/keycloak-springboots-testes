package br.com.hioktec.keycloakspringboottestes.dto;

import lombok.Data;

@Data
public class UsuarioRequest {
	
	private String username;
	
	private String password;
}
