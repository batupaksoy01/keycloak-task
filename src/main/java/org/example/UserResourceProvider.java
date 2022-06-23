package org.example;

import lombok.extern.slf4j.Slf4j;
import org.example.mapper.AddressMapper;
import org.example.model.AddressEntity;
import org.example.model.AddressInputDto;
import org.jboss.resteasy.annotations.cache.NoCache;
import org.keycloak.connections.jpa.JpaConnectionProvider;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.jpa.entities.UserEntity;
import org.keycloak.models.utils.KeycloakModelUtils;
import org.keycloak.services.managers.AppAuthManager;
import org.keycloak.services.managers.AuthenticationManager;
import org.keycloak.services.resource.RealmResourceProvider;
import org.keycloak.utils.MediaType;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.POST;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.List;

@Slf4j
public class UserResourceProvider implements RealmResourceProvider
{
    private final KeycloakSession session;

    public UserResourceProvider(KeycloakSession session) {
        this.session = session;
    }

    @Override
    public Object getResource()
    {
        return this;
    }

    @Override
    public void close()
    {

    }

    @Path("bulk")
    @POST
    @NoCache
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUserAddress(List<AddressInputDto> addresses) {
        checkAuth();

        RealmModel realmModel = session.getContext().getRealm();
        EntityManager entityManager = session.getProvider(JpaConnectionProvider.class).getEntityManager();

        for (AddressInputDto address: addresses) {
            AddressEntity addressEntity = findAddressEntity(realmModel, entityManager, address);
            if (addressEntity == null) {
                addressEntity = AddressMapper.INSTANCE.addressDtoToAddressEntity(address);
                addressEntity.setId(KeycloakModelUtils.generateId());
                addressEntity.setRealmId(realmModel.getId());

                entityManager.persist(addressEntity);
                log.info("New address created with id:{}", addressEntity.getId());
            }
            else { log.info("Address already exists with id:{}", addressEntity.getId()); }

            for (String id : address.getUserIds()) {
                UserEntity userEntity = entityManager.find(UserEntity.class, id);
                if (userEntity != null) {
                    if (!addressEntity.getUsers().contains(userEntity)) {
                        addressEntity.getUsers().add(userEntity);
                        log.info("Registering user with id:{} to address with id: {}", id, addressEntity.getId());
                    }
                    else { log.info("User with id:{}, already registered to address with id: {}", id, addressEntity.getId()); }
                }
                else { log.info("No user found with id:{}", id); }
            }

            entityManager.flush();
        }

        return Response.noContent().build();
    }

    @Path("bulk/{ids}")
    @DELETE
    @NoCache
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUsers(@PathParam("ids") String ids) {
        checkAuth();

        RealmModel realmModel = session.getContext().getRealm();

        String[] idList = ids.split(",");

        for (String id: idList)
        {
            UserModel userModel = session.users().getUserById(realmModel, id);
            if  (userModel != null) {
                log.info("Removed user with id:{}", id);
                session.users().removeUser(realmModel, userModel);
            }
            else {
                log.info("No user found with id:{}", id);
            }
        }

        return Response.noContent().build();
    }

    private void checkAuth() {
        AuthenticationManager.AuthResult authResult = new AppAuthManager.BearerTokenAuthenticator(session).authenticate();
        if (authResult == null) {
            throw new NotAuthorizedException("Bearer is missing.");
        }
        else if (authResult.getToken().getIssuedFor() == null) {
            throw new ForbiddenException("Token is not properly issued for client.");
        }
    }

    private AddressEntity findAddressEntity(RealmModel realmModel, EntityManager entityManager, AddressInputDto address) {
        TypedQuery<AddressEntity> query = entityManager.createNamedQuery("findAddress", AddressEntity.class);
        query.setParameter("realmId", realmModel.getId());
        query.setParameter("city", address.getCity());
        query.setParameter("country", address.getCountry());
        query.setParameter("zipCode", address.getZipCode());
        query.setParameter("addressLine", address.getAddressLine());

        try
        {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
