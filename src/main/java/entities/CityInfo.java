package entities;

import dtos.CityInfoDTO;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@NamedQuery(name = "CityInfo.deleteAllRows", query = "DELETE from CityInfo")
@Table(name = "CITYINFO")
public class CityInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "zipCode", nullable = false)
    private Integer zipCode;

    @Size(max = 45)
    @NotNull
    @Column(name = "cityName", nullable = false, length = 45)
    private String cityName;

    @OneToMany(mappedBy = "cityInfo")
    private Set<Address> addresses = new LinkedHashSet<>();

    public CityInfo() {
    }

    public CityInfo(Integer zipCode, String cityName, Set<Address> addresses) {
        this.zipCode = zipCode;
        this.cityName = cityName;
        this.addresses = addresses;
    }

    public CityInfo(Integer zipCode, String cityName) {
        this.zipCode = zipCode;
        this.cityName = cityName;
    }

    public CityInfo(Integer zipCode) {
        this.zipCode = zipCode;
    }

    public CityInfo(CityInfoDTO cityInfoDTO) {
        this.id = cityInfoDTO.getId();
        this.zipCode = cityInfoDTO.getZipCode();
        this.cityName = cityInfoDTO.getCityName();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getZipCode() {
        return zipCode;
    }

    public void setZipCode(Integer zipCode) {
        this.zipCode = zipCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CityInfo)) return false;
        CityInfo cityInfo = (CityInfo) o;
        return getId().equals(cityInfo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "CityInfo{" +
                "id=" + id +
                ", zipCode=" + zipCode +
                ", cityName='" + cityName + '\'' +
                '}';
    }
}