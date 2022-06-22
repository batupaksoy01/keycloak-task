package org.example;

import org.example.model.AddressEntity;
import org.keycloak.connections.jpa.entityprovider.JpaEntityProvider;

import java.util.Collections;
import java.util.List;

public class AddressJpaEntityProvider implements JpaEntityProvider
{
    @Override
    public List<Class<?>> getEntities()
    {
        return Collections.singletonList(AddressEntity.class);
    }

    @Override
    public String getChangelogLocation()
    {
        return "META-INF/changelog.xml";
    }

    @Override
    public String getFactoryId()
    {
        return AddressJpaEntityProviderFactory.ID;
    }

    @Override
    public void close()
    {

    }
}
