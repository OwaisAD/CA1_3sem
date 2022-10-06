package facades;

import dtos.PersonDTO;
import entities.*;
import errorhandling.EntityNotFoundException;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class PersonFacadeTest {

    private static EntityManagerFactory emf;
    private static PersonFacade facade;
    private static HobbyFacade hobbyFacade;
    private SetUpTest setUpTest = new SetUpTest();

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
        setUpTest.setUp(em);
    }

    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }

    @Test
    public void testCreatingAPerson() throws Exception {
        Person person = facade.createPerson(new Person("andreas@mail.dk", "Andreas", "Fritzbøger", setUpTest.getPhone2(), setUpTest.getAddress2()));
        assertEquals("Andreas", person.getFirstName());
        assertEquals("Kanalvej", person.getAddress().getStreet());
        System.out.println(person);
    }

    @Test
    public void testGettingAllPersons() {
        List<PersonDTO> personDTOList = facade.getAllPersons();
        assertEquals(2, personDTOList.size());
    }

    // test getting person by id
    @Test
    public void testGettingPersonById() throws EntityNotFoundException {
        PersonDTO person = facade.getPersonById(setUpTest.getPerson2().getId());
        assertEquals("daniel@mail.dk", person.getEmail());
    }

    // test getting person by phonenumber
    @Test
    public void testGettingPersonByPhoneNumber() throws EntityNotFoundException {
        PersonDTO personDTO = facade.getPersonByPhoneNumber("12345678");
        assertEquals("thomas@mail.dk", personDTO.getEmail());
    }

    @Test
    public void testAddingAHobbyToAPerson() throws Exception {
        //add hobby h1 to our person
        Person person = facade.addHobbyToPerson(setUpTest.getPerson().getId(), setUpTest.getHobby1());
        assertEquals(1, person.getHobbies().size());
        assertEquals("3D-udskrivning", person.getHobbies().get(0).getName());
        assertEquals("Flot hobby bla", person.getHobbies().get(0).getDescription());
    }


    @Test
    public void testGettingAllPersonsByZipCode() {
        List<PersonDTO> personDTOList = facade.getAllPersonsGivenAZipCode(2800);
        assertEquals(2, personDTOList.size());
    }

    @Test
    public void testGetAllPersonsGivenAHobby() throws EntityNotFoundException {
        facade.addHobbyToPerson(setUpTest.getPerson2().getId(), setUpTest.getHobby2());
        List<PersonDTO> personDTOList = facade.getAllPersonsGivenAHobbyId(setUpTest.getHobby2().getId());
        assertEquals("Daniel", personDTOList.get(0).getFirstName());
    }

    @Test
    public void testGetAmountOfPersonsGivenAHobby() throws EntityNotFoundException {
        facade.addHobbyToPerson(setUpTest.getPerson2().getId(), setUpTest.getHobby2());
        Long actual = facade.getAmountOfPersonsGivenAHobby(setUpTest.getHobby2().getId());
        assertEquals(1, actual);
    }

    @Test
    public void testDeletePerson() {
        facade.deletePerson(new PersonDTO(setUpTest.getPerson2()));
        assertThrows(EntityNotFoundException.class, () -> facade.getPersonById(setUpTest.getPerson2().getId()));
    }

    @Test
    public void testDeletePersonWithAHobby() throws EntityNotFoundException {
        facade.addHobbyToPerson(setUpTest.getPerson2().getId(),setUpTest.getHobby2());
        facade.deletePerson(new PersonDTO(setUpTest.getPerson2()));
        assertThrows(EntityNotFoundException.class, () -> facade.getPersonById(setUpTest.getPerson2().getId()));

        assertDoesNotThrow(() -> hobbyFacade.getHobbyById(setUpTest.getHobby2().getId()));
    }

}
