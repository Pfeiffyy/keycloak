package com.keycoak.userfed;

import org.keycloak.Config;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.provider.ProviderConfigProperty;
import org.keycloak.provider.ProviderConfigurationBuilder;
import org.keycloak.storage.UserStorageProviderFactory;

import java.util.List;


public class FedUserStorageProviderFactory implements UserStorageProviderFactory<FedUserStorageProvider> {

	  @Override
	  public void init(Config.Scope config) {

	    // this configuration is pulled from the SPI configuration of this provider in the standalone[-ha] / domain.xml
	    // see setup.cli
		  System.out.println("##########################FedUserStorageProviderFactory###############################");

	  }
	
	
	@Override
	public FedUserStorageProvider create(KeycloakSession session, ComponentModel model) {
		// here you can setup the user storage provider, initiate some connections, etc.

		//FedRepository repository = new FedRepository();

		return new FedUserStorageProvider(session, model);
	}

	@Override
	public String getId() {
		return "fed-user-provider";
	}

	@Override
	public List<ProviderConfigProperty> getConfigProperties() {
		return ProviderConfigurationBuilder.create().property("myParam", "My Param", "Some Description",
				ProviderConfigProperty.STRING_TYPE, "some value", null).build();
	}
}
