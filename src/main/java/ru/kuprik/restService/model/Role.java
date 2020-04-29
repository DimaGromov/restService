package ru.kuprik.restService.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;


    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private List<Client> clients;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
