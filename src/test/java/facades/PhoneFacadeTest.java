package facades;

import dtos.AddressDTO;
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
public class PhoneFacadeTest {

    private static EntityManagerFactory emf;
    private static PhoneFacade facade;

    private CityInfo c1 = new CityInfo(2800, "Kongens Lyngby", new LinkedHashSet<>());
    private CityInfo c2 = new CityInfo(3000, "Helsingør", new LinkedHashSet<>());
    private Phone phone1 = new Phone("12345678", "Telenor", false);
    private Phone phone2 = new Phone("24682468", "CBB", false);
    private Address a1 = new Address(new AddressDTO("Sushi Blv", "2tv", false, c1));
    private Address a2 = new Address(new AddressDTO("Kanalvej", "5a", false, c2));
    private Hobby h1 = new Hobby("https://en.wikipedia.org/wiki/3D_printing", "3D-udskrivning", "Generel", "Indendørs", "Flot hobby bla");
    private Hobby h2 = new Hobby("https://en.wikipedia.org/wiki/Acrobatics", "Akrobatik", "Generel", "Indendørs", "Fed hobby");
    private Person person = new Person("thomas@mail.dk", "Thomas", "Fritzbøger", phone1, a1);
    private Person person2 = new Person("daniel@mail.dk", "Daniel", "Drobek", phone2, a1);


    public PhoneFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
       emf = EMF_Creator.createEntityManagerFactoryForTest();
       facade = PhoneFacade.getPhoneFacade(emf);

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
            //em.createNamedQuery("Phone.deleteAllRows").executeUpdate();
           // em.persist(new Phone("12345678", "Telenor", false));
           // em.persist(new Phone("24682468", "CBB", false));
           // em.persist(new Phone("98765432", "Telia", false));

            em.createNamedQuery("Person.deleteAllRows").executeUpdate();
            em.createNamedQuery("Address.deleteAllRows").executeUpdate();
            em.createNamedQuery("CityInfo.deleteAllRows").executeUpdate();
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
            em.persist(person2);

            em.getTransaction().commit();
        } finally {
            em.close();
        }

    }

    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }

    // TODO: Delete or change this method 
    @Test
    public void testCreatingAPhone() throws Exception {
        Phone phone = facade.createPhone(new Phone("11111111", "Telmore", false));
        assertEquals(3, facade.getAllPhones().size());
    }

    @Test
    public void testGetAllPhones() {
        List<Phone> phonesList = facade.getAllPhones();
        assertEquals(2, phonesList.size());
    }

    @Test
    public void getPhoneByPhoneNumber() {
        Phone phone = facade.getPhoneByPhoneNumber("24682468");
        assertEquals("CBB", phone.getDescription());
    }


}
