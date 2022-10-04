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
public class CityInfoFacadeTest {

    private static EntityManagerFactory emf;
    private static CityInfoFacade facade;
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

  //  private CityInfo cityInfo1 = new CityInfo(3000, "Helsingør");
  //  private CityInfo cityInfo2 = new CityInfo(2800, "Kongens Lyngby");


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
            //em.createNamedQuery("CityInfo.deleteAllRows").executeUpdate();

           // em.persist(cityInfo1);
           // em.persist(cityInfo2);

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
    public void testGettingAllCityInfos() throws Exception {
        List<CityInfoDTO> cityInfos = facade.getAllCities();
        assertEquals(2, cityInfos.size());
    }

    @Test
    public void testGettingCityInfoById() throws Exception {
        CityInfoDTO cityInfoDTO = facade.getCityInfoById(c1.getId());
        assertEquals("Kongens Lyngby", cityInfoDTO.getCityName());
    }

    @Test
    public void testGettingCityByZipCode() throws Exception {
        CityInfoDTO cityInfoDTO = facade.getCityByZipCode(c1.getZipCode());
        assertEquals("Kongens Lyngby", cityInfoDTO.getCityName());
    }

}
