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
public class Traineer {
    private int maHV;
 private int maKH;
 private String maNH;
 private double point = -1.0;
 private String name;

    public Traineer(int maHV, int maKH, String maNH,String name) {
        this.maHV = maHV;
        this.maKH = maKH;
        this.maNH = maNH;
        this.name = name;
    }
    
    public String toString(){
        return this.maNH + " - " + name;
    }
 
    public Traineer(){
    }

    public int getMaHV() {
        return maHV;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    

    public void setMaHV(int maHV) {
        this.maHV = maHV;
    }

    public int getMaKH() {
        return maKH;
    }

    public void setMaKH(int maKH) {
        this.maKH = maKH;
    }

    public String getMaNH() {
        return maNH;
    }

    public void setMaNH(String maNH) {
        this.maNH = maNH;
    }

    public double getPoint() {
        return point;
    }

    public void setPoint(double point) {
        this.point = point;
    }
 
 

}
