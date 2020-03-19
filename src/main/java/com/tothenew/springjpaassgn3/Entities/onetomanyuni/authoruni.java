package com.tothenew.springjpaassgn3.Entities.onetomanyuni;

import javax.persistence.*;
import java.util.Set;

@Entity
public class authoruni {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "authoruni_id",referencedColumnName = "id")
    Set<bookuni> bookunis;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<bookuni> getBookunis() {
        return bookunis;
    }

    public void setBookunis(Set<bookuni> bookunis) {
        this.bookunis = bookunis;
    }
}
