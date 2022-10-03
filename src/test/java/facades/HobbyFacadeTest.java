package facades;

import dtos.AddressDTO;
import dtos.HobbyDTO;
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
public class HobbyFacadeTest {

    private static EntityManagerFactory emf;
    private static HobbyFacade facade;

    private static PersonFacade personFacade;
    Hobby h1 = new Hobby("https://en.wikipedia.org/wiki/3D_printing", "3D-udskrivning", "Generel", "Indendørs", "Flot hobby bla");

    Hobby h2 = new Hobby("https://en.wikipedia.org/wiki/Acrobatics", "Akrobatik", "Generel", "Indendørs", "Fed hobby");


    CityInfo c1 = new CityInfo(2800, "Kongens Lyngby", new LinkedHashSet<>());
    CityInfo c2 = new CityInfo(3000, "Helsingør", new LinkedHashSet<>());
    Phone phone1 = new Phone("12345678", "Telenor", false);

    Phone phone2 = new Phone("24682468", "CBB", false);

    Address a1 = new Address(new AddressDTO("Sushi Blv", "2tv", false, c1));

    Address a2 = new Address(new AddressDTO("Kanalvej", "5a", false, c2));

    Person person = new Person("thomas@mail.dk", "Thomas", "Fritzbøger", phone1, a1);

    public HobbyFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
       emf = EMF_Creator.createEntityManagerFactoryForTest();
       facade = HobbyFacade.getHobbyFacade(emf);
       personFacade = PersonFacade.getPersonFacade(emf);

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
            em.createNamedQuery("Hobby.deleteAllRows").executeUpdate();
            em.createNamedQuery("Person.deleteAllRows").executeUpdate();


            em.persist(c1);
            em.persist(c2);
            em.persist(phone1);
            em.persist(phone2);
            em.persist(a1);
            em.persist(a2);
            em.persist(h1);
            em.persist(h2);

            em.getTransaction().commit();
        } finally {
            em.close();
        }

        EntityManager em2 = emf.createEntityManager();
        try {
            em2.getTransaction().begin();

            em2.persist(person);

            em2.getTransaction().commit();
        } finally {
            em2.close();
        }

    }

    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }

    // TODO: Delete or change this method 
    @Test
    public void testCreatingNewHobby() throws Exception {
        HobbyDTO hobby = facade.createHobby(new HobbyDTO("https://en.wikipedia.org/wiki/Baking", "Bagning", "Generel", "Indendørs", "Fedtede fingre"));
        assertEquals(3, facade.getAllHobbies().size());
    }

    @Test
    public void testGettingAllHobbies() throws Exception {
        List<HobbyDTO> hobbyList = facade.getAllHobbies();
        assertEquals(2, hobbyList.size());
    }

    @Test
    public void testGettingHobbyById() throws Exception {
        Hobby hobby = facade.getHobbyById(h2.getId());
        assertEquals("Akrobatik", hobby.getName());
    }

    @Test
    public void testAddingPeopleToHobby() throws Exception {
        System.out.println(h2);
        h2 = facade.addPersonToHobby(person, h2);
        System.out.println(h2);

        assertEquals(1, h2.getPeople().size());

        System.out.println(personFacade.getAllPersons());

    }


}
