package ru.leskov.springcourse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.leskov.springcourse.dao.PersonDAO;
import ru.leskov.springcourse.models.Person;
//import ru.leskov.springcourse.util.PersonValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonDAO personDAO;
//    private final PersonValidator personValidator;

    @Autowired
    public PeopleController(PersonDAO personDAO){//, PersonValidator personValidator) {
        this.personDAO = personDAO;

//        this.personValidator = personValidator;
    }

    @GetMapping()
    public String index(Model model){
        //получим всех людей из DAO и положим в модель

        model.addAttribute("people", personDAO.index());
        return  "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        //получим 1 человека по его ID ищ DAO и передадим этого человека
        //на отображение в представление
        model.addAttribute("person", personDAO.show(id));
        return "people/show";
    }

    @GetMapping("/new")
    public String newPerson(Model model){
        model.addAttribute("person", new Person());
        return "people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult){ //положить в модель данные с формы
//        personValidator.validate(person,bindingResult);
        if (bindingResult.hasErrors())
            return "people/new";
        personDAO.save(person);                 //@Valid отвечает что значение соответствуют доступным значениям
        return "redirect:/people";
        // когда человек будет добавлен в БД, он увидит переход на /people и перейдет
    }
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("person", personDAO.show(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult,@PathVariable("id") int id){
//        personValidator.validate(person,bindingResult);

        if (bindingResult.hasErrors())
            return "people/edit";
        personDAO.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        personDAO.delete(id);
        return "redirect:/people";
    }
}
