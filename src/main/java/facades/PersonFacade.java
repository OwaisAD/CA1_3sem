package facades;

import dtos.PersonDTO;
import entities.Hobby;
import entities.Person;
import entities.Phone;
import errorhandling.EntityNotFoundException;
import errorhandling.GenericExceptionMapper;
import utils.EMF_Creator;

import javax.persistence.*;
import javax.ws.rs.WebApplicationException;
import java.util.List;
import java.util.Objects;
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

    public Person addHobbyToPerson(int personId, Hobby hobby) throws EntityNotFoundException{
        EntityManager em = getEntityManager();
        Person person = null;
        try {
            person = em.find(Person.class, personId);

            if(person == null)
                throw new EntityNotFoundException("The entity Person with ID: " + personId + " was not found");

            if(person.getHobbies().contains(hobby)) {
                throw new WebApplicationException("Person is already apart of hobby with name: " + hobby.getName());
            }

            person.addHobbies(hobby);
            em.getTransaction().begin();
            em.merge(person);

            em.getTransaction().commit();

        } finally {
            em.close();
        }
        return person;
    }

    public Person removeHobbyFromPerson(int personId, Hobby hobby) throws EntityNotFoundException{
        EntityManager em = getEntityManager();
        Person person = null;
        try {
            person = em.find(Person.class, personId);

            if(person == null)
                throw new EntityNotFoundException("The entity Person with ID: " + personId + " was not found");

            // check if hobby doesn't exist in the person list
            boolean found = false;
            List<Hobby> hobbies = person.getHobbies();
            for (int i = 0; i < hobbies.size(); i++) {
                if(hobbies.get(i).getId().intValue() == hobby.getId().intValue()) {
                    person.removeHobbies(hobby);
                    found = true;
                    em.getTransaction().begin();
                    em.merge(person);
                    em.getTransaction().commit();
                }
            }
            if(!found) {
                throw new WebApplicationException(person.getFirstName() + " with ID " + personId + " did not have a hobby with ID: " + hobby.getId() + " and name " + hobby.getName());
            }

        } finally {
            em.close();
        }
        return person;
    }


    public List<PersonDTO> getAllPersonsGivenAZipCode(int zipCode) {
        // check number formatting
        String regex = "^[0-9]{3,4}$"; // this here can be made to check if the zipcode given is a correct danish zipcode
        String zipCodeToStr = String.valueOf(zipCode);
        boolean checkZipCode = zipCodeToStr.matches(regex);
        if(!checkZipCode) {
            throw new WebApplicationException("Unknown ZipCode format: " + zipCode + ". Please enter a valid danish zipcode (typically 3 or 4 digits)");
        }

        EntityManager em = getEntityManager();
        try {
            TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p WHERE p.address.cityInfo.zipCode= :zip", Person.class);
            query.setParameter("zip", zipCode);
            List<Person> persons = query.getResultList();

            if(persons.size() == 0) {
                throw new WebApplicationException("Found no persons with ZipCode: " + zipCode);
            }

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

            if(persons.size() == 0) {
                throw new WebApplicationException("Found no persons with hobby id: " + hobbyId);
            }

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

    public void deletePerson(PersonDTO personDTO) {
        EntityManager em = getEntityManager();
        try {
            Person person = em.find(Person.class, personDTO.getId());

            em.getTransaction().begin();
            if (!em.contains(person)) {
                person = em.merge(person);
            }
            em.remove(person);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public Person editPersonById(int personId, String email, String firstName, String lastName) {
        EntityManager em = getEntityManager();
        Person person = em.find(Person.class,personId);
        person.setEmail(email);
        person.setFirstName(firstName);
        person.setLastName(lastName);
        try {
            em.getTransaction().begin();
            em.merge(person);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return person;
    }

    public static void main(String[] args) {
        emf = EMF_Creator.createEntityManagerFactory();
        //PersonFacade pf = getPersonFacade(emf);

    }

}
