package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.HobbyDTO;
import errorhandling.EntityNotFoundException;
import facades.HobbyFacade;
import facades.PersonFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

//Todo Remove or change relevant parts before ACTUAL use
@Path("hobbies")
public class HobbyResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    private static final HobbyFacade FACADE =  HobbyFacade.getHobbyFacade(EMF);

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();


    // when /api/hobbies is hit, the following endpoint executes
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllHobbies() {
        return Response.ok().entity(GSON.toJson(FACADE.getAllHobbies())).build();
    }


    // when we want one specific hobby, the following endpoint executes
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getHobbyById(@PathParam("id") int id) throws EntityNotFoundException {
        return Response.ok().entity(GSON.toJson(new HobbyDTO(FACADE.getHobbyById(id)))).build();
    }

    @GET
    @Path("/name/{hobbyName}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getHobbyByName(@PathParam("hobbyName") String hobbyName) throws EntityNotFoundException {
        return Response.ok().entity(GSON.toJson(new HobbyDTO(FACADE.getHobbyByName(hobbyName)))).build();
    }
}
