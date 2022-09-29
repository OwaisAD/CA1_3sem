package facades;

import dtos.CityInfoDTO;
import dtos.HobbyDTO;
import entities.CityInfo;
import entities.Hobby;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class CityInfoFacade {

    private static CityInfoFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private CityInfoFacade() {}
    
    /**
     * 
     * @param _emf
     * @return an instance of this facade class.
     */
    public static CityInfoFacade getCityInfoFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new CityInfoFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public List<CityInfoDTO> getAllCities(){
        EntityManager em = getEntityManager();
        try {
            TypedQuery<CityInfo> query = em.createQuery("SELECT ci FROM CityInfo ci", CityInfo.class);
            List<CityInfo> cities = query.getResultList();
            return CityInfoDTO.getDtos(cities);
        } finally {
            em.close();
        }
    }

    public CityInfoDTO getCityById(int id) {
        EntityManager em = getEntityManager();
        try {
            CityInfo city = em.find(CityInfo.class, id);
            return new CityInfoDTO(city);
        }finally {
            em.close();
        }
    }

    // method for returning a specific city by its zipcode
    public CityInfoDTO getCityByZipCode(int zipCode) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<CityInfo> query = em.createQuery("SELECT c from CityInfo c WHERE c.zipCode= :zipCode", CityInfo.class);
            query.setParameter("zipCode", zipCode);
            CityInfo cityInfo = query.getSingleResult();
            return new CityInfoDTO(cityInfo);
        }finally {
            em.close();
        }
    }
    
    public static void main(String[] args) {
        emf = EMF_Creator.createEntityManagerFactory();
        CityInfoFacade cif = getCityInfoFacade(emf);
        System.out.println(cif.getAllCities());

    }

}
