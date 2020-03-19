package com.tothenew.springjpaassgn3.Entities.onetomanybi;

import javax.persistence.*;

@Entity
public class BookBi {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn
    AuthorBi authorBi;

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

    public AuthorBi getAuthorBi() {
        return authorBi;
    }

    public void setAuthorBi(AuthorBi authorBi) {
        this.authorBi = authorBi;
    }
}
