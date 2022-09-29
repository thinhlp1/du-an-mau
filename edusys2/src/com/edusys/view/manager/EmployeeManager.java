/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edusys.view.manager;

import com.edusys.dao.EmployeeDAO;
import com.edusys.helper.AppStatus;
import com.edusys.helper.DialogHelper;
import com.edusys.helper.ShareHelper;
import com.edusys.model.Employee;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ADMIN
 */
public class EmployeeManager extends javax.swing.JFrame {

    private int currentEmployIndex = 0;
    private DefaultTableModel tblModel;
    private EmployeeDAO dao = new EmployeeDAO();

    public EmployeeManager() {
        initComponents();
        init();
    }
    
     public EmployeeManager(String maNV) {
        initComponents();
        init();
        tabs.setSelectedIndex(0);
        setModel(dao.findById(maNV));
         setStatus(false);
        
    }

    void init() {
        setIconImage(ShareHelper.APP_ICON);
        tblModel = (DefaultTableModel) tblEmployee.getModel();
    }
    
    

    void load() {
        tblModel.setRowCount(0);
        try {
            List<Employee> list = dao.select();
            for (Employee emp : list) {
                Object[] row = {
                    emp.getEmpID(),
                    emp.getPassword(),
                    emp.getName(),
                    emp.isLore() ? "Trưởng phòng" : "Nhân viên"
                };
                tblModel.addRow(row);
            }
        } catch (Exception e) {
            DialogHelper.alertError(this, "Có lỗi xảy ra. Vui lòng liên hệ với kỹ thuật hỗ trợ!");
        }
    }

    void insert() {
        Employee model = getModel();
        String confirm = new String(txtComfirmPass.getPassword());
        if (confirm.equals(model.getPassword())) {
            try {
                boolean rs = dao.insert(model);
                this.load();
                this.clear();
                DialogHelper.alert(this, "Thêm mới thành công!");
            } catch (Exception e) {
                DialogHelper.alertError(this, "Thêm mới thất bại!");
            }
        } else {
            DialogHelper.alertError(this, "Xác nhận mật khẩu không đúng!");
        }
    }

    Employee getModel() {
        Employee model = new Employee();
        model.setEmpID(txtMaNV.getText());
        model.setName(txtName.getText());
        model.setPassword(new String(txtComfirmPass.getPassword()));
        model.setLore(rdbAdmin.isSelected());
        return model;
    }

    void setModel(Employee model) {
        txtMaNV.setText(model.getEmpID());
        txtName.setText(model.getName());
        txtPass.setText(model.getPassword());
       
        txtComfirmPass.setText(model.getPassword());
        rdbAdmin.setSelected(model.isLore());
        rdbEmployee.setSelected(!model.isLore());
    }

    void clear() {
        this.setModel(new Employee());
        this.setStatus(true);
    }

    void edit() {
        try {
            String manv = (String) tblEmployee.getValueAt(this.currentEmployIndex, 0);
            Employee model = dao.findById(manv);
            if (model != null) {
                this.setModel(model);
                this.setStatus(false);
            }
        } catch (Exception e) {
            DialogHelper.alertError(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    void delete() {
        if (DialogHelper.confirm(this, "Bạn thực sự muốn xóa nhân viên này?")) {
            String manv = txtMaNV.getText();
            try {
                dao.delete(manv);
                this.load();
                this.clear();
                DialogHelper.alert(this, "Xóa thành công!");
            } catch (Exception e) {
                DialogHelper.alertError(this, "Xóa thất bại!");
            }
        }
    }

    void update() {
        Employee model = getModel();
        String confirm = new String(txtComfirmPass.getPassword());
        if (!confirm.equals(model.getPassword())) {
            DialogHelper.alert(this, "Xác nhận mật khẩu không đúng!");
        } else {
            try {

                dao.update(model);
                this.load();
                DialogHelper.alert(this, "Cập nhật thành công!");
            } catch (Exception e) {
                DialogHelper.alertError(this, "Cập nhật thất bại!");
            }
        }
    }

    void setStatus(boolean insertable) {
        txtMaNV.setEditable(insertable);
        btnInsert.setEnabled(insertable);
        btnUpdate.setEnabled(!insertable);
        btnDelete.setEnabled(!insertable);
        boolean first = this.currentEmployIndex > 0;
        boolean last = this.currentEmployIndex < tblEmployee.getRowCount() - 1;
        btnFirst.setEnabled(!insertable && first);
        btnPrev.setEnabled(!insertable && first);
        btnNext.setEnabled(!insertable && last);
        btnLast.setEnabled(!insertable && last);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btngLore = new javax.swing.ButtonGroup();
        tabs = new javax.swing.JTabbedPane();
        pnlUpdate3 = new javax.swing.JPanel();
        btnNew = new javax.swing.JButton();
        btnInsert = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnFirst = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        jLabel25 = new javax.swing.JLabel();
        txtMaNV = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        rdbAdmin = new javax.swing.JRadioButton();
        rdbEmployee = new javax.swing.JRadioButton();
        txtComfirmPass = new javax.swing.JPasswordField();
        txtPass = new javax.swing.JPasswordField();
        pnlList3 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tblEmployee = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("QUẢN LÝ NHÂN VIÊN");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        btnNew.setText("Mới");
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });

        btnInsert.setText("Thêm");
        btnInsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertActionPerformed(evt);
            }
        });

        btnUpdate.setText("Sữa");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnDelete.setText("Xóa");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnFirst.setText("|<");
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });

        btnPrev.setText("<<");
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });

        btnNext.setText(">>");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnLast.setText(">|");
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });

        jLabel25.setText("Mã nhân viên");

        txtMaNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaNVActionPerformed(evt);
            }
        });

        jLabel27.setText("Mật khẩu");

        jLabel28.setText("Xác nhận mật khẩu");

        txtName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNameActionPerformed(evt);
            }
        });

        jLabel29.setText("Vai trò");

        jLabel30.setText("Họ tên");

        btngLore.add(rdbAdmin);
        rdbAdmin.setText("Trưởng phòng");
        rdbAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbAdminActionPerformed(evt);
            }
        });

        btngLore.add(rdbEmployee);
        rdbEmployee.setText("Nhân viên");
        rdbEmployee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbEmployeeActionPerformed(evt);
            }
        });

        txtPass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPassActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlUpdate3Layout = new javax.swing.GroupLayout(pnlUpdate3);
        pnlUpdate3.setLayout(pnlUpdate3Layout);
        pnlUpdate3Layout.setHorizontalGroup(
            pnlUpdate3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlUpdate3Layout.createSequentialGroup()
                .addGroup(pnlUpdate3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlUpdate3Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlUpdate3Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 710, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlUpdate3Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlUpdate3Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 710, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlUpdate3Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlUpdate3Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(rdbAdmin)
                        .addGap(18, 18, 18)
                        .addComponent(rdbEmployee))
                    .addGroup(pnlUpdate3Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(btnInsert)
                        .addGap(15, 15, 15)
                        .addComponent(btnUpdate)
                        .addGap(15, 15, 15)
                        .addComponent(btnDelete)
                        .addGap(15, 15, 15)
                        .addComponent(btnNew)
                        .addGap(167, 167, 167)
                        .addComponent(btnFirst)
                        .addGap(13, 13, 13)
                        .addComponent(btnPrev)
                        .addGap(9, 9, 9)
                        .addComponent(btnNext)
                        .addGap(9, 9, 9)
                        .addComponent(btnLast))
                    .addGroup(pnlUpdate3Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(pnlUpdate3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtComfirmPass, javax.swing.GroupLayout.PREFERRED_SIZE, 710, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlUpdate3Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(pnlUpdate3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, 710, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(20, 20, 20))
        );
        pnlUpdate3Layout.setVerticalGroup(
            pnlUpdate3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlUpdate3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel25)
                .addGap(4, 4, 4)
                .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabel27)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(jLabel28)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtComfirmPass, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jLabel30)
                .addGap(4, 4, 4)
                .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jLabel29)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlUpdate3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(rdbEmployee, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                    .addComponent(rdbAdmin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(29, 29, 29)
                .addGroup(pnlUpdate3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnInsert)
                    .addComponent(btnUpdate)
                    .addComponent(btnDelete)
                    .addComponent(btnNew)
                    .addComponent(btnFirst)
                    .addComponent(btnPrev)
                    .addComponent(btnNext)
                    .addComponent(btnLast)))
        );

        tabs.addTab("CẬP NHẬT", pnlUpdate3);

        tblEmployee.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "MÃ NV", "MẬT KHẨU", "HỌ TÊN", "VAI TRÒ"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblEmployee.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblEmployeeMouseClicked(evt);
            }
        });
        jScrollPane8.setViewportView(tblEmployee);

        javax.swing.GroupLayout pnlList3Layout = new javax.swing.GroupLayout(pnlList3);
        pnlList3.setLayout(pnlList3Layout);
        pnlList3Layout.setHorizontalGroup(
            pnlList3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlList3Layout.createSequentialGroup()
                .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 750, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        pnlList3Layout.setVerticalGroup(
            pnlList3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 421, Short.MAX_VALUE)
        );

        tabs.addTab("DANH SÁCH", pnlList3);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 255));
        jLabel1.setText("QUẢN LÝ NHÂN VIÊN QUẢN TRỊ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(tabs)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabs)
                .addGap(20, 20, 20))
        );

        setSize(new java.awt.Dimension(793, 570));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        update();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        currentEmployIndex = (currentEmployIndex < tblEmployee.getRowCount() - 1 ? currentEmployIndex + 1 : 0);
        this.edit();
    }//GEN-LAST:event_btnNextActionPerformed

    private void txtMaNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaNVActionPerformed

    }//GEN-LAST:event_txtMaNVActionPerformed

    private void txtNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNameActionPerformed

    }//GEN-LAST:event_txtNameActionPerformed

    private void rdbAdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbAdminActionPerformed

    }//GEN-LAST:event_rdbAdminActionPerformed

    private void rdbEmployeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbEmployeeActionPerformed

    }//GEN-LAST:event_rdbEmployeeActionPerformed

    private void txtPassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPassActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPassActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        load();
        setStatus(true);
    }//GEN-LAST:event_formWindowOpened

    private void btnInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertActionPerformed
        insert();
    }//GEN-LAST:event_btnInsertActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        delete();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        clear();
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        currentEmployIndex = 0;
        this.edit();
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        currentEmployIndex = (currentEmployIndex > 0 ? currentEmployIndex - 1 : tblEmployee.getRowCount() - 1);
        this.edit();
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        currentEmployIndex = tblEmployee.getRowCount() - 1;
        this.edit();
    }//GEN-LAST:event_btnLastActionPerformed

    private void tblEmployeeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblEmployeeMouseClicked
        if (evt.getClickCount() == 2) {
            currentEmployIndex = tblEmployee.rowAtPoint(evt.getPoint());
            if (currentEmployIndex >= 0) {
                this.edit();
                tabs.setSelectedIndex(0);
            }
        }
    }//GEN-LAST:event_tblEmployeeMouseClicked

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
            java.util.logging.Logger.getLogger(EmployeeManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EmployeeManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EmployeeManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EmployeeManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EmployeeManager().setVisible(true);
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
    private javax.swing.ButtonGroup btngLore;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JPanel pnlList3;
    private javax.swing.JPanel pnlUpdate3;
    private javax.swing.JRadioButton rdbAdmin;
    private javax.swing.JRadioButton rdbEmployee;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tblEmployee;
    private javax.swing.JPasswordField txtComfirmPass;
    private javax.swing.JTextField txtMaNV;
    private javax.swing.JTextField txtName;
    private javax.swing.JPasswordField txtPass;
    // End of variables declaration//GEN-END:variables
}
