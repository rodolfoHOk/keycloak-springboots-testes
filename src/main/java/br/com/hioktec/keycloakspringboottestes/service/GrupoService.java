package br.com.hioktec.keycloakspringboottestes.service;

import java.util.List;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.GroupRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GrupoService {
	
	private final Keycloak keycloak;
	
	@Value("${keycloak.realm}")
	private String realm;

	public void criar(String nome) {
		GroupRepresentation grupo = new GroupRepresentation();
		grupo.setName(nome);
		keycloak.realm(realm).groups().add(grupo);
	}
	
	public List<GroupRepresentation> encontrarTodos() {
		return keycloak.realm(realm).groups().groups();
	}
}
