package entities;

import dtos.HobbyDTO;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

@Entity
@NamedQuery(name = "Hobby.deleteAllRows", query = "DELETE from Hobby")
@Table(name = "HOBBY")
public class Hobby {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @Column(name = "wikiLink")
    private String wikiLink;

    @Size(max = 45)
    @NotNull
    @Column(name = "name", nullable = false, length = 45)
    private String name;

    @Size(max = 45)
    @NotNull
    @Column(name = "category", nullable = false, length = 45)
    private String category;

    @Size(max = 45)
    @NotNull
    @Column(name = "type", nullable = false, length = 45)
    private String type;

    @Size(max = 355)
    @Column(name = "description", length = 355)
    private String description;

    @ManyToMany
    @JoinTable(name = "HOBBY_has_PERSON",
            joinColumns = @JoinColumn(name = "HOBBY_id"),
            inverseJoinColumns = @JoinColumn(name = "PERSON_id"))
    private List<Person> people = new ArrayList<>();

    public Hobby() {
    }

    public Hobby(String wikiLink, String name, String category, String type, String description) {
        this.wikiLink = wikiLink;
        this.name = name;
        this.category = category;
        this.type = type;
        this.description = description;
    }

    public Hobby(HobbyDTO hobbyDTO) {
        this.wikiLink = hobbyDTO.getWikiLink();
        this.name = hobbyDTO.getName();
        this.category = hobbyDTO.getCategory();
        this.type = hobbyDTO.getType();
        this.description = hobbyDTO.getDescription();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWikiLink() {
        return wikiLink;
    }

    public void setWikiLink(String wikiLink) {
        this.wikiLink = wikiLink;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Person> getPeople() {
        return people;
    }

    public void setPeople(List<Person> people) {
        this.people = people;
    }

    public void addPerson(Person person) {
        this.people.add(person);
        if(!person.getHobbies().contains(this)) { //what is "this"
            person.addHobbies(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Hobby)) return false;
        Hobby hobby = (Hobby) o;
        return getId().equals(hobby.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Hobby{" +
                "id=" + id +
                ", wikiLink='" + wikiLink + '\'' +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", people=" + people +
                '}';
    }
}