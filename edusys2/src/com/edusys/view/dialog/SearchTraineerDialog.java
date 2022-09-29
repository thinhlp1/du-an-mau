/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edusys.view.dialog;

import com.edusys.dao.LearnerDAO;
import com.edusys.dao.TraineeDAO;
import com.edusys.helper.DialogHelper;
import com.edusys.model.Learner;
import com.edusys.model.Traineer;
import com.edusys.view.manager.TraineerManager;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ADMIN
 */
public class SearchTraineerDialog extends javax.swing.JDialog {

    /**
     * Creates new form SeacherTraineerDialog
     */
     private Integer maKH;
    DefaultListModel listModel = new DefaultListModel();
    List<Learner> listAllLearner = new ArrayList<>();
    List<Traineer> listAllTrainner = new ArrayList<>();
     private LearnerDAO learnerDAO = new LearnerDAO();
    private TraineeDAO dao = new TraineeDAO();
    private TraineerManager parentFrame ;
    
    public SearchTraineerDialog(java.awt.Frame parent, boolean modal,Integer maKH,TraineerManager jframe) {
        super(parent, modal);
        initComponents();
        this.maKH = maKH;
        parentFrame = jframe;
        init();
        load(listAllLearner);
    }
    
    public void init(){
       listAllLearner = learnerDAO.selectByCourse(maKH);
       listAllTrainner = dao.selectByCourse(maKH.toString());
    }
    
    
    public List<Learner> fillLeartByKeyword(String keyword){
        List<Learner> list = new ArrayList<>();
        keyword = keyword.toUpperCase();
        for (int i = 0; i < listAllLearner.size();i++){
           
            if (listAllLearner.get(i).toString().contains(keyword)){
              
                list.add(listAllLearner.get(i));
            }else{
                
            }
        }
        
        
        
        return list;
    }
    
    public void load(List list){
          listModel.clear();
        try {      
          
           
            for (int i = 0; i < list.size(); i++) {
               listModel.addElement(list.get(i));
                
            }      
            ltsTraineer.setModel(listModel);

        } catch (Exception e) {
            e.printStackTrace();
            DialogHelper.alertError(this, "Lỗi truy vấn học viên khi load!");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtSearch = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        ltsTraineer = new javax.swing.JList<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        txtSearch.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                txtSearchInputMethodTextChanged(evt);
            }
        });
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSearchKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSearchKeyTyped(evt);
            }
        });

        ltsTraineer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ltsTraineerMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(ltsTraineer);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSearch)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 527, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 424, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        setSize(new java.awt.Dimension(565, 573));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
     
    }//GEN-LAST:event_formWindowOpened

    private void txtSearchInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_txtSearchInputMethodTextChanged
       
    }//GEN-LAST:event_txtSearchInputMethodTextChanged

    private void txtSearchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyPressed
        
    }//GEN-LAST:event_txtSearchKeyPressed

    private void txtSearchKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyTyped
        
    }//GEN-LAST:event_txtSearchKeyTyped

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
      String keyWord = txtSearch.getText();
        System.out.println(keyWord);
        load(fillLeartByKeyword(keyWord));
    }//GEN-LAST:event_txtSearchKeyReleased

    private void ltsTraineerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ltsTraineerMouseClicked
         if (evt.getClickCount() == 2) {
//            Learner learner = (Learner) ltsTraineer.getSelectedValue();
//           
//               parentFrame.setLearner(learner);
//               this.dispose();
            
        }
         
    }//GEN-LAST:event_ltsTraineerMouseClicked

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(SeacherTraineerDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(SeacherTraineerDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(SeacherTraineerDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(SeacherTraineerDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the dialog */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                SearchTraineerDialog dialog = new SearchTraineerDialog(new javax.swing.JFrame(), true,3,new TraineerManager());
//                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
//                    @Override
//                    public void windowClosing(java.awt.event.WindowEvent e) {
//                        System.exit(0);
//                    }
//                });
//                dialog.setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList<Object> ltsTraineer;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
