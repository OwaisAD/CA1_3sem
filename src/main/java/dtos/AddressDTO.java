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
}
