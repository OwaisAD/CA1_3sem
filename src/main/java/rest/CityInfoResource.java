package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.CityInfo;
import facades.CityInfoFacade;
import facades.HobbyFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

//Todo Remove or change relevant parts before ACTUAL use
@Path("cities")
public class CityInfoResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    private static final CityInfoFacade FACADE =  CityInfoFacade.getCityInfoFacade(EMF);

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();


    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllCities() {
        return Response.ok().entity(GSON.toJson(FACADE.getAllCities())).build();
    }


    @GET
    @Path("{zipcode}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getCityByZipCode(@PathParam("zipcode") int id) {
        return Response.ok().entity(GSON.toJson(FACADE.getCityByZipCode(id))).build();
    }

    @GET
    @Path("/id/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getCityById(@PathParam("id") int id) {
        return Response.ok().entity(GSON.toJson(FACADE.getCityInfoById(id))).build();
    }

}
