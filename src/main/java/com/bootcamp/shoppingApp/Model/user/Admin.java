package com.bootcamp.shoppingApp.Model.user;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name="user_id")
public class Admin extends User {
}
