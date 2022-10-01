package facades;

import dtos.AddressDTO;
import dtos.CityInfoDTO;
import entities.Address;
import entities.CityInfo;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.LinkedHashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class CityInfoFacadeTest {

    private static EntityManagerFactory emf;
    private static CityInfoFacade facade;


    public CityInfoFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
       emf = EMF_Creator.createEntityManagerFactoryForTest();
       facade = CityInfoFacade.getCityInfoFacade(emf);
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

            em.persist(new CityInfo(3000, "Helsingør"));
            em.persist(new CityInfo(2800, "Kongens Lyngby"));

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
    public void testGettingAllCityInfos() throws Exception {
        List<CityInfoDTO> cityInfos = facade.getAllCities();
        assertEquals(2, cityInfos.size());
        assertEquals("Kongens Lyngby", cityInfos.get(1).getCityName());
    }

    @Test
    public void testGettingCityInfoById() throws Exception {
        CityInfoDTO cityInfoDTO = facade.getCityInfoById(1);
        assertEquals("Helsingør", cityInfoDTO.getCityName());
    }

    @Test
    public void testGettingCityByZipCode() throws Exception {
        CityInfoDTO cityInfoDTO = facade.getCityByZipCode(3000);
        assertEquals("Helsingør", cityInfoDTO.getCityName());
    }

}
