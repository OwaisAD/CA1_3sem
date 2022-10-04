package entities;

import dtos.AddressDTO;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@NamedQuery(name = "Address.deleteAllRows", query = "DELETE from Address")
@Table(name = "ADDRESS")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 45)
    @NotNull
    @Column(name = "street", nullable = false, length = 45)
    private String street;

    @Size(max = 45)
    @Column(name = "additionalInfo", length = 45)
    private String additionalInfo = "";

    @NotNull
    @Column(name = "isPrivate", nullable = false)
    private boolean isPrivate;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CITYINFO_id", nullable = false)
    private CityInfo cityInfo;

    @OneToMany(mappedBy = "address")
    private Set<Person> people = new LinkedHashSet<>();

    public Address() {
    }


    public Address(AddressDTO addressDTO) {
        this.street = addressDTO.getStreet();
        this.additionalInfo = addressDTO.getAdditionalInfo();
        this.isPrivate = addressDTO.isPrivate();
        this.cityInfo = addressDTO.getCityInfo();
    }

    public Address(String street, String additionalInfo, boolean isPrivate, CityInfo cityInfo) {
        this.street = street;
        this.additionalInfo = additionalInfo;
        this.isPrivate = isPrivate;
        this.cityInfo = cityInfo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public boolean getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public CityInfo getCityInfo() {
        return cityInfo;
    }

    public void setCityInfo(CityInfo cityInfo) {
        this.cityInfo = cityInfo;
    }

    public Set<Person> getPeople() {
        return people;
    }

    public void setPeople(Set<Person> people) {
        this.people = people;
    }

    public Integer getCityZip() {
        return cityInfo.getZipCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;
        Address address = (Address) o;
        return getId().equals(address.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", street='" + street + '\'' +
                ", additionalInfo='" + additionalInfo + '\'' +
                ", isPrivate=" + isPrivate +
                ", cityInfo=" + cityInfo +
                ", people=" + people +
                '}';
    }
}