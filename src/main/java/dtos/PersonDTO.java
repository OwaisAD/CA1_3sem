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
public class PersonDTO {
    private Integer id;
    private String email;
    private String firstName;
    private String lastName;
    private Phone phone;
    private Address address;
    private Set<Hobby> hobbies;


    public PersonDTO(String email, String firstName, String lastName, Phone phone, Address address, Set<Hobby> hobbies) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.address = address;
        this.hobbies = hobbies;
    }

    public PersonDTO(Person person) {
        if(person.getId() != null) {
            this.id = person.getId();
        }
        this.email = person.getEmail();
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.phone = person.getPhone();
        this.address = person.getAddress();
        this.hobbies = person.getHobbies();
    }

    public static List<PersonDTO> getDtos(List<Person> personList){
        List<PersonDTO> persondtos = new ArrayList();
        personList.forEach(person->persondtos.add(new PersonDTO(person)));
        return persondtos;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Phone getPhone() {
        return phone;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Set<Hobby> getHobbies() {
        return hobbies;
    }

    public void setHobbies(Set<Hobby> hobbies) {
        this.hobbies = hobbies;
    }

    @Override
    public String toString() {
        return "PersonDTO{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone=" + phone +
                ", address=" + address +
                ", hobbies=" + hobbies +
                '}';
    }
}
