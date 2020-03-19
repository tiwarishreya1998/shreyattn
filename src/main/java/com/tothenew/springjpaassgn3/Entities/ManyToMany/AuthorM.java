package com.tothenew.springjpaassgn3.Entities.ManyToMany;

import javax.persistence.*;
import java.util.Set;

@Entity
public class AuthorM {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToMany
    @JoinTable(name = "author_book",joinColumns = @JoinColumn(name = "author_id",referencedColumnName = "id"),inverseJoinColumns = @JoinColumn(name = "book_id",referencedColumnName = "id"))
    Set<BookM> books;
}
