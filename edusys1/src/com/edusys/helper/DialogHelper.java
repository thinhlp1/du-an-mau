/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edusys.helper;

import com.raven.dialog.Message;
import com.raven.main.EdusysApp;
import java.awt.Component;
import javax.swing.JOptionPane;

/**
 *
 * @author ADMIN
 */
public class DialogHelper {

    /**
     * Hiển thị thông báo cho người dùng
     *
     * @param parent là cửa sổ chứa thông báo
     * @param message là thông báo
     */
    public static void alert(Component parent, String message) {
        JOptionPane.showMessageDialog(null, message,
                "Hệ thống quản lý đào tạo", JOptionPane.INFORMATION_MESSAGE
        );
    }
    
     public static void alertError(Component parent, String message) {
         
        JOptionPane.showMessageDialog(null, message,
                "Hệ thống quản lý đào tạo", JOptionPane.ERROR_MESSAGE
        );
    }
     
     

    /**
     * Hiển thị thông báo và yêu cầu người dùng xác nhận
     *
     * @param parent là cửa sổ chứa thông báo
     * @param message là câu hỏi yes/no
     * @return là kết quả nhận được true/false
     */
    public static boolean confirm(Component parent, String message) {
        int result = JOptionPane.showConfirmDialog(null, message,
                "Hệ thống quản lý đào tạo",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return result == JOptionPane.YES_OPTION;
    }

    /**
     * Hiển thị thông báo yêu cầu nhập dữ liệu
     *
     * @param parent là cửa sổ chứa thông báo
     * @param message là thông báo nhắc nhở nhập
     * @return là kết quả nhận được từ người sử dụng nhập vào
     */
    public static String prompt(Component parent, String message) {
        return JOptionPane.showInputDialog(null, message,
                "Hệ thống quản lý đào tạo", JOptionPane.INFORMATION_MESSAGE);
    }
    
     public static  boolean showMessage(String message) {
        Message obj = new Message(EdusysApp.getFrames()[0], true);
        obj.showMessage(message);
        return obj.isOk();
    }
}