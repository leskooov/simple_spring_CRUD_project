package ru.leskov.project.models;

import javax.persistence.*;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "Item")
public class Item {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Item name should not be empty")
    @Column(name = "item_name")
    private String itemName;

    @NotEmpty(message = "Average Score should not be empty")
    @Column(name = "average_score")
    private String averageScore;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person owner;

    public Item(){

    }
    public Item(String itemName, String averageScore) {
        this.itemName = itemName;
        this.averageScore = averageScore;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(String averageScore) {
        this.averageScore = averageScore;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", itemName='" + itemName + '\'' +
                ", averageScore='" + averageScore + '\'' +
                ", owner=" + owner +
                '}';
    }
}
