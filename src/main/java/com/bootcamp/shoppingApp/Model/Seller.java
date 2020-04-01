package com.bootcamp.shoppingApp.Model;
import javax.persistence.*;

@Entity
@PrimaryKeyJoinColumn(name="user_ids")
public class Seller extends User{
    private String gst;
    private String company_contact;
    private String company_name;

    public String getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }

    public String getCompany_contact() {
        return company_contact;
    }

    public void setCompany_contact(String company_contact) {
        this.company_contact = company_contact;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }
}
