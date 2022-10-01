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
public class Course {

    private int maKH;
    private String maCD;
    private double fee;
    private int duration;
    private Date openingDay;
    private String note;
    private String maNV;
    private Date createDay = DateHelper.now();

    public Course(int maKH, String maCD, double fee, int duration, Date openingDay, String note, String maNV) {
        this.maKH = maKH;
        this.maCD = maCD;
        this.fee = fee;
        this.duration = duration;
        this.openingDay = openingDay;
        this.note = note;
        this.maNV = maNV;
    }
    
    public Course(){
        
    }

    public String toString() {
        return maCD + " - " + DateHelper.toString(openingDay, "dd/MM/yyyy");
    }
    
    public String info(){
         return maCD + " - " + DateHelper.toString(openingDay, "dd/MM/yyyy");
    }

    public int getMaKH() {
        return maKH;
    }

    public void setMaKH(int maKH) {
        this.maKH = maKH;
    }

    public String getMaCD() {
        return maCD;
    }

    public void setMaCD(String maCD) {
        this.maCD = maCD;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Date getOpeningDay() {
        return openingDay;
    }

    public void setOpeningDay(Date openingDay) {
        this.openingDay = openingDay;
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

    public Date getCreateDay() {
        return createDay;
    }

    public void setCreateDay(Date createDay) {
        this.createDay = createDay;
    }
    
    

}
