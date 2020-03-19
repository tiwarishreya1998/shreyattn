package com.tothenew.springjpaassgn3.repos;

import com.tothenew.springjpaassgn3.Entities.Author;
import org.springframework.data.repository.CrudRepository;

public interface AuthorRepo extends CrudRepository<Author,Long> {
}
