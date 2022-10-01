package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.AddressDTO;
import dtos.CityInfoDTO;
import dtos.HobbyDTO;
import dtos.PersonDTO;
import entities.*;
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

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getPersonById(@PathParam("id") int id) {
        return Response.ok().entity(GSON.toJson(FACADE.getPersonById(id))).build();
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response createPerson(String person) {

        Person p = GSON.fromJson(person, Person.class);
        System.out.println(p);

        // get cityInfo
        CityInfoDTO cityInfoDTO = cityInfoFacade.getCityByZipCode(p.getAddress().getCityInfo().getZipCode());
        System.out.println(cityInfoDTO);
        p.getAddress().setCityInfo(new CityInfo(cityInfoDTO));


        // create address
        AddressDTO a = addressFacade.create(new AddressDTO(p.getAddress()));
        System.out.println(a);
        p.setAddress(new Address(a));

        // create phone
        Phone phone = phoneFacade.createPhone(p.getPhone());
        System.out.println(phone);
        p.setPhone(phone);


        // create the person
        Person pNew = FACADE.createPerson(p);

        return Response.ok().entity(GSON.toJson(pNew)).build();
    }

    @POST
    @Path("{id}/hobby")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response addHobbyToPerson(@PathParam("id") int id, String hobbyId) {

        // Find the hobby
        Hobby hobby = GSON.fromJson(hobbyId, Hobby.class);
        HobbyDTO foundHobby = hobbyFacade.getHobbyById(hobby.getId());

        System.out.println("FOUND HOBBY");
        System.out.println(foundHobby);

        // Find the person
        PersonDTO personDTO = FACADE.getPersonById(id);
        System.out.println("FOUND PERSON");
        System.out.println(personDTO);

        // Add hobby to person
        return Response.ok().entity(GSON.toJson(FACADE.addHobbyToPerson(new Person(personDTO),new Hobby(foundHobby)))).build();
    }

    @GET
    @Path("/phone/{phoneNumber}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllPersons(@PathParam("phoneNumber") String phoneNumber) {
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

        HobbyDTO hobbyDTO = hobbyFacade.getHobbyById(hobbyId);
        Long peopleAmount = FACADE.getAmountOfPersonsGivenAHobby(hobbyId);

        return "{\"hobby\":\"" + hobbyDTO.getName() + "\"" + "," + "\"personcount\":\""+ peopleAmount +"\"" + "}";
    }

}
