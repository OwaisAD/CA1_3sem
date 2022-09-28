/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entities.Address;
import entities.Hobby;
import entities.Person;
import entities.Phone;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author tha
 */
public class HobbyDTO {
    private Integer id;
    private String wikiLink;
    private String name;
    private String category;
    private String type;
    private String description;
    private Set<Person> people;


    public HobbyDTO(String wikiLink, String name, String category, String type, String description, Set<Person> people) {
        this.wikiLink = wikiLink;
        this.name = name;
        this.category = category;
        this.type = type;
        this.description = description;
        this.people = people;
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
        this.people = hobby.getPeople();
    }

    public static List<HobbyDTO> getDtos(List<Hobby> hobbyList){
        List<HobbyDTO> hobbyDTOS = new ArrayList();
        hobbyList.forEach(hobby->hobbyDTOS.add(new HobbyDTO(hobby)));
        return hobbyDTOS;
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

    public Set<Person> getPeople() {
        return people;
    }

    public void setPeople(Set<Person> people) {
        this.people = people;
    }

    @Override
    public String toString() {
        return "HobbyDTO{" +
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
