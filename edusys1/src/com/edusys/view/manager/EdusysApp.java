package com.edusys.view.manager;

import com.edusys.dao.EmployeeDAO;
import com.edusys.helper.AppStatus;
import com.ui.swing.component.EventMenuSelected;

import com.ui.swing.component.Header;

import com.ui.swing.component.Menu;

import java.awt.Component;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.animation.timing.Animator;

public class EdusysApp extends javax.swing.JFrame {

    private MigLayout layout;
    private Menu menu;
    private Header header;
    private ManagerForm managerForm;
    private Animator animator;

    private LearnerManage learnerManager;
    private EmployeeManager employeeManager;
    private MajorManager majorManager;
    private CourseManager2 courseManager;
    
    
    private int menuIndex;
    private int subMenuIndex;

     static class MAIN_MENU {

        static int MANAGER = 1;
        static int STATISTICAL = 2;
        static int GUIGE = 3;
        static int LOGOUT = 5;
        static int EXIT = 6;

    }



    public EdusysApp() {
//        new LoadingDialog(this,true).setVisible(true);
//        new LoginDialog(this,true).setVisible(true);
        
        AppStatus.USER = new EmployeeDAO().findById("ThinhLP");
        initComponents();
        init();
        
       
    }

    private void init() {
        layout = new MigLayout("fill", "0[]0[100%, fill]0", "0[fill, top]0");
        mainPane.setLayout(layout);
        menu = new Menu();
        
         menu.addEventMenuSelected(new MenuListener() );
          
       
      
        header = new Header();
        managerForm = new ManagerForm();
        learnerManager = new LearnerManage();
     
        mainPane.add(menu, "w 200!, spany 2");    // Span Y 2cell
        mainPane.add(header, "h 50!, wrap");
        mainPane.add(learnerManager, "w 100%, h 100%");
       showForm(managerForm);
    }
    
    
    class MenuListener implements EventMenuSelected{
          @Override
            public void selected(int index) {
                 System.out.println(index);
                if (index == MAIN_MENU.MANAGER) {
                    System.out.println(index);
                    showForm(managerForm);
                } else if (index == MAIN_MENU.GUIGE) {
                   // setForm(form1);
                } else if (index == MAIN_MENU.STATISTICAL) {
                  //  setForm(form2);
                } else if (index == MAIN_MENU.LOGOUT) {
                    //setForm(form3);
                }else if (index == MAIN_MENU.EXIT){
                    System.exit(1);
                }
            }
    }
    



 public void showForm(Component form) {
        mainPane.remove(mainPane.getComponentCount()-1);
        mainPane.add(form, "w 100%, h 100%");
        repaint();
        revalidate();
    }
    
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPane = new javax.swing.JLayeredPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        mainPane.setBackground(new java.awt.Color(245, 245, 245));
        mainPane.setOpaque(true);

        javax.swing.GroupLayout mainPaneLayout = new javax.swing.GroupLayout(mainPane);
        mainPane.setLayout(mainPaneLayout);
        mainPaneLayout.setHorizontalGroup(
            mainPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1857, Short.MAX_VALUE)
        );
        mainPaneLayout.setVerticalGroup(
            mainPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 930, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(mainPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(mainPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(EdusysApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EdusysApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EdusysApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EdusysApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new EdusysApp().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane mainPane;
    // End of variables declaration//GEN-END:variables
}

