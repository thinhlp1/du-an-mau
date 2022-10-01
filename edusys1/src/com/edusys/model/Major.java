/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edusys.model;

/**
 *
 * @author ADMIN
 */
public class Major {
    private String maCD;
 private String name;
 private double fee;
 private int duration;
 private String picture;
 private String description;

    public Major(String maCD, String name, double fee, int duration, String picture, String description) {
        this.maCD = maCD;
        this.name = name;
        this.fee = fee;
        this.duration = duration;
        this.picture = picture;
        this.description = description;
    }

     public Major(){
         
     }
     
    @Override
     public String toString(){
         return this.name + " " + this.maCD + this.fee + this.duration;
     }
    
 
    public String getMaCD() {
        return maCD;
    }

    public void setMaCD(String maCD) {
        this.maCD = maCD;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
 
 
}
