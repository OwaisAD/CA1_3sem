package facades;

import dtos.AddressDTO;
import entities.Address;
import entities.CityInfo;
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
public class PhoneFacadeTest {

    private static EntityManagerFactory emf;
    private static PhoneFacade facade;

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
            em.createNamedQuery("Phone.deleteAllRows").executeUpdate();
            em.persist(new Phone("12345678", "Telenor", false));
            em.persist(new Phone("24682468", "CBB", false));
            em.persist(new Phone("98765432", "Telia", false));

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
        assertEquals(4, facade.getAllPhones().size());
    }

    @Test
    public void testGetAllPhones() {
        List<Phone> phonesList = facade.getAllPhones();
        assertEquals(3, phonesList.size());
    }

    @Test
    public void getPhoneByPhoneNumber() {
        Phone phone = facade.getPhoneByPhoneNumber("98765432");
        assertEquals("Telia", phone.getDescription());
    }


}
