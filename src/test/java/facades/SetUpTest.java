package facades;

import dtos.AddressDTO;
import entities.*;

import javax.persistence.EntityManager;
import java.util.LinkedHashSet;

public class SetUpTest {

    private CityInfo cityInfo1 = new CityInfo(2800, "Kongens Lyngby", new LinkedHashSet<>());
    private CityInfo cityInfo2 = new CityInfo(3000, "Helsingør", new LinkedHashSet<>());
    private Phone phone1 = new Phone("12345678", "Telenor", false);
    private Phone phone2 = new Phone("24682468", "CBB", false);
    private Address address1 = new Address(new AddressDTO("Sushi Blv", "2tv", false, cityInfo1));
    private Address address2 = new Address(new AddressDTO("Kanalvej", "5a", false, cityInfo2));
    private Hobby hobby1 = new Hobby("https://en.wikipedia.org/wiki/3D_printing", "3D-udskrivning", "Generel", "Indendørs", "Flot hobby bla");
    private Hobby hobby2 = new Hobby("https://en.wikipedia.org/wiki/Acrobatics", "Akrobatik", "Generel", "Indendørs", "Fed hobby");
    private Person person = new Person("thomas@mail.dk", "Thomas", "Fritzbøger", phone1, address1);
    private Person person2 = new Person("daniel@mail.dk", "Daniel", "Drobek", phone2, address1);


    public SetUpTest() {
    }

    public void setUp(EntityManager em) {

        try {
            em.getTransaction().begin();
            em.createNamedQuery("Person.deleteAllRows").executeUpdate();
            em.createNamedQuery("Address.deleteAllRows").executeUpdate();
            em.createNamedQuery("CityInfo.deleteAllRows").executeUpdate();
            em.createNamedQuery("Phone.deleteAllRows").executeUpdate();
            em.createNamedQuery("Hobby.deleteAllRows").executeUpdate();
            em.persist(cityInfo1);
            em.persist(cityInfo2);

            em.persist(phone1);
            em.persist(phone2);

            em.persist(hobby1);
            em.persist(hobby2);

            em.persist(address1);
            em.persist(address2);

            em.persist(person);
            em.persist(person2);
            em.getTransaction().commit();
        } finally {
            em.close();
        }

    }

    public CityInfo getCityInfo1() {
        return cityInfo1;
    }

    public CityInfo getCityInfo2() {
        return cityInfo2;
    }

    public Phone getPhone1() {
        return phone1;
    }

    public Phone getPhone2() {
        return phone2;
    }

    public Address getAddress1() {
        return address1;
    }

    public Address getAddress2() {
        return address2;
    }

    public Hobby getHobby1() {
        return hobby1;
    }

    public Hobby getHobby2() {
        return hobby2;
    }

    public Person getPerson() {
        return person;
    }

    public Person getPerson2() {
        return person2;
    }
}

