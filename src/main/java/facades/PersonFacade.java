package facades;

import dtos.PersonDTO;
import entities.CityInfo;
import entities.Hobby;
import entities.Person;
import entities.Phone;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class PersonFacade {

    private static PersonFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private PersonFacade() {}
    
    /**
     * 
     * @param _emf
     * @return an instance of this facade class.
     */
    public static PersonFacade getPersonFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PersonFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }


    /*public PersonDTO create(PersonDTO pd){
        Person person = new Person(pd);

        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(person);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new PersonDTO(person);
    }*/

    public Person createPerson(Person person) {

        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(person);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return person;
    }

    public List<PersonDTO> getAllPersons() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p", Person.class);
            List<Person> persons = query.getResultList();
            return PersonDTO.getDtos(persons);
        } finally {
            em.close();
        }
    }

    public Person getPersonById(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Person.class, id);
        } finally {
            em.close();
        }
    }

    public Person addHobbyToPerson(Person person, Hobby hobby) {
        EntityManager em = getEntityManager();
        try {
            // check if person exists
            if(person != null && hobby != null) {
                em.getTransaction().begin();
                person.getHobbies().add(hobby);
                em.merge(person);
                em.getTransaction().commit();
            }

        } finally {
            em.close();
        }
        return person;
    }



    /*public PersonDTO getPersonByPhoneNumber(String phoneNumber) {

    }*/

    public static void main(String[] args) {
        emf = EMF_Creator.createEntityManagerFactory();
        //PersonFacade pf = getPersonFacade(emf);


    }

}
