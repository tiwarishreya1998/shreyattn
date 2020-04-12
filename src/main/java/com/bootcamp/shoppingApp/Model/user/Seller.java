package com.bootcamp.shoppingApp.Model.user;

import com.bootcamp.shoppingApp.Model.Product.Product;

import javax.persistence.*;
import java.util.Set;

@Entity
@PrimaryKeyJoinColumn(name="user_id")
public class Seller extends User {
    private String gst;
    private String companyContact;
    private String companyName;

    @OneToMany(mappedBy = "seller",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<Product>products;

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public String getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }

    public String getCompanyContact() {
        return companyContact;
    }

    public void setCompanyContact(String companyContact) {
        this.companyContact = companyContact;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
