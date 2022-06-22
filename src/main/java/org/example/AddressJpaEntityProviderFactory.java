package org.example;

import org.keycloak.Config;
import org.keycloak.connections.jpa.entityprovider.JpaEntityProvider;
import org.keycloak.connections.jpa.entityprovider.JpaEntityProviderFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

public class AddressJpaEntityProviderFactory implements JpaEntityProviderFactory
{
    protected static final String ID = "address-entity-provider";

    @Override
    public JpaEntityProvider create(KeycloakSession keycloakSession)
    {
        return new AddressJpaEntityProvider();
    }

    @Override
    public void init(Config.Scope scope)
    {

    }

    @Override
    public void postInit(KeycloakSessionFactory keycloakSessionFactory)
    {

    }

    @Override
    public void close()
    {

    }

    @Override
    public String getId()
    {
        return ID;
    }
}
