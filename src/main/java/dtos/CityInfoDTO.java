package dtos;

import entities.Address;
import entities.CityInfo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * A DTO for the {@link CityInfo} entity
 */
public class CityInfoDTO implements Serializable {
    private Integer id;
    @NotNull
    private final Integer zipCode;
    @Size(max = 45)
    @NotNull
    private final String cityName;
    private final List<AddressDto> addresses = new ArrayList<>();

    public CityInfoDTO(Integer id, Integer zipCode, String cityName) {
        this.id = id;
        this.zipCode = zipCode;
        this.cityName = cityName;
    }

    public CityInfoDTO(CityInfo cityInfo) {
        if(cityInfo.getId() != null) {
            this.id = cityInfo.getId();
        }
        this.zipCode = cityInfo.getZipCode();
        this.cityName = cityInfo.getCityName();

        cityInfo.getAddresses().forEach(address -> {
            AddressDto addressDto = new AddressDto(
                    address.getId(),
                    address.getStreet(),
                    address.getAdditionalInfo(),
                    address.getIsPrivate()
            );

            addresses.add(addressDto);
        });
    }

    public static List<CityInfoDTO> getDTOs(List<CityInfo> cityInfoList) {
        List<CityInfoDTO> cityInfoDTOS = new ArrayList<>();
        cityInfoList.forEach(cityInfo -> {
            cityInfoDTOS.add(new CityInfoDTO(cityInfo));
        });
        return cityInfoDTOS;
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

    public List<AddressDto> getAddresses() {
        return addresses;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "zipCode = " + zipCode + ", " +
                "cityName = " + cityName + ", " +
                "addresses = " + addresses + ")";
    }

    /**
     * A DTO for the {@link Address} entity
     */
    public static class AddressDto implements Serializable {
        private final Integer id;
        @Size(max = 45)
        @NotNull
        private final String street;
        @Size(max = 45)
        private final String additionalInfo;
        @NotNull
        private final boolean isPrivate;

        public AddressDto(Integer id, String street, String additionalInfo, boolean isPrivate) {
            this.id = id;
            this.street = street;
            this.additionalInfo = additionalInfo;
            this.isPrivate = isPrivate;
        }

        public Integer getId() {
            return id;
        }

        public String getStreet() {
            return street;
        }

        public String getAdditionalInfo() {
            return additionalInfo;
        }

        public boolean getIsPrivate() {
            return isPrivate;
        }

        @Override
        public String toString() {
            return getClass().getSimpleName() + "(" +
                    "id = " + id + ", " +
                    "street = " + street + ", " +
                    "additionalInfo = " + additionalInfo + ", " +
                    "isPrivate = " + isPrivate + ")";
        }
    }
}