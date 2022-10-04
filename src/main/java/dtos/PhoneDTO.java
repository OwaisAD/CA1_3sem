package dtos;

import entities.Phone;

public class PhoneDTO {

    private int id;
    private String number;
    private String description;
    private boolean isPrivate;

    public PhoneDTO(Phone phone) {
        this.id = phone.getId();
        this.number = phone.getNumber();
        this.description = phone.getDescription();
        this.isPrivate = phone.getIsPrivate();
    }

    public PhoneDTO(String number, String description, boolean isPrivate) {
        this.number = number;
        this.description = description;
        this.isPrivate = isPrivate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    @Override
    public String toString() {
        return "PhoneDTO{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", description='" + description + '\'' +
                ", isPrivate=" + isPrivate +
                '}';
    }
}
