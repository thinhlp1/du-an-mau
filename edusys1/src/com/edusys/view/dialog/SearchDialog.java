/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edusys.view.dialog;

import com.edusys.dao.LearnerDAO;
import com.edusys.dao.TraineeDAO;
import com.edusys.helper.AppStatus;
import com.edusys.helper.DialogHelper;
import com.edusys.model.Learner;
import com.edusys.model.Traineer;
import com.edusys.view.manager.LearnerManager3;
import com.edusys.view.manager.TraineerManager;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;

/**
 *
 * @author ADMIN
 */
public class SearchDialog extends javax.swing.JDialog {

    private DefaultListModel listModel = new DefaultListModel();
    private List<Learner> listAllLearner = new ArrayList<>();
    
    private List currentListAll;
   

    /**
     * Creates new form SearchDialog
     * @param parent
     * @param modal
     */
    public SearchDialog(java.awt.Frame parent, boolean modal, String manager,Object...id) {
        super(parent, modal);
        initComponents();
        initSearcher(manager,id);

    }

    public void initSearcher(String manager,Object...id) {
        if (manager.equals("Learner")) {
            LearnerDAO learnerDAO = new LearnerDAO();
            TraineeDAO dao = new TraineeDAO();
            listAllLearner = learnerDAO.selectByCourse(Integer.parseInt(id[0].toString()));
            currentListAll = listAllLearner;
            fillList(currentListAll);
        }
    }
    
    public List fillByKeyword(String keyword, List listAll){
        List<Object> list = new ArrayList<>();
        keyword = keyword.toUpperCase();
        for (int i = 0; i < listAll.size();i++){
           
            if (listAll.get(i).toString().contains(keyword)){
                list.add(listAll.get(i));
            }
        }   
        return list;
    }
    
    public void fillList(List list){
         listModel.clear();
        try {      
          
           
            for (int i = 0; i < list.size(); i++) {
               listModel.addElement(list.get(i));
                
            }      
            listSearch.setModel(listModel);

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
        listSearch = new javax.swing.JList<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

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

        listSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listSearchMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(listSearch);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSearch)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE))
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

        setSize(new java.awt.Dimension(418, 575));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtSearchInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_txtSearchInputMethodTextChanged

    }//GEN-LAST:event_txtSearchInputMethodTextChanged

    private void txtSearchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyPressed

    }//GEN-LAST:event_txtSearchKeyPressed

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        String keyWord = txtSearch.getText();
       
        fillList(fillByKeyword(keyWord,currentListAll));
    }//GEN-LAST:event_txtSearchKeyReleased

    private void txtSearchKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyTyped

    }//GEN-LAST:event_txtSearchKeyTyped

    private void listSearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listSearchMouseClicked
        if (evt.getClickCount() == 2) {
            Object object = listSearch.getSelectedValue();
            
            if (object instanceof Learner){
                Learner learner = (Learner) object;
                System.out.println(learner.getMaNH());
                 TraineerManager traineerManager =  (TraineerManager) AppStatus.OPEN_CONTROLLER.getOpeningWindown();
                 traineerManager.setSelected(learner.getMaNH());
            }

//            parentFrame.setLearner(learner);
//            this.dispose();

        }
        this.dispose();

    }//GEN-LAST:event_listSearchMouseClicked

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(SearchDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SearchDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SearchDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SearchDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                SearchDialog dialog = new SearchDialog(new javax.swing.JFrame(),true, "Traineer",3);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList<Object> listSearch;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
