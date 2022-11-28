package ru.leskov.springcourse.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.leskov.springcourse.models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//посредник между БД и классом Person
@Component
public class PersonDAO {
    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
//    private static final String URL = "jdbc:postgresql://localhost:5432/db_for_person";
//    private static final String USERNAME = "postgres";
//    private static final String PASSWORD = "myadmin";
//
//    private static Connection connection;
//    static {
//        try {
//            Class.forName("org.postgresql.Driver");
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//
//        try {
//            connection = DriverManager.getConnection(URL,USERNAME, PASSWORD);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }



    public List<Person> index(){

        //стейтмент = запрос к БД
        //запрос возвращается ResultSet
/**Переписанный способ запроса через обертку JDBC API -> jdbcTemplate*/
        return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Optional<Person> show(String email){
        return jdbcTemplate.query("SELECT * FROM Person" +
                                      " WHERE email=?",
                new Object[] {email},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }
    public Person show(int id){
        //получим 1 человека по ID из DAO и передадим на отображение в представление
//        return people.stream().filter(person -> person.getId() == id).findAny().orElse(null);
//        Person person = null;
//        try {
//            PreparedStatement preparedStatement =
//                    connection.prepareStatement("SELECT * " + //connection = подключение к БД
//                                                    "FROM Person " +
//                                                    "WHERE id=?");
//            preparedStatement.setInt(1,id);
//            ResultSet resultSet = preparedStatement.executeQuery();
//
//            resultSet.next();
//            person = new Person();
//            person.setId(resultSet.getInt("id"));
//            person.setName(resultSet.getString("name"));
//            person.setAge(resultSet.getInt("age"));
//            person.setEmail(resultSet.getString("email"));
//            person.setNameHobby(resultSet.getString("namehobby"));
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return person;
        /**Переписанный способ запроса через обертку JDBC API -> jdbcTemplate*/
        /**Если есть хотя бы 1 объект, то он будет возвращен, иначе вернутся null*/
        return jdbcTemplate.query("SELECT * FROM Person WHERE id=?",
                        new Object[]{id},
                        new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);}

    public void save(Person person){
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Person VALUES(1,?,?,?,?)");
//
//            preparedStatement.setString(1,person.getName());
//            preparedStatement.setInt(2,person.getAge());
//            preparedStatement.setString(3,person.getEmail());
//            preparedStatement.setString(4,person.getNameHobby());
//
//
//            preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        jdbcTemplate.update("INSERT INTO Person(name,age,email,namehobby,address) VALUES(?, ?, ?,?,?)", person.getName(), person.getAge(),
                person.getEmail(), person.getNameHobby(), person.getAddress());
    }

//    public void update(int id, Person updatePerson){
////        try {
////            PreparedStatement preparedStatement =
////                    connection.prepareStatement("UPDATE Person " +
////                                                    "SET name=?, age=?, email=?, namehobby=?" +
////                            "                        WHERE id=?");
////
////            preparedStatement.setString(1, updatePerson.getName());
////            preparedStatement.setInt(2, updatePerson.getAge());
////            preparedStatement.setString(3, updatePerson.getEmail());
////            preparedStatement.setString(4, updatePerson.getNameHobby());
////            preparedStatement.setInt(5, id);
////
////            preparedStatement.executeUpdate();
////        } catch (SQLException throwables) {
////            throwables.printStackTrace();
////        }
//        jdbcTemplate.update("UPDATE Person SET name=?, age=?, email=? WHERE id=?", updatePerson.getName(),
//                updatePerson.getAge(), updatePerson.getEmail(), updatePerson.getNameHobby(), id);
//    }

    public void update(int id, Person updatedPerson) {
        jdbcTemplate.update("UPDATE Person " +
                                "SET name=?, age=?, email=?, namehobby=?, address=? " +
                                "WHERE id=?",
                updatedPerson.getName(), updatedPerson.getAge(),
                updatedPerson.getEmail(),updatedPerson.getNameHobby(),updatedPerson.getAddress(),id);
    }
    public void delete(int id){
//        try {
//            PreparedStatement preparedStatement =
//                    connection.prepareStatement("DELETE FROM Person " +
//                                                    "WHERE id=?");
//
//            preparedStatement.setInt(1,id);
//
//            preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        jdbcTemplate.update("DELETE FROM Person WHERE id=?", id);
    }

    /**Тестирую производительность пакетной вставки*/
    public void testMultipleUpdate(){
        List<Person> people = create1000People();
        long before = System.currentTimeMillis();

        for (Person person : people){
            jdbcTemplate.update("INSERT INTO Person VALUES(?, ?, ?, ?,?)", person.getId(),person.getName(), person.getAge(),
                    person.getEmail(), person.getNameHobby());

        }

        long after = System.currentTimeMillis();

        System.out.println("TIME: " + (after - before));
    }

    public void testBatchUpdate(){
        List<Person> people = create1000People();
        long before = System.currentTimeMillis();

       jdbcTemplate.batchUpdate("INSERT INTO Person VALUES(?,?,?,?,?)", new BatchPreparedStatementSetter() {
           @Override
           public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
               preparedStatement.setInt(1,people.get(i).getId());
               preparedStatement.setString(2, people.get(i).getName());
               preparedStatement.setInt(3,people.get(i).getAge());
               preparedStatement.setString(4, people.get(i).getEmail());
               preparedStatement.setString(5, people.get(i).getNameHobby());
           }

           @Override
           public int getBatchSize() {
               return people.size();
           }
       });

        long after = System.currentTimeMillis();

        System.out.println("TIME WITH BACH: " + (after - before));

    }
    private List<Person> create1000People() {
        List<Person> people = new ArrayList<>();
        for (int i = 0; i<1000; i++)
           people.add(new Person(i,"Name" + i, 30, "test"+i+"@people.ru", "sport"+i, "address" + i));
        return people;
    }




}

