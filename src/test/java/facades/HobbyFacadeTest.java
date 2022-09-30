package facades;

import entities.Hobby;
import entities.Phone;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class HobbyFacadeTest {

    private static EntityManagerFactory emf;
    private static HobbyFacade facade;

    Hobby h1 = new Hobby("https://en.wikipedia.org/wiki/3D_printing", "3D-udskrivning", "Generel", "Indendørs", "Flot hobby bla");

    Hobby h2 = new Hobby("https://en.wikipedia.org/wiki/Acrobatics", "Akrobatik", "Generel", "Indendørs", "Fed hobby");


    public HobbyFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
       emf = EMF_Creator.createEntityManagerFactoryForTest();
       facade = HobbyFacade.getHobbyFacade(emf);

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

            em.persist(h1);
            em.persist(h2);

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
    public void testCreatingNewHobby() throws Exception {
        //INSERT INTO HOBBY (name,wikiLink,category,type)  VALUES ('Bagning','','Generel','Indendørs');
        Hobby hobby = facade.createHobby(new Hobby("https://en.wikipedia.org/wiki/Baking", "Bagning", "Generel", "Indendørs", "fedtede fingre"));
        assertEquals(3, facade.getAllHobbies().size());
    }



}
