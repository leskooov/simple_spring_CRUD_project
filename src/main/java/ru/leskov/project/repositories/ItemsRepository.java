package ru.leskov.project.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.leskov.project.models.Item;
import ru.leskov.project.models.Person;

import java.util.List;

@Repository
public interface ItemsRepository extends JpaRepository<Item,Integer> {
    List<Item> findByItemName(String itemName);

    // person.getItems()
    List<Item> findByOwner(Person owner);
}
