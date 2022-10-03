package facades;

import dtos.HobbyDTO;
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
            TypedQuery<PersonDTO> query = em.createQuery("SELECT NEW dtos.PersonDTO(p) FROM Person p", PersonDTO.class);
            List<PersonDTO> persons = query.getResultList();
            return persons;
        } finally {
            em.close();
        }
    }

    public PersonDTO getPersonById(int id) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p WHERE p.id = :personid", Person.class);
            query.setParameter("personid", id);

            Person person = query.getResultList().get(0);
            return new PersonDTO(person);

        } finally {
            em.close();
        }
    }

    public PersonDTO getPersonByPhoneNumber(String phoneNumber) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p WHERE p.phone.number = :number", Person.class);
            query.setParameter("number", phoneNumber);
            Person person = query.getResultList().get(0); //bør testes
            return new PersonDTO(person);
        } finally {
            em.close();
        }
    }

    public Person addHobbyToPerson(Person person, Hobby hobby) {

        person.addHobbies(hobby);

        EntityManager em = getEntityManager();
        try {
            // check if person exists

                em.getTransaction().begin();

                em.merge(person); //this needs to be fixed from the stackoverflow error

                em.getTransaction().commit();

        } finally {
            em.close();
        }
        return person;
    }

    public List<PersonDTO> getAllPersonsGivenAZipCode(int zipCode) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p WHERE p.address.cityinfo.zipCode= :zip", Person.class);
            query.setParameter("zip", zipCode);
            List<Person> persons = query.getResultList();
            return PersonDTO.getDtos(persons);
        } finally {
            em.close();
        }
    }

    public List<PersonDTO> getAllPersonsGivenAHobbyId(int hobbyId) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p JOIN p.hobbies ph WHERE ph.id = :hobbyid", Person.class);
            query.setParameter("hobbyid", hobbyId);
            List<Person> persons = query.getResultList();
            return PersonDTO.getDtos(persons);
        } finally {
            em.close();
        }
    }

    public Long getAmountOfPersonsGivenAHobby(int hobbyId) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery("SELECT count(p) FROM Person p JOIN p.hobbies ph WHERE ph.id= :hobbyid", Long.class);
            query.setParameter("hobbyid", hobbyId);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }


    /*public PersonDTO getPersonByPhoneNumber(String phoneNumber) {

    }*/

    public static void main(String[] args) {
        emf = EMF_Creator.createEntityManagerFactory();
        //PersonFacade pf = getPersonFacade(emf);


    }

}
