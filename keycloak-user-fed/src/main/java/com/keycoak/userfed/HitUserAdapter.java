package com.keycoak.userfed;

import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.storage.StorageId;
import org.keycloak.storage.adapter.AbstractUserAdapterFederatedStorage;


public class HitUserAdapter extends AbstractUserAdapterFederatedStorage {

    private final HitUser user;
    private final String keycloakId;

    public HitUserAdapter(KeycloakSession session, RealmModel realm, ComponentModel model, HitUser user) {
        super(session, realm, model);
        this.user = user;
        System.out.println("******************************************************************************************");
        System.out.println("realm.getName() : " + realm.getName()+ " />>>>>>>>> " + user);

        System.out.println( StorageId.keycloakId(model, user.getId()));
        
        this.keycloakId = StorageId.keycloakId(model, user.getId());
    }

    @Override
    public String getId() {
        return keycloakId;
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public void setUsername(String username) {
        user.setUsername(username);
    }

    @Override
    public String getEmail() {
        return user.getEmail();
    }

    @Override
    public void setEmail(String email) {
        user.setEmail(email);
    }

    @Override
    public String getFirstName() {
        return user.getFirstName();
    }

    @Override
    public void setFirstName(String firstName) {
        user.setFirstName(firstName);
    }

    @Override
    public String getLastName() {
        return user.getLastName();
    }

    @Override
    public void setLastName(String lastName) {
        user.setLastName(lastName);
    }
}
