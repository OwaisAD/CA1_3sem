package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@NamedQuery(name = "Phone.deleteAllRows", query = "DELETE from Phone")
@Table(name = "PHONE")
public class Phone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 45)
    @NotNull
    @Column(name = "number", nullable = false, length = 45)
    private String number;

    @Size(max = 45)
    @Column(name = "description", length = 45)
    private String description;

    @NotNull
    @Column(name = "isPrivate", nullable = false)
    private boolean isPrivate;

    public Phone() {
    }

    public Phone(String number) {
        this.number = number;
    }

    public Phone(Integer id, String number, String description, boolean isPrivate) {
        this.id = id;
        this.number = number;
        this.description = description;
        this.isPrivate = isPrivate;
    }

    public Phone(String number, String description, boolean isPrivate) {
        this.number = number;
        this.description = description;
        this.isPrivate = isPrivate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public boolean getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Phone)) return false;
        Phone phone = (Phone) o;
        return getId().equals(phone.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Phone{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", description='" + description + '\'' +
                ", isPrivate=" + isPrivate +
                '}';
    }
}