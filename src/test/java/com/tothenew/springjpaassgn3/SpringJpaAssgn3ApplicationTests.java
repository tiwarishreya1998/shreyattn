package com.tothenew.springjpaassgn3;

import com.tothenew.springjpaassgn3.Entities.Address;
import com.tothenew.springjpaassgn3.Entities.Author;
import com.tothenew.springjpaassgn3.Entities.Book;
import com.tothenew.springjpaassgn3.Entities.Subject;
import com.tothenew.springjpaassgn3.Entities.onetomanybi.AuthorBi;
import com.tothenew.springjpaassgn3.Entities.onetomanybi.BookBi;
import com.tothenew.springjpaassgn3.Entities.onetomanyuni.authoruni;
import com.tothenew.springjpaassgn3.Entities.onetomanyuni.bookuni;
import com.tothenew.springjpaassgn3.repos.AuthorBiRepo;
import com.tothenew.springjpaassgn3.repos.AuthorRepo;
import com.tothenew.springjpaassgn3.repos.authorunirepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;

@SpringBootTest
class SpringJpaAssgn3ApplicationTests {

    @Autowired
    AuthorRepo authorRepo;

    @Autowired
    authorunirepo authorunirepoO;

    @Autowired
    AuthorBiRepo authorBiRepo;

    @Test
    void insertAuthor() {
        Author author = new Author();
        Address address = new Address();
        address.setLocation("some location");
        address.setState("some state");
        address.setStreetNumber(1);
        author.setAddress(address);
        Set<Subject> subjects = new HashSet<>();
        Subject subject = new Subject();
        subject.setAuthor(author);
        subject.setName("maths");
        Subject subject1 = new Subject();
        subject1.setAuthor(author);
        subject1.setName("science");
        Subject subject3 = new Subject();
        subject3.setAuthor(author);
        subject3.setName("computer");
        subjects.add(subject);
        subjects.add(subject1);
        subjects.add(subject3);
        author.setSubjects(subjects);

        Book book = new Book();
        book.setAuthor(author);
        book.setBookName("As you like it");

        author.setBook(book);

        authorRepo.save(author);
    }

    @Test
    void insertAuthorUni() {
        bookuni bookuniO = new bookuni();
        bookuniO.setName("as you like it");
        bookuni bookuni1 = new bookuni();
        bookuni1.setName("as you like it too");
        Set<bookuni> bookunis = new HashSet<>();
        bookunis.add(bookuniO);
        bookunis.add(bookuni1);

        authoruni authoruniO = new authoruni();
        authoruniO.setName("rambo");
        authoruniO.setBookunis(bookunis);

        authorunirepoO.save(authoruniO);
    }

    @Test
    void insertAuthorBi() {
        AuthorBi authorBi = new AuthorBi();
        authorBi.setName("rambo");

        BookBi bookBi = new BookBi();
        bookBi.setName("As you like it");
        bookBi.setAuthorBi(authorBi);
        BookBi bookBi1 = new BookBi();
        bookBi1.setName("As you like it too");
        bookBi1.setAuthorBi(authorBi);
        Set<BookBi> bookBis = new HashSet<>();
        bookBis.add(bookBi);
        bookBis.add(bookBi1);

        authorBi.setBookBis(bookBis);

        authorBiRepo.save(authorBi);
    }


}
