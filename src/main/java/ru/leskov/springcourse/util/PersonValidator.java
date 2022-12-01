//package ru.leskov.springcourse.util;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.validation.Errors;
//import org.springframework.validation.Validator;
//import ru.leskov.springcourse.dao.PersonDAO;
//import ru.leskov.springcourse.models.Person;
//@Component
//public class PersonValidator implements Validator {
//
//
//    private final PersonDAO personDAO;
//
//    @Autowired
//    public PersonValidator(PersonDAO personDAO) {
//        this.personDAO = personDAO;
//    }
//
//    @Override
//    public boolean supports(Class<?> aClass) {
//        //к какому классу относится валидатор
//
//        return Person.class.equals(aClass);
//    }
//
//    @Override
//    public void validate(Object o, Errors errors) {
//        //вызывается на том объекте который приходит с формы
//        //знаем, что передаем объект класса Person, поэтому даункастим класс
//        Person person = (Person)o;
//        //посмотрим есть ли человек с таким эе имейлом в БД
//        //обращаемся через дао
//        if (personDAO.show(person.getEmail()).isPresent()){
//            errors.rejectValue("email","","This email is already taken");
//        }
//        //проверка на то, чтоб имя было с заглавной буквы.
//        if (!Character.isUpperCase(person.getName().codePointAt(0)))
//            errors.rejectValue("name","","Name should be start with a capital letter");
//    }
//}
