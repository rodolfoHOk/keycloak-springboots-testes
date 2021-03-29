package br.com.hioktec.keycloakspringboottestes.service;

import java.util.List;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleService {
	
	private final Keycloak keycloak;
	
	@Value("${keycloak.realm}")
	private String realm;

	public void criar(String nome) {
		RoleRepresentation role = new RoleRepresentation();
		role.setName(nome);
		
		keycloak.realm(realm).roles().create(role);
	}
	
	public List<RoleRepresentation> encontrarTodos() {
		return keycloak.realm(realm).roles().list();
	}
	
	public RoleRepresentation encontrarPorNome(String nome) {
		return keycloak.realm(realm).roles().get(nome).toRepresentation();
	}
}
