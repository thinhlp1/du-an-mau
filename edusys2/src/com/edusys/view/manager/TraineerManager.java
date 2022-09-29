/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edusys.view.manager;

import com.edusys.dao.LearnerDAO;
import com.edusys.dao.TraineeDAO;
import com.edusys.helper.AppStatus;
import com.edusys.helper.DialogHelper;
import com.edusys.helper.ShareHelper;
import com.edusys.model.Learner;
import com.edusys.model.Traineer;
import com.edusys.view.dialog.SearchDialog;
import com.edusys.view.dialog.SearchTraineerDialog;
import java.sql.SQLException;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.*;

/**
 *
 * @author ADMIN
 */
public class TraineerManager extends javax.swing.JFrame {

    private int index = 0;
    private TraineeDAO dao = new TraineeDAO();
    private LearnerDAO learnerDAO = new LearnerDAO();
    private Integer maKH;
    DefaultTableModel tblModel;
      DefaultComboBoxModel cbbModel;
    private List<Traineer> ListAllTraineer = new ArrayList<>() ;
    
     private List<Learner> ListAllLearner = new ArrayList<>() ;
    
    /**
     * Creates new form TraineerManager
     */
    public TraineerManager() {
        initComponents();
    }

    public TraineerManager(Integer maKH) {
        initComponents();
        init();
        
        this.maKH = maKH;
         load();
         ListAllLearner = learnerDAO.selectByCourse(maKH);

    }

    void fillTraineerCbb() {
       
        cbbModel.removeAllElements();
        try {
 
            for (int i = 0; i < ListAllLearner.size(); i++) {
                 cbbModel.addElement(ListAllLearner.get(i));
                
            }
            cbbTraineer.setSelectedIndex(-1);
        } catch (Exception e) {
            DialogHelper.alertError(this, "Lỗi truy vấn học viên kni fill!");
        }
    }

    public void loadFilted(){
        tblModel.setRowCount(0);
         List<Traineer> listFilted = new ArrayList<>();
            
            if (rdbHasPoint.isSelected()){
               for (Traineer tr : ListAllTraineer){
                   if (tr.getPoint() >= 0){
                       listFilted.add(tr);
                   }
               }
            }else if ( rdbNotPoint.isSelected() ){
                for (Traineer tr : ListAllTraineer){
                   if (tr.getPoint() == -1){
                       listFilted.add(tr);
                   }
               }
            }else{
                listFilted = ListAllTraineer;
            }
            
             for (Traineer tr : listFilted) {
                Object[] row = {
                    tr.getMaHV(),
                    tr.getMaNH(),
                    tr.getName(),
                    tr.getPoint(),
                    false};
                tblModel.addRow(row);
            }
    }
    
    void load() {

        tblModel.setRowCount(0);
        try {
            List<Traineer> list = dao.selectByCourse(maKH.toString());
            ListAllTraineer = list;
           
            
            for (Traineer tr : list) {
                Object[] row = {
                    tr.getMaHV(),
                    tr.getMaNH(),
                    tr.getName(),
                    tr.getPoint(),
                    false};
                tblModel.addRow(row);
            }

        } catch (Exception e) {
            e.printStackTrace();
            DialogHelper.alertError(this, "Lỗi truy vấn học viên khi load!");
        }
    }

    void insert() {
        
        boolean valid = validInput();
        if (!valid){
            DialogHelper.alertError(this, "Vui lòng nhập thông tin hợp lệ!");
            return;
        }
        
        Learner nguoiHoc = (Learner) cbbTraineer.getSelectedItem();
        Traineer model = new Traineer();
        model.setMaKH(maKH);
        model.setMaNH(nguoiHoc.getMaNH());
        model.setPoint(Double.valueOf(txtPoint.getText()));
        try {
            dao.insert(model);
            this.fillTraineerCbb();
            this.load();
        } catch (Exception e) {
            DialogHelper.alertError(this, "Lỗi thêm học viên vào khóa học!");
        }
    }

    void update() {

        for (int i = 0; i < tblTraineer.getRowCount(); i++) {
            Integer mahv = (Integer) tblTraineer.getValueAt(i, 0);
            String manh = (String) tblTraineer.getValueAt(i, 1);
            Double diem = Double.parseDouble(tblTraineer.getValueAt(i, 3).toString());
            Boolean isDelete = Boolean.parseBoolean(tblTraineer.getValueAt(i, 4).toString());
            if (isDelete) {
                boolean rs = dao.delete(maKH.toString(),mahv.toString());
                if(!rs){
                      DialogHelper.alert(this, "Xóa " + manh + " không thành công" );
                      return;
                }
            } else {
                Traineer model = new Traineer();
                model.setMaHV(mahv);
                model.setMaKH(maKH);
                model.setMaNH(manh);
                model.setPoint(diem);
                dao.update(model);
            }
        }
        this.fillTraineerCbb();
        this.load();
        DialogHelper.alert(this, "Cập nhật thành công!");
    }
    
    public void setSelected(String maNH){
        Learner learner = learnerDAO.findById(maNH);
        cbbModel.setSelectedItem(learner);
    }
    
    public boolean validInput(){
        int indexLearner = cbbTraineer.getSelectedIndex();
        int point;
        
        
        if ( indexLearner == -1){
            return false;
        }
        
        try {
            point = Integer.parseInt(txtPoint.getText());
            if (point > 10 || point < -1){
                return  false;
            }
        } catch (Exception e) {
            return false;
        }
        
        
        return true;
    }

    public void init() {
        setIconImage(ShareHelper.APP_ICON);
        cbbModel = (DefaultComboBoxModel) cbbTraineer.getModel();
        tblModel = new DefaultTableModel() {
            public Class getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return Object.class;
                    case 1:
                        return Object.class;
                    case 2:
                        return Object.class;
                    case 3:
                        return Object.class;
                    case 4:
                        return Boolean.class;

                    default:
                        return Boolean.class;
                }
            }

        };
        tblTraineer.setModel(tblModel);
        tblModel.addColumn("MA HV");
        tblModel.addColumn("MA NH");
        tblModel.addColumn("HO TEN");
        tblModel.addColumn("DIEM");
        tblModel.addColumn("XOA");
       
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btngFilter = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        cbbTraineer = new javax.swing.JComboBox<>();
        txtPoint = new javax.swing.JTextField();
        btnAdd = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblTraineer = new javax.swing.JTable();
        rdbNotPoint = new javax.swing.JRadioButton();
        rdbAll = new javax.swing.JRadioButton();
        rdbHasPoint = new javax.swing.JRadioButton();
        btnUpdate = new javax.swing.JButton();
        btnSearch = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("QUẢN LÝ HỌC VIÊN");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 255));
        jLabel1.setText("HỌC VIÊN TRONG KHÓA");

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btnAdd.setText("Thêm");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(cbbTraineer, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(28, 28, 28)
                .addComponent(txtPoint, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(btnAdd)
                .addGap(42, 42, 42))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(cbbTraineer)
                    .addComponent(btnAdd, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                    .addComponent(txtPoint))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 255));
        jLabel2.setText("HỌC VIÊN KHÁC");

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        tblTraineer.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "MÃ HV", "MÃ NH", "HỌ VÀ TÊN", "ĐIỂM", "XÓA"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblTraineer);

        btngFilter.add(rdbNotPoint);
        rdbNotPoint.setText("Chưa có điểm");
        rdbNotPoint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbNotPointActionPerformed(evt);
            }
        });

        btngFilter.add(rdbAll);
        rdbAll.setText("Tất cả");
        rdbAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbAllActionPerformed(evt);
            }
        });

        btngFilter.add(rdbHasPoint);
        rdbHasPoint.setText("Đã có điểm");
        rdbHasPoint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbHasPointActionPerformed(evt);
            }
        });

        btnUpdate.setText("Cập nhật");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(rdbAll)
                        .addGap(15, 15, 15)
                        .addComponent(rdbHasPoint)
                        .addGap(17, 17, 17)
                        .addComponent(rdbNotPoint)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 303, Short.MAX_VALUE)
                        .addComponent(btnUpdate)
                        .addGap(27, 27, 27)))
                .addGap(9, 9, 9))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(jScrollPane1)
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rdbAll)
                    .addComponent(rdbHasPoint)
                    .addComponent(rdbNotPoint)
                    .addComponent(btnUpdate))
                .addGap(14, 14, 14))
        );

        btnSearch.setText("Tìm nhanh");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(10, 10, 10))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(768, 789));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
              fillTraineerCbb();

    }//GEN-LAST:event_formWindowOpened

    private void rdbAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbAllActionPerformed
        loadFilted();
    }//GEN-LAST:event_rdbAllActionPerformed

    private void rdbHasPointActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbHasPointActionPerformed
        loadFilted();
    }//GEN-LAST:event_rdbHasPointActionPerformed

    private void rdbNotPointActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbNotPointActionPerformed
        loadFilted();
    }//GEN-LAST:event_rdbNotPointActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        update();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        insert();
        fillTraineerCbb();
        load();
        txtPoint.setText("");
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
       new SearchDialog(this, true, "Learner", maKH).setVisible(true);
    }//GEN-LAST:event_btnSearchActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        AppStatus.OPEN_CONTROLLER.closeOpeningWindown();
    }//GEN-LAST:event_formWindowClosing

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
            java.util.logging.Logger.getLogger(TraineerManager.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TraineerManager.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TraineerManager.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TraineerManager.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TraineerManager().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnUpdate;
    private javax.swing.ButtonGroup btngFilter;
    private javax.swing.JComboBox<String> cbbTraineer;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rdbAll;
    private javax.swing.JRadioButton rdbHasPoint;
    private javax.swing.JRadioButton rdbNotPoint;
    private javax.swing.JTable tblTraineer;
    private javax.swing.JTextField txtPoint;
    // End of variables declaration//GEN-END:variables
}

