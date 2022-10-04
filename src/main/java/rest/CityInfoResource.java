package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.CityInfo;
import errorhandling.EntityNotFoundException;
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
    @Path("/zip/{zipcode}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getCityByZipCode(@PathParam("zipcode") int id) throws EntityNotFoundException {
        return Response.ok().entity(GSON.toJson(FACADE.getCityByZipCode(id))).build();
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getCityById(@PathParam("id") int id) throws EntityNotFoundException {
        return Response.ok().entity(GSON.toJson(FACADE.getCityInfoById(id))).build();
    }

    @GET
    @Path("/name/{cityName}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getCityByName(@PathParam("cityName") String cityName) throws EntityNotFoundException {
        return Response.ok().entity(GSON.toJson(FACADE.getCityInfoByName(cityName))).build();
    }

}
