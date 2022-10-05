package facades;

import dtos.HobbyDTO;
import entities.Hobby;
import entities.Person;
import errorhandling.EntityNotFoundException;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class HobbyFacade {

    private static HobbyFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private HobbyFacade() {}
    
    /**
     * 
     * @param _emf
     * @return an instance of this facade class.
     */
    public static HobbyFacade getHobbyFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new HobbyFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }


    public HobbyDTO createHobby(HobbyDTO hobbyDTO) {
        Hobby hobby = new Hobby(hobbyDTO);
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(hobby);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new HobbyDTO(hobby);
    }

    public List<HobbyDTO> getAllHobbies(){
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Hobby> query = em.createQuery("SELECT h FROM Hobby h", Hobby.class);
            List<Hobby> hobbies = query.getResultList();
            return HobbyDTO.getDTOs(hobbies);
        } finally {
            em.close();
        }
    }

    public Hobby getHobbyById(int id) throws EntityNotFoundException{
        EntityManager em = getEntityManager();
        try {
            Hobby hobby = em.find(Hobby.class, id);
            if(hobby == null)
                throw new EntityNotFoundException("Entity for Hobby with ID: " + id + " was not found");

            return hobby;
        } finally {
            em.close();
        }
    }

    public Hobby addPersonToHobby(Person person, Hobby hobby) {
        EntityManager em = getEntityManager();
        hobby.getPeople().add(person);
        try {
            em.getTransaction().begin();
            em.merge(hobby);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return hobby;
    }

    public Hobby getHobbyByName(String hobbyName) throws EntityNotFoundException {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Hobby> query = em.createQuery("SELECT h FROM Hobby h WHERE h.name = :hname", Hobby.class);
            query.setParameter("hname", hobbyName);
            List<Hobby> hobbyList = query.getResultList();

            if(hobbyList.size() == 0) {
                throw new EntityNotFoundException("The entity Hobby with name: " + hobbyName + " was not found");
            }

            return hobbyList.get(0);
        } finally {
            em.close();
        }
    }

    public static void main(String[] args) {
        emf = EMF_Creator.createEntityManagerFactory();
        //HobbyFacade hf = new HobbyFacade();
        //System.out.println(hf.getAllHobbies());

    }


}
