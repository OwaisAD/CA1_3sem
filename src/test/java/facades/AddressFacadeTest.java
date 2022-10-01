package facades;

import dtos.AddressDTO;
import dtos.CityInfoDTO;
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
public class AddressFacadeTest {

    private static EntityManagerFactory emf;
    private static AddressFacade facade;

    CityInfo c1 = new CityInfo(2800, "Kongens Lyngby", new LinkedHashSet<>());
    CityInfo c2 = new CityInfo(3000, "Helsingør", new LinkedHashSet<>());

    Address a1 = new Address(new AddressDTO("Sushi Blv", "2tv", false, c2));

    Address a2 = new Address(new AddressDTO("Kanalvej", "5a", false, c1));

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
            em.createNamedQuery("Address.deleteAllRows").executeUpdate();
            em.createNamedQuery("CityInfo.deleteAllRows").executeUpdate();
            em.persist(c1);
            em.persist(c2);

            em.getTransaction().commit();
        } finally {
            em.close();
        }

        EntityManager em2 = emf.createEntityManager();
        try {
            em2.getTransaction().begin();
            em2.persist(a1);
            em2.persist(a2);


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
    public void testGettingCityInfoByZipCode() throws Exception {
        CityInfoDTO cityInfo = facade.getCityInfoByZipCode(2800);
        System.out.println(cityInfo);
        assertEquals("Kongens Lyngby", cityInfo.getCityName());
    }

    @Test
    public void testCreatingAnAddress() throws Exception {
        AddressDTO addressDTO = facade.create(new AddressDTO("Sushi Blv", "2th", false, c1));
        assertEquals("Sushi Blv", addressDTO.getStreet());
        Address address = new Address(addressDTO);
        assertEquals("Kongens Lyngby", address.getCityInfo().getCityName());
    }

    @Test
    public void testGettingAllAddressesByZipCode() throws Exception {
        List<AddressDTO> addressList = facade.getAllAddressesByZipCode(2800);
        assertEquals(1, addressList.size());

        AddressDTO address = addressList.get(0);
        assertEquals("5a", address.getAdditionalInfo());
    }

    /*@Test
    public void testGettingAddressById() throws Exception {
        AddressDTO address = facade.getAddressById(c2.getId());
        assertEquals("Kongens Lyngby", address.getCityInfo().getCityName());
        assertEquals("Kanalvej", address.getStreet());
    }*/

}
