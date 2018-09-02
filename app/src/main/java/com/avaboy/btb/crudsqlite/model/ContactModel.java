package com.avaboy.btb.crudsqlite.model;

import java.io.Serializable;

/**
 * Created by iengpho on 8/28/18.
 */

public class ContactModel implements Serializable {

    private int mId;
    private String mFirstName, mLastName, mGender, mPhone, mEmail;

    public ContactModel() {}

    public ContactModel(int id,String firstName, String lastName, String gender, String phone, String email) {
        this.mId = id;
        this.mFirstName = firstName;
        this.mLastName = lastName;
        this.mGender = gender;
        this.mPhone = phone;
        this.mEmail = email;
    }

    public int getId() {
        return mId;
    }

    public void setId(int Id) {
        this.mId = Id;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        this.mFirstName = firstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        this.mLastName = lastName;
    }

    public String getGender() {
        return mGender;
    }

    public void setGender(String gender) {
        this.mGender = gender;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        this.mPhone = phone;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        this.mEmail = email;
    }
}
