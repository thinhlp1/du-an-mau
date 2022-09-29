/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edusys.model;

import com.edusys.helper.DateHelper;
import java.util.Date;

/**
 *
 * @author ADMIN
 */
public class Learner {
     private String maNH;
 private String name;
 private Date dob;
 private boolean gender;
 private String numberPhone;
 private String email;
 private String note;
 private String maNV;
 private Date registDay = DateHelper.now();

    public Learner(String maNH, String name, Date dob, boolean gender, String numberPhone, String email, String note, String maNV) {
        this.maNH = maNH;
        this.name = name;
        this.dob = dob;
        this.gender = gender;
        this.numberPhone = numberPhone;
        this.email = email;
        this.note = note;
        this.maNV = maNV;
    }
    
    public Learner(){
        
    }

    
 
    public String toString(){
        return maNH + " - " + name;
    }
    
    public String info(){
        return maNH + " " + name + email + numberPhone ;
    }
 
    public String getMaNH() {
        return maNH;
    }

    public void setMaNH(String maNH) {
        this.maNH = maNH;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public boolean getGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public Date getRegistDay() {
        return registDay;
    }

    public void setRegistDay(Date registDay) {
        this.registDay = registDay;
    }

    
}
