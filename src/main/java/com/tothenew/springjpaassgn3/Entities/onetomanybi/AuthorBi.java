package com.tothenew.springjpaassgn3.Entities.onetomanybi;

import javax.persistence.*;
import java.util.Set;

@Entity
public class AuthorBi {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    Set<BookBi> bookBis;

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

    public Set<BookBi> getBookBis() {
        return bookBis;
    }

    public void setBookBis(Set<BookBi> bookBis) {
        this.bookBis = bookBis;
    }
}
