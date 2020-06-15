package com.keycoak.userfed;

import org.keycloak.component.ComponentModel;
import org.keycloak.credential.CredentialInput;
import org.keycloak.credential.CredentialInputUpdater;
import org.keycloak.credential.CredentialInputValidator;
import org.keycloak.models.GroupModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.RoleModel;
import org.keycloak.models.UserCredentialModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.credential.PasswordCredentialModel;
import org.keycloak.storage.StorageId;
import org.keycloak.storage.UserStorageProvider;
import org.keycloak.storage.user.UserLookupProvider;

import java.util.Collections;
import java.util.Set;

public class FedUserStorageProvider
		implements UserStorageProvider, UserLookupProvider, CredentialInputUpdater, CredentialInputValidator {

	private final KeycloakSession session;
	private final ComponentModel model;
	private final FedRepository repository;

	public FedUserStorageProvider(KeycloakSession session, ComponentModel model, FedRepository repository) {
		System.out.println("############################DemoUserStorageProvider#####################################"
				+ repository);

		this.session = session;
		this.model = model;
		this.repository = repository;
	}

	@Override
	public boolean supportsCredentialType(String credentialType) {
		System.out.println(
				"############################supportsCredentialType##################################" + repository);

		return PasswordCredentialModel.TYPE.equals(credentialType);
	}

	@Override
	public boolean isConfiguredFor(RealmModel realm, UserModel user, String credentialType) {
		System.out
				.println("############################isConfiguredFor##################################" + repository);

		return supportsCredentialType(credentialType);
	}

	@Override
	public boolean isValid(RealmModel realm, UserModel user, CredentialInput input) {
		System.out.println("############################isValid##################################" + repository);

		if (!supportsCredentialType(input.getType()) || !(input instanceof UserCredentialModel)) {
			return false;
		}
		UserCredentialModel cred = (UserCredentialModel) input;
		return repository.validateCredentials(user.getUsername(), cred.getChallengeResponse());
	}

	@Override
	public boolean updateCredential(RealmModel realm, UserModel user, CredentialInput input) {
		System.out
				.println("############################updateCredential##################################" + repository);

		if (!supportsCredentialType(input.getType()) || !(input instanceof UserCredentialModel)) {
			return false;
		}
		UserCredentialModel cred = (UserCredentialModel) input;
		return repository.updateCredentials(user.getUsername(), cred.getChallengeResponse());
	}

	@Override
	public void disableCredentialType(RealmModel realm, UserModel user, String credentialType) {
		System.out.println(
				"############################disableCredentialType##################################" + repository);

	}

	@Override
	public Set<String> getDisableableCredentialTypes(RealmModel realm, UserModel user) {
		System.out.println("############################getDisableableCredentialTypes##################################"
				+ repository);

		return Collections.emptySet();
	}

	@Override
	public void preRemove(RealmModel realm) {
		System.out.println("############################preRemove##################################" + repository);

	}

	@Override
	public void preRemove(RealmModel realm, GroupModel group) {
		System.out.println("############################preRemove2##################################" + repository);

	}

	@Override
	public void preRemove(RealmModel realm, RoleModel role) {
		System.out.println("############################preRemove3##################################" + repository);
	}

	@Override
	public void close() {
		System.out.println(
				"############################close##############################################################close##############################################################close##############################################################close##############################################################close##############################################################close##############################################################close##############################################################close##############################################################close##############################################################close##############################################################close##################################"
						+ repository);
	}

	@Override
	public UserModel getUserById(String id, RealmModel realm) {
		System.out.println("############################getUserById##################################" + repository);

		String externalId = StorageId.externalId(id);
		return new UserAdapter(session, realm, model, repository.findUserById(externalId));
	}

	@Override
	public UserModel getUserByUsername(String username, RealmModel realm) {
		System.out
				.println("############################getUserByUsername##################################" + username);

		// Hier baue ich die Abfrage ÃƒÆ’Ã†â€™Ãƒâ€ Ã¢â‚¬â„¢ÃƒÆ’Ã¢â‚¬Â ÃƒÂ¢Ã¢â€šÂ¬Ã¢â€žÂ¢ÃƒÆ’Ã†â€™ÃƒÂ¢Ã¢â€šÂ¬Ã‚Â ÃƒÆ’Ã‚Â¢ÃƒÂ¢Ã¢â‚¬Å¡Ã‚Â¬ÃƒÂ¢Ã¢â‚¬Å¾Ã‚Â¢ÃƒÆ’Ã†â€™Ãƒâ€ Ã¢â‚¬â„¢ÃƒÆ’Ã‚Â¢ÃƒÂ¢Ã¢â‚¬Å¡Ã‚Â¬Ãƒâ€¦Ã‚Â¡ÃƒÆ’Ã†â€™ÃƒÂ¢Ã¢â€šÂ¬Ã…Â¡ÃƒÆ’Ã¢â‚¬Å¡Ãƒâ€šÃ‚Â¼ber den HIT rein!!!!!

		FedUser user = repository.findUserByUsernameOrEmail(username);

		// Zugriff auf Webservice
		// HoleUserWennVorhanden(username);

// diee Abfrage hat mich 2 Tage meines Lebens gekostet!!!!
		if (user != null) {

			return new UserAdapter(session, realm, model, user);
		} else {
			return null;
		}

	}

	@Override
	public UserModel getUserByEmail(String email, RealmModel realm) {
		System.out.println("############################getUserByEmail##################################" + repository);

		return getUserByUsername(email, realm);
	}

}
