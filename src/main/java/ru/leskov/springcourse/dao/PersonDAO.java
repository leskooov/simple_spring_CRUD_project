package ru.leskov.springcourse.dao;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.leskov.springcourse.models.Person;

import java.util.List;
import java.util.Optional;

//посредник между БД и классом Person
@Component
public class PersonDAO {

private final SessionFactory sessionFactory;

    @Autowired
    public PersonDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)
    public List<Person> index(){
        Session session = sessionFactory.getCurrentSession();
        //usual hibernate cod
        return session.createQuery("select p from Person p",Person.class).getResultList();
    }
    @Transactional
    public Optional<Person> show(String email){

        return null;
    }
    @Transactional(readOnly = true)
    public Person show(int id){

        /**Переписанный способ запроса через обертку JDBC API -> jdbcTemplate*/
        /**Если есть хотя бы 1 объект, то он будет возвращен, иначе вернутся null*/
        Session session = sessionFactory.getCurrentSession();
        return session.get(Person.class,id);
    }
    @Transactional
    public void save(Person person){
        Session session = sessionFactory.getCurrentSession();
        session.save(person);
    }

    @Transactional
    public void update(int id, Person updatedPerson) {
        Session session = sessionFactory.getCurrentSession();
        Person personToBeUpdated = session.get(Person.class,id);

        personToBeUpdated.setName(updatedPerson.getName());
        personToBeUpdated.setAge(updatedPerson.getAge());
        personToBeUpdated.setNameHobby(updatedPerson.getNameHobby());
        personToBeUpdated.setAddress(updatedPerson.getAddress());
        personToBeUpdated.setEmail(updatedPerson.getEmail());

    }
    @Transactional
    public void delete(int id){
        Session session = sessionFactory.getCurrentSession();
        session.delete(session.get(Person.class,id));
    }

}

