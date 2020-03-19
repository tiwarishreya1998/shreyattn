package com.tothenew.springjpaassgn3.Entities.ManyToMany;

import javax.persistence.*;
import java.util.Set;

@Entity
public class BookM {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToMany(mappedBy = "books")
    Set<AuthorM> authors;
}
