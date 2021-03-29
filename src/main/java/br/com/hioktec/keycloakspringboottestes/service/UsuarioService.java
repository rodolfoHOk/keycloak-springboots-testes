package br.com.hioktec.keycloakspringboottestes.service;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.core.Response;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.hioktec.keycloakspringboottestes.dto.UsuarioRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {
	
	private final Keycloak keycloak;
	
	@Value("${keycloak.realm}")
	private String realm;
	
	public List<UserRepresentation> encontrarTodos() {
		return keycloak.realm(realm).users().list();
	}
	
	public List<UserRepresentation> encontrarPorUsername(String username) {
		return keycloak.realm(realm).users().search(username);
	}
	
	public UserRepresentation encontrarPorId(String id) {
		return keycloak.realm(realm).users().get(id).toRepresentation();
	}
	
	public void atribuirGrupo(String idUsuario, String idGrupo) {
		keycloak.realm(realm).users().get(idUsuario).joinGroup(idGrupo);
	}
	
	public void atribuirRole(String idUsuario, RoleRepresentation role) {
		keycloak.realm(realm).users().get(idUsuario).roles().realmLevel().add(Arrays.asList(role));
	}
	
	public Response criar(UsuarioRequest request) {
		CredentialRepresentation password = prepararPasswordRepresentation(request.getPassword());
		
		UserRepresentation usuario = prepararUserRepresentation(request, password);
		
		return keycloak.realm(realm).users().create(usuario);
	}
	
	private CredentialRepresentation prepararPasswordRepresentation(String password) {
		CredentialRepresentation passRepresentation = new CredentialRepresentation();
		passRepresentation.setType(CredentialRepresentation.PASSWORD);
		passRepresentation.setTemporary(false);
		passRepresentation.setValue(password);
		
		return passRepresentation;
	}
	
	private UserRepresentation prepararUserRepresentation(UsuarioRequest request, CredentialRepresentation passRepresentation) {
		UserRepresentation novoUsuario = new UserRepresentation();
		novoUsuario.setUsername(request.getUsername());
		novoUsuario.setCredentials(Arrays.asList(passRepresentation));
		novoUsuario.setEnabled(true);
		novoUsuario.setEmailVerified(true);
		
		return novoUsuario;
	}
}
