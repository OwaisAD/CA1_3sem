package facades;

import dtos.HobbyDTO;
import dtos.PersonDTO;
import entities.CityInfo;
import entities.Hobby;
import entities.Person;
import entities.Phone;
import errorhandling.EntityNotFoundException;
import errorhandling.GenericExceptionMapper;
import utils.EMF_Creator;

import javax.persistence.*;
import javax.ws.rs.WebApplicationException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public PersonDTO getPersonById(int id) throws EntityNotFoundException {
        EntityManager em = getEntityManager();
        try {
            //Query query = em.createQuery("SELECT p FROM Person p WHERE p.id = :personid", Person.class);
            //query.setParameter("personid", id);
            //Person person = (Person) query.getSingleResult();

            Person person = em.find(Person.class,id);

            if(person == null) {
                throw new EntityNotFoundException("The entity Person with ID: " + id + " was not found");
            }

            return new PersonDTO(person);
        } finally {
            em.close();
        }
    }

    public PersonDTO getPersonByPhoneNumber(String phoneNumber) throws EntityNotFoundException {
        // check number formatting
        String regex = "(?<!\\d)\\d{8}(?!\\d)";
        boolean checkPhoneNumber = phoneNumber.matches(regex);
        if(!checkPhoneNumber) {
            throw new WebApplicationException("Please enter a valid danish phone number (8 digits)");
        }


        EntityManager em = getEntityManager();
        try {
            TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p WHERE p.phone.number = :number", Person.class);
            query.setParameter("number", phoneNumber);
            List<Person> personList = query.getResultList(); //bør testes

            if(personList.size() == 0) {
                throw new EntityNotFoundException("The entity Person with number: " + phoneNumber + " was not found");
            }

            return new PersonDTO(personList.get(0));
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
