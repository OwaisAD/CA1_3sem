package dtos;

import entities.Hobby;
import entities.Person;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A DTO for the {@link entities.Hobby} entity
 */
public class HobbyDTO implements Serializable {
    private Integer id;
    @Size(max = 255)
    private final String wikiLink;
    @Size(max = 45)
    @NotNull
    private final String name;
    @Size(max = 45)
    @NotNull
    private final String category;
    @Size(max = 45)
    @NotNull
    private final String type;
    @Size(max = 355)
    private final String description;
    private final List<PersonInnerDTO> people = new ArrayList<>();

    public HobbyDTO(Integer id, String wikiLink, String name, String category, String type, String description) {
        this.id = id;
        this.wikiLink = wikiLink;
        this.name = name;
        this.category = category;
        this.type = type;
        this.description = description;
    }

    public HobbyDTO(String wikiLink, String name, String category, String type, String description) {
        this.wikiLink = wikiLink;
        this.name = name;
        this.category = category;
        this.type = type;
        this.description = description;
    }

    public HobbyDTO(Hobby hobby) {
        if(hobby.getId() != null) {
            this.id = hobby.getId();
        }
        this.wikiLink = hobby.getWikiLink();
        this.name = hobby.getName();
        this.category = hobby.getCategory();
        this.type = hobby.getType();
        this.description = hobby.getDescription();

        hobby.getPeople().forEach(person -> {
            PersonInnerDTO personInnerDTO = new PersonInnerDTO(
                    person.getId(),
                    person.getEmail(),
                    person.getFirstName(),
                    person.getLastName()
            );

            people.add(personInnerDTO);
        });
    }

    public static List<HobbyDTO> getDTOs(List<Hobby> hobbies) {
        List<HobbyDTO> hobbyDTOS = new ArrayList<>();
        hobbies.forEach(hobby -> {
            hobbyDTOS.add(new HobbyDTO(hobby));
        });
        return hobbyDTOS;
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

    public List<PersonInnerDTO> getPeople() {
        return people;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "wikiLink = " + wikiLink + ", " +
                "name = " + name + ", " +
                "category = " + category + ", " +
                "type = " + type + ", " +
                "description = " + description + ", " +
                "people = " + people + ")";
    }

    /**
     * A DTO for the {@link entities.Person} entity
     */
    public static class PersonInnerDTO implements Serializable {
        private final Integer id;
        @Size(max = 45)
        @NotNull
        private final String email;
        @Size(max = 45)
        @NotNull
        private final String firstName;
        @Size(max = 45)
        @NotNull
        private final String lastName;

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