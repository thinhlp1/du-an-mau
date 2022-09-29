/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edusys.helper;

import com.edusys.model.Employee;

/**
 *
 * @author ADMIN
 */
public class AppStatus {

    /**
     * Đối tượng này chứa thông tin người sử dụng sau khi đăng nhập
     */
    public static Employee USER = null;
    public static int STATUS = 0;
    public static OpenController OPEN_CONTROLLER = new OpenController();
    //0 is not load
    //1 is loading
    

    public static void logoff() {
        AppStatus.USER = null;
    }
    
    public static boolean isFirstStart(){
        return STATUS == 1;
    }

    /**
     * Kiểm tra xem đăng nhập hay chưa
     *
     * @return đăng nhập hay chưa
     */
    public static boolean authenticated() {
        return AppStatus.USER != null;
    }
    
//    public static String currentTask;
//    public static String previousTask;
}
