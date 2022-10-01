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
public class Employee {

    private String empID;
    private String password;
    private String name;
    private boolean lore = false;

    public Employee(String empID, String password, String name) {
        this.empID = empID;
        this.password = password;
        this.name = name;
    }
    
    public Employee(){
        
    }

    @Override
    public String toString() {
        return empID + " - " + name;
    }
    
    

    public String getEmpID() {
        return empID;
    }

    public void setEmpID(String empID) {
        this.empID = empID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isLore() {
        return lore;
    }

    public void setLore(boolean lore) {
        this.lore = lore;
    }
    
    
}
