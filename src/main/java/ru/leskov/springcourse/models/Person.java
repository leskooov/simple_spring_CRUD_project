package ru.leskov.springcourse.models;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "Person")
public class Person {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "*Name should not be empty") //в поле не было пустого значения
    @Size(min = 2, max = 30, message = "Should be between 2 and 30 characters") //имя от 2 до 30 букв
    @Column(name = "name")
    private String name;
    @NotEmpty(message = "*Message should not be empty")
    @Email(message = "*Email should be valid")
    @Column(name = "email")
    private String email;
    @Min(value = 0, message = "*Age must be greater then 0")
    @Column(name="age")
    //@NotEmpty(message = "Age should not be empty")
    private int age;
    @NotEmpty(message = "*Hobby should be not empty")
    @Column(name = "nameHobby")
    private String nameHobby;
    //Страна, Город, Индекс (******)
    //Russia, Moscow, 123456
    @Pattern(regexp = "[A-Z]\\w+, [A-Z]\\w+, \\d{6}",
            message ="Your address should be in this format: Country, City, Postal Code(6 digits)")
    @Column(name = "address")
    private String address;
    public Person(String name, int age, String email,String nameHobby, String adress) {
        this.name = name;
        this.nameHobby = nameHobby;
        this.age = age;
        this.email =email;
        this.address = adress;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Person(){

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameHobby() {
        return nameHobby;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNameHobby(String nameHobby) {
        this.nameHobby = nameHobby;
    }

}
