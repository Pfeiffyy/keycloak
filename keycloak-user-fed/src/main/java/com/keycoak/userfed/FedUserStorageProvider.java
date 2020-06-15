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

	public FedUserStorageProvider(KeycloakSession session, ComponentModel model) {
		System.out.println("############################DemoUserStorageProvider#####################################");

		this.session = session;
		this.model = model;
	}

	@Override
	public boolean supportsCredentialType(String credentialType) {
		System.out.println("############################supportsCredentialType##################################");

		return PasswordCredentialModel.TYPE.equals(credentialType);
	}

	@Override
	public boolean isConfiguredFor(RealmModel realm, UserModel user, String credentialType) {
		System.out.println("############################isConfiguredFor##################################");

		return supportsCredentialType(credentialType);
	}

	@Override
	public boolean isValid(RealmModel realm, UserModel user, CredentialInput input) {
		System.out.println("############################isValid##################################");

		if (!supportsCredentialType(input.getType()) || !(input instanceof UserCredentialModel)) {
			return false;
		}
		UserCredentialModel cred = (UserCredentialModel) input;
		// return repository.validateCredentials(user.getUsername(),
		// cred.getChallengeResponse());
		return true;
	}

	@Override
	public boolean updateCredential(RealmModel realm, UserModel user, CredentialInput input) {
		System.out.println("############################updateCredential##################################");

		if (!supportsCredentialType(input.getType()) || !(input instanceof UserCredentialModel)) {
			return false;
		}
		UserCredentialModel cred = (UserCredentialModel) input;
		// return repository.updateCredentials(user.getUsername(),
		// cred.getChallengeResponse());
		return true;
	}

	@Override
	public void disableCredentialType(RealmModel realm, UserModel user, String credentialType) {
		System.out.println("############################disableCredentialType##################################");

	}

	@Override
	public Set<String> getDisableableCredentialTypes(RealmModel realm, UserModel user) {
		System.out
				.println("############################getDisableableCredentialTypes##################################");

		return Collections.emptySet();
	}

	@Override
	public void preRemove(RealmModel realm) {
		System.out.println("############################preRemove##################################");

	}

	@Override
	public void preRemove(RealmModel realm, GroupModel group) {
		System.out.println("############################preRemove2##################################");

	}

	@Override
	public void preRemove(RealmModel realm, RoleModel role) {
		System.out.println("############################preRemove3##################################");
	}

	@Override
	public void close() {
		System.out.println(
				"############################close##############################################################close##############################################################close##############################################################close##############################################################close##############################################################close##############################################################close##############################################################close##############################################################close##############################################################close##############################################################close##################################");
	}

	@Override
	public UserModel getUserById(String id, RealmModel realm) {
		System.out.println("############################getUserById##################################");

		String externalId = StorageId.externalId(id);
		return new HitUserAdapter(session, realm, model, getUserfromHit(externalId));
	}

	@Override
	public UserModel getUserByUsername(String username, RealmModel realm) {
		System.out
				.println("############################getUserByUsername##################################" + username);

		// Hier baue ich die Abfrage
		// ÃƒÆ’Ã†â€™Ãƒâ€ Ã¢â‚¬â„¢ÃƒÆ’Ã¢â‚¬Â ÃƒÂ¢Ã¢â€šÂ¬Ã¢â€žÂ¢ÃƒÆ’Ã†â€™ÃƒÂ¢Ã¢â€šÂ¬Ã‚Â ÃƒÆ’Ã‚Â¢ÃƒÂ¢Ã¢â‚¬Å¡Ã‚Â¬ÃƒÂ¢Ã¢â‚¬Å¾Ã‚Â¢ÃƒÆ’Ã†â€™Ãƒâ€ Ã¢â‚¬â„¢ÃƒÆ’Ã‚Â¢ÃƒÂ¢Ã¢â‚¬Å¡Ã‚Â¬Ãƒâ€šÃ‚Â ÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â¢ÃƒÆ’Ã‚Â¢ÃƒÂ¢Ã¢â€šÂ¬Ã…Â¡Ãƒâ€šÃ‚Â¬ÃƒÆ’Ã‚Â¢ÃƒÂ¢Ã¢â€šÂ¬Ã…Â¾Ãƒâ€šÃ‚Â¢ÃƒÆ’Ã†â€™Ãƒâ€ Ã¢â‚¬â„¢ÃƒÆ’Ã¢â‚¬Â ÃƒÂ¢Ã¢â€šÂ¬Ã¢â€žÂ¢ÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â¢ÃƒÆ’Ã‚Â¢ÃƒÂ¢Ã¢â€šÂ¬Ã…Â¡Ãƒâ€šÃ‚Â¬ÃƒÆ’Ã¢â‚¬Â¦Ãƒâ€šÃ‚Â¡ÃƒÆ’Ã†â€™Ãƒâ€ Ã¢â‚¬â„¢ÃƒÆ’Ã‚Â¢ÃƒÂ¢Ã¢â‚¬Å¡Ã‚Â¬Ãƒâ€¦Ã‚Â¡ÃƒÆ’Ã†â€™ÃƒÂ¢Ã¢â€šÂ¬Ã…Â¡ÃƒÆ’Ã¢â‚¬Å¡Ãƒâ€šÃ‚Â¼ber
		// den HIT rein!!!!!

		// FedUser user = repository.findUserByUsernameOrEmail(username);

		HitUser user = getUserfromHit(username);

		// Zugriff auf Webservice
		// HoleUserWennVorhanden(username);

// diee Abfrage hat mich 2 Tage meines Lebens gekostet!!!!
		if (user != null) {

			return new HitUserAdapter(session, realm, model, user);
		} else {
			return null;
		}

	}

	private HitUser getUserfromHit(String username) {
		// TODO Auto-generated method stub
		HitUser hu = new HitUser();
		hu.setFirstName("Hans");
		hu.setLastName("Dampf");
		hu.setEmail("Hans@dampf.de");
		hu.setUsername(username);
		hu.setPassword(hu.getEmail());

		return null;
	}

	@Override
	public UserModel getUserByEmail(String email, RealmModel realm) {
		System.out.println("############################getUserByEmail##################################");

		return getUserByUsername(email, realm);
	}

}
