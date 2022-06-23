package org.example.model;

import lombok.Getter;
import lombok.Setter;
import org.keycloak.models.jpa.entities.UserEntity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "ADDRESS_ENTITY", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "REALM_ID", "CITY", "COUNTRY", "ZIP_CODE", "ADDRESS_LINE" })
})
@NamedQueries({
        @NamedQuery(name = "findAddress", query = "SELECT b FROM AddressEntity b WHERE b.realmId = :realmId AND " +
                "b.city = :city AND b.country = :country AND b.zipCode = :zipCode AND b.addressLine = :addressLine")
})
@Getter
@Setter
public class AddressEntity implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID")
    @Access(AccessType.PROPERTY)
    private String id;

    @Column(name = "REALM_ID", nullable = false)
    private String realmId;

    @OneToMany(cascade = CascadeType.REMOVE)
    private Collection<UserEntity> users = new ArrayList<>();

    @Column(name = "CITY")
    private String city;
    @Column(name = "COUNTRY")
    private String country;
    @Column(name = "ZIP_CODE")
    private String zipCode;
    @Column(name = "ADDRESS_LINE")
    private String addressLine;

    @Override
    public boolean equals(Object o)
    {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        AddressEntity that = (AddressEntity) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id);
    }

    @Override
    public String toString()
    {
        return "AddressEntity{" +
                "id='" + id + '\'' +
                ", realmId='" + realmId + '\'' +
                ", users=" + users +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", addressLine='" + addressLine + '\'' +
                '}';
    }
}
