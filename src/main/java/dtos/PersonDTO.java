package dtos;

import entities.Hobby;
import entities.Person;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * A DTO for the {@link entities.Person} entity
 */
public class PersonDTO implements Serializable {
    private Integer id;
    @Size(max = 45)
    @NotNull
    private final String email;
    @Size(max = 45)
    @NotNull
    private final String firstName;
    @Size(max = 45)
    @NotNull
    private final String lastName;
    private final List<HobbyInnerDTO> hobbies = new ArrayList<>();

    public PersonDTO(Integer id, String email, String firstName, String lastName) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public PersonDTO(String email, String firstName, String lastName) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public PersonDTO(Person person) {
        if(person.getId() != null) {
            this.id = person.getId();
        }
        this.email = person.getEmail();
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        person.getHobbies().forEach(hobby -> {
            hobbies.add(new HobbyInnerDTO(hobby));
        });
    }

    public static List<PersonDTO> getDtos(List<Person> persons) {
        List<PersonDTO> personDTOS = new ArrayList<>();
        persons.forEach(person -> {
            personDTOS.add(new PersonDTO(person));
        });
        return personDTOS;
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

    public List<HobbyInnerDTO> getHobbies() {
        return hobbies;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "email = " + email + ", " +
                "firstName = " + firstName + ", " +
                "lastName = " + lastName + ", " +
                "hobbies = " + hobbies + ")";
    }

    /**
     * A DTO for the {@link entities.Hobby} entity
     */
    public static class HobbyInnerDTO implements Serializable {
        private final Integer id;
        private final String wikiLink;
        private final String name;
        private final String category;
        private final String type;
        private final String description;

        public HobbyInnerDTO(Integer id, String wikiLink, String name, String category, String type, String description) {
            this.id = id;
            this.wikiLink = wikiLink;
            this.name = name;
            this.category = category;
            this.type = type;
            this.description = description;
        }

        public HobbyInnerDTO(Hobby hobby) {
            this.id = hobby.getId();
            this.wikiLink = hobby.getWikiLink();
            this.name = hobby.getName();
            this.category = hobby.getCategory();
            this.type = hobby.getType();
            this.description = hobby.getDescription();
        }

        public Integer getId() {
            return id;
        }

        public String getWikiLink() {
            return wikiLink;
        }

        public String getName() {
            return name;
        }

        public String getCategory() {
            return category;
        }

        public String getType() {
            return type;
        }

        public String getDescription() {
            return description;
        }

        @Override
        public String toString() {
            return getClass().getSimpleName() + "(" +
                    "id = " + id + ", " +
                    "wikiLink = " + wikiLink + ", " +
                    "name = " + name + ", " +
                    "category = " + category + ", " +
                    "type = " + type + ", " +
                    "description = " + description + ")";
        }
    }
}