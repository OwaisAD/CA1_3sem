/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entities.Address;
import entities.CityInfo;
import entities.Hobby;
import entities.Person;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author tha
 */
public class AddressDTO {
    private Integer id;
    private String street;
    private String additionalInfo = "";
    private boolean isPrivate;
    private CityInfo cityInfo;

    private List<PersonInnerDTO> personInnerDTOS = new ArrayList<>();

    public AddressDTO(String street, String additionalInfo, boolean isPrivate, CityInfo cityInfo) {
        this.street = street;
        this.additionalInfo = additionalInfo;
        this.isPrivate = isPrivate;
        this.cityInfo = cityInfo;
    }

    public AddressDTO(Address address) {
        if(address.getId() != null) {
            this.id = address.getId();
        }
        this.street = address.getStreet();
        this.additionalInfo = address.getAdditionalInfo();
        this.isPrivate = address.getIsPrivate();
        this.cityInfo = address.getCityInfo();
    }

    public static List<AddressDTO> getDTOs(List<Address> addresses) {
        List<AddressDTO> addressDTOList = new ArrayList<>();
        addresses.forEach(address -> {
            addressDTOList.add(new AddressDTO(address));
        });
        return addressDTOList;
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

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public CityInfo getCityInfo() {
        return cityInfo;
    }

    public void setCityInfo(CityInfo cityInfo) {
        this.cityInfo = cityInfo;
    }

    public List<PersonInnerDTO> getPersonInnerDTOS() {
        return personInnerDTOS;
    }

    @Override
    public String toString() {
        return "AddressDTO{" +
                "id=" + id +
                ", street='" + street + '\'' +
                ", additionalInfo='" + additionalInfo + '\'' +
                ", isPrivate=" + isPrivate +
                ", CityInfo=" + cityInfo +
                '}';
    }

    /**
     * A DTO for the {@link entities.CityInfo} entity
     */
    public static class CityInfoInnerDTO implements Serializable {
        private Integer id;
        private Integer zipCode;
        private String cityName;

        public CityInfoInnerDTO(Integer id, Integer zipCode, String cityName) {
            this.id = id;
            this.zipCode = zipCode;
            this.cityName = cityName;
        }

        public Integer getId() {
            return id;
        }

        public Integer getZipCode() {
            return zipCode;
        }

        public String getCityName() {
            return cityName;
        }

        @Override
        public String toString() {
            return "CityInfoInnerDTO{" +
                    "id=" + id +
                    ", zipCode=" + zipCode +
                    ", cityName='" + cityName + '\'' +
                    '}';
        }
    }

    /**
     * A DTO for the {@link entities.Person} entity
     */
    public static class PersonInnerDTO implements Serializable {
        private Integer id;

        private String email;

        private String firstName;
        private String lastName;

        public PersonInnerDTO(Integer id, String email, String firstName, String lastName) {
            this.id = id;
            this.email = email;
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public Integer getId() {
            return id;
        }

        public String getEmail() {
            return email;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        @Override
        public String toString() {
            return getClass().getSimpleName() + "(" +
                    "id = " + id + ", " +
                    "email = " + email + ", " +
                    "firstName = " + firstName + ", " +
                    "lastName = " + lastName + ")";
        }
    }
}
