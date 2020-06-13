package com.pf.keycloak.eventhandler;

import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.admin.AdminEvent;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.RealmProvider;
import org.keycloak.models.UserModel;
import org.keycloak.email.EmailException;

import java.util.Map;

public class EventListenerRegisterProvider implements EventListenerProvider {

	private final KeycloakSession session;
	private final RealmProvider model;
	//dies dann evtl. extern hosten
	public static String betreff = "Erstregistrierung bei ISABEL";
	public static String bodytext = "Sehr geehrte Fachseite, \r\n ein neuer User hat sich für ISABEL registriert. \r\n Userdaten: \r\n";
	public static String fachEmail = "post@pfeifferdirk.de";

	public EventListenerRegisterProvider(KeycloakSession session) {
		this.session = session;
		this.model = session.realms();
	}

	@Override
	public void onEvent(Event event) {

		System.out.println(
				"--------------------------------------------------------Event:" + event.getType().name() + "<<<");
		// Bei einer neuregistrierung eine mail an das Funktionspostfach verschicken
		if (event.getType().name().equalsIgnoreCase("REGISTER")) {

			RealmModel realm = this.model.getRealm(event.getRealmId());
			UserModel user = this.session.users().getUserById(event.getUserId(), realm);
			if (user != null) {
				String userInfo = "Email: " + user.getEmail() + "\r\n" + "Username: " + user.getUsername();

				user.setEmail(fachEmail);

				org.keycloak.email.DefaultEmailSenderProvider senderProvider = new org.keycloak.email.DefaultEmailSenderProvider(
						session);
				try {
					senderProvider.send(session.getContext().getRealm().getSmtpConfig(), user, betreff,
							bodytext + userInfo, bodytext + userInfo);

				} catch (EmailException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
//		System.out.println(
//				"***************************************************************************************************************");
//		System.out.println("********************Event Occurred:" + toString(event));

	}

	@Override
	public void onEvent(AdminEvent adminEvent, boolean b) {
	}

	@Override
	public void close() {

	}

	private String toString(Event event) {

		StringBuilder sb = new StringBuilder();

		sb.append("type=");

		sb.append(event.getType());

		sb.append(", realmId=");

		sb.append(event.getRealmId());

		sb.append(", clientId=");

		sb.append(event.getClientId());

		sb.append(", userId=");

		sb.append(event.getUserId());

		sb.append(", ipAddress=");

		sb.append(event.getIpAddress());

		if (event.getError() != null) {

			sb.append(", error=");

			sb.append(event.getError());

		}

		if (event.getDetails() != null) {

			for (Map.Entry<String, String> e : event.getDetails().entrySet()) {

				sb.append(", ");

				sb.append(e.getKey());

				if (e.getValue() == null || e.getValue().indexOf(' ') == -1) {

					sb.append("=");

					sb.append(e.getValue());

				} else {

					sb.append("='");

					sb.append(e.getValue());

					sb.append("'");

				}

			}

		}

		return sb.toString();

	}

	private String toString(AdminEvent adminEvent) {

		StringBuilder sb = new StringBuilder();

		sb.append("operationType=");

		sb.append(adminEvent.getOperationType());

		sb.append(", realmId=");

		sb.append(adminEvent.getAuthDetails().getRealmId());

		sb.append(", clientId=");

		sb.append(adminEvent.getAuthDetails().getClientId());

		sb.append(", userId=");

		sb.append(adminEvent.getAuthDetails().getUserId());

		sb.append(", ipAddress=");

		sb.append(adminEvent.getAuthDetails().getIpAddress());

		sb.append(", resourcePath=");

		sb.append(adminEvent.getResourcePath());

		if (adminEvent.getError() != null) {

			sb.append(", error=");

			sb.append(adminEvent.getError());

		}

		return sb.toString();

	}
}