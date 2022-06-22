package org.example;

import org.keycloak.Config;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ProviderConfigProperty;
import org.keycloak.services.resource.RealmResourceProvider;
import org.keycloak.services.resource.RealmResourceProviderFactory;

import java.util.List;

public class UserResourceProviderFactory implements RealmResourceProviderFactory
{

    @Override
    public RealmResourceProvider create(KeycloakSession keycloakSession)
    {
        return new UserResourceProvider(keycloakSession);
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
        return "users-custom";
    }

    @Override
    public int order()
    {
        return RealmResourceProviderFactory.super.order();
    }

    @Override
    public List<ProviderConfigProperty> getConfigMetadata()
    {
        return RealmResourceProviderFactory.super.getConfigMetadata();
    }
}
