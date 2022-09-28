/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entities.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author tha
 */
public class CityInfoDTO {
    private Integer id;
    private Integer zipCode;
    private String cityName;
    private Set<Address> addresses;

    public CityInfoDTO(Integer zipCode, String cityName, Set<Address> addresses) {
        this.zipCode = zipCode;
        this.cityName = cityName;
        this.addresses = addresses;
    }

    public CityInfoDTO(CityInfo cityInfo) {
        if(cityInfo.getId() != null) {
            this.id = cityInfo.getId();
        }
        this.zipCode = cityInfo.getZipCode();
        this.cityName = cityInfo.getCityName();
        this.addresses = cityInfo.getAddresses();
    }

    public static List<CityInfoDTO> getDtos(List<CityInfo> cityInfoList){
        List<CityInfoDTO> cityInfoDTOS = new ArrayList();
        cityInfoList.forEach(city->cityInfoDTOS.add(new CityInfoDTO(city)));
        return cityInfoDTOS;
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
    public String toString() {
        return "CityInfoDTO{" +
                "id=" + id +
                ", zipCode=" + zipCode +
                ", cityName='" + cityName + '\'' +
                '}' + "\n";
    }

/*    @Override
    public String toString() {
        return "CityInfoDTO{" +
                "id=" + id +
                ", zipCode=" + zipCode +
                ", cityName='" + cityName + '\'' +
                ", addresses=" + addresses +
                '}';
    }*/
}
