/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edusys.view.manager;

import com.edusys.dao.LearnerDAO;
import com.edusys.helper.AppStatus;
import com.edusys.helper.DateHelper;
import com.edusys.helper.DialogHelper;
import com.edusys.helper.ShareHelper;
import com.edusys.model.Learner;
import java.awt.HeadlessException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ADMIN
 */
public class LearnerManager3 extends javax.swing.JFrame {

    private int currentIndex = 0;
    private LearnerDAO dao = new LearnerDAO();
    List<Learner> listAllLearner ;

    private DefaultTableModel tblModel;

    /**
     * Creates new form LeanrerManager
     */
    public LearnerManager3() {
        initComponents();
        init();
    }

    public void init() {
        setIconImage(ShareHelper.APP_ICON);
        tblModel = (DefaultTableModel) tblLearner.getModel();
        loadLearner();
        fillTable(listAllLearner);

    }
    
    
    
    void loadLearner(){
         listAllLearner = dao.select();
    }
    
    void filtLearner(String keyword){
         List<Learner> list = new ArrayList<>();
        keyword = keyword.toUpperCase();
        for (int i = 0; i < listAllLearner.size();i++){
           
            if (listAllLearner.get(i).info().toUpperCase().contains(keyword)){
              
                list.add(listAllLearner.get(i));
            }
        }
        fillTable(list);
       
    }

    void fillTable(List<Learner> list) {
      
        tblModel.setRowCount(0);
        try {
            String keyword = txtSearch.getText();
        
            for (Learner learner : list) {
                Object[] row = {
                    learner.getMaNH(),
                    learner.getName(),
                    learner.getGender() ? "Nam" : "Nữ",
                    DateHelper.toString(learner.getDob()),
                    learner.getNumberPhone(),
                    learner.getEmail(),
                    learner.getMaNV(),
                    DateHelper.toString(learner.getRegistDay())
                };
                tblModel.addRow(row);
            }
        } catch (Exception e) {
            DialogHelper.alertError(this, "Lỗi truy vấn dữ liệu!");
        }
    }
    
    
   

    void insert() {
        Learner model = getModel();
        try {
            dao.insert(model);
            loadLearner();
            this.fillTable(listAllLearner);
            this.clear();
            DialogHelper.alert(this, "Thêm mới thành công!");
        } catch (Exception e) {
            DialogHelper.alertError(this, "Thêm mới thất bại!");
        }
    }

    void update() {
        Learner model = getModel();
        try {
            dao.update(model);
            loadLearner();
            this.fillTable(listAllLearner);
            DialogHelper.alert(this, "Cập nhật thành công!");
        } catch (Exception e) {
            e.printStackTrace();
            DialogHelper.alertError(this, "Cập nhật thất bại!");
        }
    }

    void clear() {
        Learner model = new Learner();
      
        this.setModel(model);
        
    }

    void delete() {
        if (DialogHelper.confirm(this, "Bạn thực sự muốn xóa người học này?")) {
            String malearner = txtMaNH.getText();
            try {
                dao.delete(malearner);
                 loadLearner();
                this.fillTable(listAllLearner);
                this.clear();
                DialogHelper.alert(this, "Xóa thanh công!");
            } catch (HeadlessException e) {
                DialogHelper.alertError(this, "Xóa thất bại!");
            }
        }
    }

    void edit() {
        try {
            String malearner = (String) tblLearner.getValueAt(this.currentIndex, 0);
            Learner model = dao.findById(malearner);
            if (model != null) {
                this.setModel(model);
                this.setStatus(false);
            }
        } catch (Exception e) {
            DialogHelper.alertError(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    void setModel(Learner model) {
        txtMaNH.setText(model.getMaNH());
        txtName.setText(model.getName());
        cbbGender.setSelectedIndex(model.getGender() ? 0 : 1);
        txtBirth.setText(model.getDob() != null ? DateHelper.toString(model.getDob()): "");
        txtNumberPhone.setText(model.getNumberPhone());
        txtEmail.setText(model.getEmail());
        txtNote.setText(model.getNote());
    }

    Learner getModel() {
        Learner model = new Learner();
        model.setMaNH(txtMaNH.getText());
        model.setName(txtName.getText());
        model.setGender(cbbGender.getSelectedIndex() == 0);
        model.setDob(DateHelper.toDate(txtBirth.getText()));
        model.setNumberPhone(txtNumberPhone.getText());
        model.setEmail(txtEmail.getText());
        model.setNote(txtNote.getText());
        model.setMaNV(AppStatus.USER.getEmpID());
        model.setRegistDay(DateHelper.now());
        return model;
    }

    void setStatus(boolean insertable) {
        txtMaNH.setEditable(insertable);
        btnInsert.setEnabled(insertable);
        btnUpdate.setEnabled(!insertable);
        btnDelete.setEnabled(!insertable);
        boolean first = this.currentIndex > 0;
        boolean last = this.currentIndex < tblLearner.getRowCount() - 1;
        btnFirst.setEnabled(!insertable && first);
        btnPrev.setEnabled(!insertable && first);
        btnLast.setEnabled(!insertable && last);
        btnNext.setEnabled(!insertable && last);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        tabs = new javax.swing.JTabbedPane();
        pnlUpdate3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        txtNumberPhone = new javax.swing.JTextField();
        jScrollPane7 = new javax.swing.JScrollPane();
        txtNote = new javax.swing.JTextArea();
        btnNew = new javax.swing.JButton();
        btnInsert = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnFirst = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        txtEmail = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        txtBirth = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        cbbGender = new javax.swing.JComboBox<>();
        txtMaNH = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblLearner = new com.raven.swing.table.Table();
        jPanel4 = new javax.swing.JPanel();
        txtSearch = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
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
        jLabel1.setText("QUẢN LÝ NGƯỜI HỌC");

        pnlUpdate3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setText("Ghi chú");
        pnlUpdate3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, -1, -1));

        jLabel19.setText("Điện thoại");
        pnlUpdate3.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 210, -1, -1));
        pnlUpdate3.add(txtNumberPhone, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 230, 340, 30));

        txtNote.setColumns(20);
        txtNote.setRows(5);
        jScrollPane7.setViewportView(txtNote);

        pnlUpdate3.add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 299, 709, -1));

        btnNew.setText("Mới");
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });
        pnlUpdate3.add(btnNew, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 425, -1, -1));

        btnInsert.setText("Thêm");
        btnInsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertActionPerformed(evt);
            }
        });
        pnlUpdate3.add(btnInsert, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 425, -1, -1));

        btnUpdate.setText("Sửa");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });
        pnlUpdate3.add(btnUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 425, -1, -1));

        btnDelete.setText("Xóa");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        pnlUpdate3.add(btnDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 425, -1, -1));

        btnFirst.setText("|<");
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });
        pnlUpdate3.add(btnFirst, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 425, -1, -1));

        btnPrev.setText("<<");
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });
        pnlUpdate3.add(btnPrev, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 425, -1, -1));

        btnNext.setText(">>");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });
        pnlUpdate3.add(btnNext, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 425, -1, -1));

        btnLast.setText(">|");
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });
        pnlUpdate3.add(btnLast, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 425, -1, -1));
        pnlUpdate3.add(txtEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 230, 340, 30));

        jLabel21.setText("Địa chỉ mail");
        pnlUpdate3.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 210, 130, -1));
        pnlUpdate3.add(txtBirth, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 160, 330, 30));

        jLabel24.setText("Ngày sinh");
        pnlUpdate3.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 140, 130, -1));

        jLabel25.setText("Mã người học");
        pnlUpdate3.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 130, -1));

        cbbGender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nam", "Nữ" }));
        cbbGender.setSelectedIndex(-1);
        pnlUpdate3.add(cbbGender, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 329, 30));

        txtMaNH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaNHActionPerformed(evt);
            }
        });
        pnlUpdate3.add(txtMaNH, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 710, 30));

        jLabel26.setText("Giới tính");
        pnlUpdate3.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 130, -1));

        jLabel27.setText("Họ tên");
        pnlUpdate3.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 130, -1));

        txtName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNameActionPerformed(evt);
            }
        });
        pnlUpdate3.add(txtName, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 710, 30));

        tabs.addTab("CẬP NHẬT", pnlUpdate3);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel6.setFont(new java.awt.Font("sansserif", 1, 15)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(76, 76, 76));
        jLabel6.setText("Data Student");
        jLabel6.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 1));

        tblLearner.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã NH", "Họ Tên", "Giới tính", "Ngày sinh", "Điện thoại", "Email", "Mã NV", "Ngày Đk"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblLearner.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblLearnerMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblLearner);

        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtSearch)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(0, 802, Short.MAX_VALUE))
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(195, 195, 195))
        );

        tabs.addTab("DANH SÁCH", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(tabs, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(tabs, javax.swing.GroupLayout.PREFERRED_SIZE, 616, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(207, 207, 207))
        );

        setSize(new java.awt.Dimension(1000, 735));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        update();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        currentIndex++;
        edit();
    }//GEN-LAST:event_btnNextActionPerformed

    private void txtMaNHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaNHActionPerformed

    }//GEN-LAST:event_txtMaNHActionPerformed

    private void txtNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNameActionPerformed

    }//GEN-LAST:event_txtNameActionPerformed

    private void btnInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertActionPerformed
        insert();
    }//GEN-LAST:event_btnInsertActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        delete();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        clear();
        setStatus(true);
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        currentIndex = 0;
        edit();
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        currentIndex--;
        edit();
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        currentIndex = tblLearner.getRowCount() - 1;
        edit();
    }//GEN-LAST:event_btnLastActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
       
        setStatus(true);
    }//GEN-LAST:event_formWindowOpened

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
       AppStatus.OPEN_CONTROLLER.closeOpeningWindown();
    }//GEN-LAST:event_formWindowClosing

    private void tblLearnerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblLearnerMouseClicked
         if (evt.getClickCount() == 2) {
            currentIndex = tblLearner.rowAtPoint(evt.getPoint());
            if (this.currentIndex >= 0) {
                this.edit();
                tabs.setSelectedIndex(0);
            }
        }
    }//GEN-LAST:event_tblLearnerMouseClicked

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchKeyReleased

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://downfillTable.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(LearnerManager3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LearnerManager3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LearnerManager3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LearnerManager3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LearnerManager3().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnInsert;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> cbbGender;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JPanel pnlUpdate3;
    private javax.swing.JTabbedPane tabs;
    private com.raven.swing.table.Table tblLearner;
    private javax.swing.JTextField txtBirth;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtMaNH;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextArea txtNote;
    private javax.swing.JTextField txtNumberPhone;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
