package facades;

import dtos.AddressDTO;
import entities.Address;
import entities.CityInfo;
import entities.RenameMe;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import java.util.LinkedHashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class AddressFacadeTest {

    private static EntityManagerFactory emf;
    private static AddressFacade facade;

    public AddressFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
       emf = EMF_Creator.createEntityManagerFactoryForTest();
       facade = AddressFacade.getAddressFacade(emf);
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
            em.persist(new CityInfo(2800, "Kongens Lyngby", new LinkedHashSet<>()));

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
    public void testGettingCityInfoByZipCode() throws Exception {
        CityInfo cityInfo = AddressFacade.getCityInfoByZipCode(2800);
        assertEquals("Kongens Lyngby", cityInfo.getCityName());
    }

    @Test
    public void testCreatingAnAddress() throws Exception {
        AddressDTO addressDTO = facade.create(new AddressDTO("Sushi Blv", "2th", false, 2800));
        assertEquals("Sushi Blv", addressDTO.getStreet());
        Address address = new Address(addressDTO);
        assertEquals("Kongens Lyngby", address.getCityInfo().getCityName());
    }

}
