package com.webservice.handler;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

public class GitHub {

    public static void main(String[] args) {

        ClientConfig clientConfig = new ClientConfig();

        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(
                "e3528054ecae479fda90ab8322d0567121e29d6e", "x-oauth-basic");
        clientConfig.register(feature);

        Client client = ClientBuilder.newClient(clientConfig);
        WebTarget webTarget = client.target("https://api.github.com").path(
                "authorizations");

        String input = "{\"scopes\":[\"public_repo\"],\"note\":\"Ramanuj\"}";

        Response response = webTarget.request("application/json").post(
                Entity.json(input));
        System.out.println(response.readEntity(String.class));
//e3528054ecae479fda90ab8322d0567121e29d6e
    }
}