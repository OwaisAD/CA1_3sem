package facades;

import dtos.AddressDTO;
import dtos.PersonDTO;
import entities.Address;
import entities.CityInfo;
import entities.Person;
import entities.Phone;
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

    public PersonFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
       emf = EMF_Creator.createEntityManagerFactoryForTest();
       facade = PersonFacade.getPersonFacade(emf);

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
            em.persist(new CityInfo(2800, "Kongens Lyngby", new LinkedHashSet<>()));
            em.persist(new CityInfo(3000, "Helsingør", new LinkedHashSet<>()));
            em.persist(new CityInfo(2980, "Kokkedal", new LinkedHashSet<>()));

            em.persist(new Phone("12345678", "Telenor", false));
            em.persist(new Phone("24682468", "CBB", false));
            em.persist(new Phone("98765432", "Telia", false));


            em.getTransaction().commit();
        } finally {
            em.close();
        }

        EntityManager em2 = emf.createEntityManager();
        try {
            em2.getTransaction().begin();

            em2.persist(new Address(new AddressDTO("Sushi Blv", "2tv", false, 2800)));
            em2.persist(new Address(new AddressDTO("Kanalvej", "5a", false, 2800)));
            em2.persist(new Address(new AddressDTO("Redde Allé", "78", false, 2980)));

            em2.getTransaction().commit();
        } finally {
            em2.close();
        }
    }

    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }

    @Test
    public void testCreatingAPerson() throws Exception {
        Person person = facade.createPerson(new Person("thomas@mail.dk", "Thomas", "Fritzbøger", "12345678", 1));
        assertEquals("Thomas", person.getFirstName());
        assertEquals("Sushi Blv", person.getAddress().getStreet());
    }







}
