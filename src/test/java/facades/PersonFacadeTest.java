package facades;

import dtos.AddressDTO;
import dtos.PersonDTO;
import entities.*;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.LinkedHashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class PersonFacadeTest {

    private static EntityManagerFactory emf;
    private static PersonFacade facade;

    private static HobbyFacade hobbyFacade;


    CityInfo c1 = new CityInfo(2800, "Kongens Lyngby", new LinkedHashSet<>());
    CityInfo c2 = new CityInfo(3000, "Helsingør", new LinkedHashSet<>());

    Phone phone1 = new Phone("12345678", "Telenor", false);
    Phone phone2 = new Phone("24682468", "CBB", false);

    Address a1 = new Address(new AddressDTO("Sushi Blv", "2tv", false, c1));

    Address a2 = new Address(new AddressDTO("Kanalvej", "5a", false, c2));

    Hobby h1 = new Hobby("https://en.wikipedia.org/wiki/3D_printing", "3D-udskrivning", "Generel", "Indendørs", "Flot hobby bla");

    Hobby h2 = new Hobby("https://en.wikipedia.org/wiki/Acrobatics", "Akrobatik", "Generel", "Indendørs", "Fed hobby");

    Person person = new Person("thomas@mail.dk", "Thomas", "Fritzbøger", phone1, a1);
    public PersonFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
       emf = EMF_Creator.createEntityManagerFactoryForTest();
       facade = PersonFacade.getPersonFacade(emf);
       hobbyFacade = HobbyFacade.getHobbyFacade(emf);

    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the code below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("CityInfo.deleteAllRows").executeUpdate();
            em.createNamedQuery("Address.deleteAllRows").executeUpdate();
            em.createNamedQuery("Phone.deleteAllRows").executeUpdate();
            em.createNamedQuery("Hobby.deleteAllRows").executeUpdate();
            em.persist(c1);
            em.persist(c2);

            em.persist(phone1);
            em.persist(phone2);

            em.persist(h1);
            em.persist(h2);

            em.persist(a1);
            em.persist(a2);

            em.persist(person);
            em.getTransaction().commit();
        } finally {
            em.close();
        }

    }

    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }

    @Test
    public void testCreatingAPerson() throws Exception {
        Person person = facade.createPerson(new Person("thomas@mail.dk", "Thomas", "Fritzbøger", phone1, a1));
        assertEquals("Thomas", person.getFirstName());
        assertEquals("Sushi Blv", person.getAddress().getStreet());
        System.out.println(person);
    }


    @Test
    public void testAddingAHobbyToAPerson() throws Exception {
        Person newPerson = facade.addHobbyToPerson(person, h1);
        assertEquals(1, newPerson.getHobbies().size());

    }



}
