package com.pf.keycloak.eventhandler;

import org.keycloak.Config;
import org.keycloak.events.EventListenerProviderFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;



public class EventListenerRegisterProviderFactory implements EventListenerProviderFactory {


    @Override
    public EventListenerRegisterProvider create(KeycloakSession keycloakSession) {

        return new EventListenerRegisterProvider(keycloakSession);
    }

    @Override
    public void init(Config.Scope scope) {

    }

    @Override
    public void postInit(KeycloakSessionFactory keycloakSessionFactory) {

    }

    @Override
    public void close() {

    }

    @Override
    public String getId() {
        return "sample_event_listener";
    }
}