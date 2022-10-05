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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public Response createPerson(String person) throws EntityNotFoundException {

        String errorMsg = "";

        // Make person from request body
        Person personFromJson = GSON.fromJson(person, Person.class);
        System.out.println(personFromJson);

        //VALIDATE PERSON INFORMATION
        // validate person email
        String email = personFromJson.getEmail();
        Pattern VALID_EMAIL_ADDRESS_REGEX =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        boolean isValidEmail = matcher.find();
        if(!isValidEmail || email == null) {
            errorMsg += "Parsed email was in a wrong format. ";
        }

        if(personFromJson.getFirstName().length() < 2) {
            errorMsg += "Firstname was not parsed correctly (at least two characters). ";
        }

        if(personFromJson.getLastName().length() < 2) {
            errorMsg += "Lastname was not parsed correctly (at least two characters). ";
        }

        String phoneNumber = personFromJson.getPhone().getNumber();
        String VALID_PHONE_NUMBER_REGEX = "(?<!\\d)\\d{8}(?!\\d)";
        boolean isValidNumber = phoneNumber.matches(VALID_PHONE_NUMBER_REGEX);
        if(!isValidNumber || phoneNumber == null) {
            errorMsg += "Invalid number parsed. Please enter a valid danish phone number (8 digits). ";
        }

        if(personFromJson.getAddress().getStreet() == null) {
            errorMsg += "Street was not parsed. ";
        }
        int zipCode = personFromJson
                .getAddress()
                .getCityInfo()
                .getZipCode();
        String VALID_ZIPCODE_REGEX = "^[0-9]{3,4}$"; // this here can be made to check if the zipcode given is a correct danish zipcode
        String zipCodeToStr = String.valueOf(zipCode);
        boolean checkZipCode = zipCodeToStr.matches(VALID_ZIPCODE_REGEX);
        if(!checkZipCode || zipCodeToStr == null) {
            errorMsg += "Unknown ZipCode format: " + zipCode + ". Please enter a valid danish zipcode (typically 3 or 4 digits). ";
        }

        if(errorMsg.length() > 1) {
            throw new EntityNotFoundException(errorMsg);
        }

        // get cityInfo
        CityInfoDTO cityInfoDTO = cityInfoFacade.getCityByZipCode(personFromJson.getAddress().getCityInfo().getZipCode());

        System.out.println(cityInfoDTO);
        personFromJson.getAddress().setCityInfo(new CityInfo(cityInfoDTO));

        // create address
//        AddressDTO a = addressFacade.create(new AddressDTO(personFromJson.getAddress()));
//        System.out.println(a);
//        personFromJson.setAddress(new Address(a));

        // create phone
//        Phone phone = phoneFacade.createPhone(personFromJson.getPhone());
//        personFromJson.setPhone(phone);

        // create the person
        Person pNew = FACADE.createPerson(personFromJson);
        PersonDTO personDTO = new PersonDTO(pNew);
        String result = GSON.toJson(personDTO);
        return Response.ok().entity(result).build();
    }

    @GET
    @Path("{personId}/addhobby/{hobbyId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response addHobbyToPerson(@PathParam("personId") int personId, @PathParam("hobbyId") int hobbyId) throws EntityNotFoundException {

        Hobby foundHobby;
        Person person;
        try {
            foundHobby = hobbyFacade.getHobbyById(hobbyId);
            person = FACADE.addHobbyToPerson(personId, foundHobby);

        } catch (EntityNotFoundException exception) {
            throw new NotFoundException(exception.getMessage());
        }

        // Add hobby to person
        return Response.ok().entity(GSON.toJson(new PersonDTO(person))).build();
    }

    @GET
    @Path("{personId}/removehobby/{hobbyId}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response removeHobbyFromPerson(@PathParam("personId") int personId, @PathParam("hobbyId") int hobbyId) throws EntityNotFoundException {

        Hobby foundHobby;
        Person person;

        try {
            foundHobby = hobbyFacade.getHobbyById(hobbyId);
            person = FACADE.removeHobbyFromPerson(personId, foundHobby);
        } catch (EntityNotFoundException exception) {
            throw new EntityNotFoundException(exception.getMessage());
        }

        // Add hobby to person
        return Response.ok().entity(GSON.toJson(new PersonDTO(person))).build();
    }


    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getPersonById(@PathParam("id") int id) throws NotFoundException {
        PersonDTO personDTO;
        try {
            personDTO = FACADE.getPersonById(id);
        } catch (EntityNotFoundException exception) {
            throw new NotFoundException(exception.getMessage());
        }
        return Response.ok().entity(GSON.toJson(personDTO)).build();
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
    public Response getAllPersonsGivenAZipcode(@PathParam("zipCode") int zipCode) {
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
    public String getAmountOfPersonsGivenAHobby(@PathParam("hobbyId") int hobbyId) throws EntityNotFoundException {

        Hobby hobby = hobbyFacade.getHobbyById(hobbyId);
        Long peopleAmount = FACADE.getAmountOfPersonsGivenAHobby(hobbyId);

        return "{\"hobby\":\"" + hobby.getName() + "\"" + "," + "\"personcount\":\""+ peopleAmount +"\"" + "}";
    }

    @PUT
    @Path("/{personId}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response editPerson(@PathParam("personId") int personId, String person) {
        Person personFromJson = GSON.fromJson(person, Person.class);

        Person personEntity = FACADE.editPersonById(personId,personFromJson.getEmail(),personFromJson.getFirstName(),personFromJson.getLastName());

        PersonDTO personDTO = new PersonDTO(personEntity);

        return Response.ok(GSON.toJson(personDTO)).build();
    }

    @DELETE
    @Path("/{personId}")
    public Response deletePerson(@PathParam("personId") int personId) throws EntityNotFoundException {
        PersonDTO personDTO = FACADE.getPersonById(personId);
        FACADE.deletePerson(personDTO);
        return Response.ok().build();
    }
}
