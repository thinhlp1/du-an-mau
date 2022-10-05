/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edusys.helper;

import com.edusys.view.manager.CourseManager;
////import com.edusys.view.manager.EmployeeManager3;
//import com.edusys.view.manager.LearnerManager3;

import com.edusys.view.manager.StatisticalManager;
import com.edusys.view.manager.TraineerManager;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;

/**
 *
 * @author ADMIN
 */
public class OpenController {

    private static List<JFrame> openedWindowns = new ArrayList<>();

//    public static void openLearnerManager() {
//        LearnerManager3 learnerManager = new LearnerManager3();
//        learnerManager.setVisible(true);
//        openedWindowns.add(learnerManager);
//        disableOtherWindown();
//    }
    
     public static void openCourseManager() {
         CourseManager courseManager = new CourseManager();
        courseManager.setVisible(true);
        openedWindowns.add(courseManager);
        disableOtherWindown();
    }
     
     public static void openTraineerManager(int maKH){
         TraineerManager trainnerManager = new TraineerManager(maKH);
         trainnerManager.setVisible(true);
         openedWindowns.add(trainnerManager);
         disableOtherWindown();
     }
     
//     public static void openEmployeeManager(){
//         EmployeeManager3 employeeManager = new EmployeeManager3();
//         employeeManager.setVisible(true);
//         openedWindowns.add(employeeManager);
//         disableOtherWindown();
//     }
//     
//     public static void openEmployeeManager(String maNV){
//         EmployeeManager3 employeeManager = new EmployeeManager3(maNV);
//         employeeManager.setVisible(true);
//         openedWindowns.add(employeeManager);
//         disableOtherWindown();
//     }
     
     
     public static void openStatiscalManager(int index){
         StatisticalManager statisticalManager = new StatisticalManager(index);
         statisticalManager.setVisible(true);
         openedWindowns.add(statisticalManager);
         disableOtherWindown();
     }
     
     public static void openMajorManager(){
//        // MajorManager3 majorManager = new MajorManager3();
//         majorManager.setVisible(true);
//         openedWindowns.add(majorManager);
//         disableOtherWindown();
     }
     
     
     public static JFrame getOpeningWindown(){
         JFrame jf = openedWindowns.get(openedWindowns.size()-1);
         return jf;
     }

//    public static void closeLearnerManager(JFrame windown) {
//        openedWindowns.remove(windown);
//        enablePrevWindown();
//    }

    public static void closeOpeningWindown() {
        openedWindowns.remove(openedWindowns.size() - 1);
        enablePrevWindown();
    }

//    public static void disableOtherWindown(JFrame openingWindown){
//        for (int i = 0 ; i < openedWindowns.size();i++){
//            if (openedWindowns.get(i) != openingWindown){
//                openedWindowns.get(i).setEnabled(false);
//            }
//        }
//    }
     static void disableOtherWindown() {
        for (int i = 0; i < openedWindowns.size() - 1; i++) {

            openedWindowns.get(i).setEnabled(false);

        }
    }

     static void enablePrevWindown() {
        openedWindowns.get(openedWindowns.size() - 1).setEnabled(true);
    }
    
    public static void initApp(JFrame homePage){
        openedWindowns.add(homePage);
    }
}
