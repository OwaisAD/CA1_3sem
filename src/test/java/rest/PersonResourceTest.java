package rest;

import dtos.AddressDTO;
import entities.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.LinkedHashSet;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
//Uncomment the line below, to temporarily disable this test
//@Disabled

public class PersonResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    private CityInfo c1;

    private Phone phone1;

    private Address a1;

    private Hobby h1;

    private Person person;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {
        //System.in.read();

        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }

    // Setup the DataBase (used by the test-server and this test) in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the EntityClass used below to use YOUR OWN (renamed) Entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        c1 = new CityInfo(2800, "Kongens Lyngby", new LinkedHashSet<>());;
        phone1 =  new Phone("12345678", "Telenor", false);
        a1 = new Address(new AddressDTO("Sushi Blv", "2tv", false, c1));
        h1 = new Hobby("https://en.wikipedia.org/wiki/3D_printing", "3D-udskrivning", "Generel", "Indendørs", "Flot hobby bla");
        person = new Person("thomas@mail.dk", "Thomas", "Fritzbøger", phone1, a1);
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Person.deleteAllRows").executeUpdate();
            em.persist(c1);
            em.persist(phone1);
            em.persist(a1);
            em.persist(h1);
            em.persist(person);

            person.addHobbies(h1);

            em.merge(person);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    public void testServerIsUp() {
        System.out.println("Testing is server UP");
        given().when().get("/persons").then().statusCode(200);
    }


    @Test
    public void testGetPersonById()  {
        given()
                .contentType(ContentType.JSON)
                .get("/persons/{id}",person.getId())
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("id", equalTo(person.getId()))
                .body("firstName", equalTo(person.getFirstName()))
                .body("phone.description", equalTo("Telenor"))
                .body("address.cityName", equalTo("Kongens Lyngby"))
                .body("hobbies.name", hasItems("3D-udskrivning"));
    }

    // testing response when trying to get a person with a non-existing id
    @Test
    public void testGetPersonByWrongId()  {
        given()
                .contentType(ContentType.JSON)
                .get("/persons/{id}", 9999999)
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND_404.getStatusCode())
                .body("code", equalTo(404))
                .body("message", equalTo("The Person entity with ID: 9999999 was not found"));

    }
}
