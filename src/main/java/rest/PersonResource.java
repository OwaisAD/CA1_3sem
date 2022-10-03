package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.AddressDTO;
import dtos.CityInfoDTO;
import dtos.HobbyDTO;
import dtos.PersonDTO;
import entities.*;
import errorhandling.EntityNotFoundException;
import facades.*;
import utils.EMF_Creator;

import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

//Todo Remove or change relevant parts before ACTUAL use
@Path("persons")
public class PersonResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
       
    private static final PersonFacade FACADE =  PersonFacade.getPersonFacade(EMF);

    private static final AddressFacade addressFacade =  AddressFacade.getAddressFacade(EMF);

    private static final PhoneFacade phoneFacade =  PhoneFacade.getPhoneFacade(EMF);

    private static final CityInfoFacade cityInfoFacade =  CityInfoFacade.getCityInfoFacade(EMF);

    private static final HobbyFacade hobbyFacade =  HobbyFacade.getHobbyFacade(EMF);

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllPersons() {
        return Response.ok().entity(GSON.toJson(FACADE.getAllPersons())).build();
    }



    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response createPerson(String person) {

        // Make person from request body
        Person personFromJson = GSON.fromJson(person, Person.class);
        System.out.println(personFromJson);

        // get cityInfo
        CityInfoDTO cityInfoDTO = cityInfoFacade.getCityByZipCode(personFromJson.getAddress().getCityInfo().getZipCode());
        System.out.println(cityInfoDTO);
        personFromJson.getAddress().setCityInfo(new CityInfo(cityInfoDTO));


        // create address
        AddressDTO a = addressFacade.create(new AddressDTO(personFromJson.getAddress()));
        System.out.println(a);
        personFromJson.setAddress(new Address(a));

        // create phone
        Phone phone = phoneFacade.createPhone(personFromJson.getPhone());
        System.out.println(phone);
        personFromJson.setPhone(phone);


        // create the person
        Person pNew = FACADE.createPerson(personFromJson);

        return Response.ok().entity(GSON.toJson(pNew)).build();
    }

    @GET
    @Path("{personId}/addhobby/{hobbyId}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response addHobbyToPerson(@PathParam("personId") int personId, @PathParam("hobbyId") int hobbyId) throws EntityNotFoundException {

        // Find the hobby
        Hobby foundHobby = hobbyFacade.getHobbyById(hobbyId);

        System.out.println("FOUND HOBBY");
        System.out.println(foundHobby);

        // Find the person
        PersonDTO personDTO = FACADE.getPersonById(personId);
        System.out.println("FOUND PERSON");
        System.out.println(personDTO);

        // Add hobby to person
        return Response.ok().entity(GSON.toJson(new PersonDTO(FACADE.addHobbyToPerson(new Person(personDTO), foundHobby)))).build();
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getPersonById(@PathParam("id") int id) throws EntityNotFoundException {
        return Response.ok().entity(GSON.toJson(FACADE.getPersonById(id))).build();
    }

    @GET
    @Path("/phone/{phoneNumber}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getPersonByPhoneNumber(@PathParam("phoneNumber") String phoneNumber) throws EntityNotFoundException {
        return Response.ok().entity(GSON.toJson(FACADE.getPersonByPhoneNumber(phoneNumber))).build();
    }

    @GET
    @Path("/city/{zipCode}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllPersons(@PathParam("zipCode") int zipCode) {
        return Response.ok().entity(GSON.toJson(FACADE.getAllPersonsGivenAZipCode(zipCode))).build();
    }

    @GET
    @Path("/hobby/{hobbyId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getPersonsGivenAHobby(@PathParam("hobbyId") int hobbyId) {
        return Response.ok().entity(GSON.toJson(FACADE.getAllPersonsGivenAHobbyId(hobbyId))).build();
    }

    @GET
    @Path("/amount/hobby/{hobbyId}")
    @Produces({MediaType.APPLICATION_JSON})
    public String getAmountOfPersonsGivenAHobby(@PathParam("hobbyId") int hobbyId) {

        Hobby hobby = hobbyFacade.getHobbyById(hobbyId);
        Long peopleAmount = FACADE.getAmountOfPersonsGivenAHobby(hobbyId);

        return "{\"hobby\":\"" + hobby.getName() + "\"" + "," + "\"personcount\":\""+ peopleAmount +"\"" + "}";
    }

}
