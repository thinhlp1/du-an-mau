/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edusys.view.manager;

import com.edusys.dao.MajorDAO;
import com.edusys.helper.AppStatus;
import com.edusys.helper.DialogHelper;
import com.edusys.helper.ShareHelper;
import com.edusys.model.Major;
import java.io.File;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ADMIN
 */
public class MajorManager extends javax.swing.JFrame {

    private int index = 0;
    private MajorDAO dao = new MajorDAO();
    private DefaultTableModel tblModel;

    /**
     * Creates new form MajorManager
     */
    public MajorManager() {
        initComponents();
        init();
    }

    public void init() {
        setIconImage(ShareHelper.APP_ICON);
        tblModel = (DefaultTableModel) tblMajor.getModel();
    }

    void load() {

        tblModel.setRowCount(0);
        try {
            List<Major> list = dao.select();
            for (Major cd : list) {
                Object[] row = {
                    cd.getMaCD(),
                    cd.getName(),
                    cd.getFee(),
                    cd.getDuration(),
                    cd.getPicture()
                };
                tblModel.addRow(row);
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    void insert() {
        Major model = getModel();
        try {
            dao.insert(model);
            this.load();
            this.clear();
            DialogHelper.alert(this, "Thêm mới thành công!");
        } catch (Exception e) {
            DialogHelper.alertError(this, "Thêm mới thất bại!");
        }
    }

    void update() {
        Major model = getModel();
        try {
            dao.update(model);
            this.load();
            DialogHelper.alert(this, "Cập nhật thành công!");
        } catch (Exception e) {
            DialogHelper.alert(this, "Cập nhật thất bại!");
        }
    }

    void delete() {
        if (DialogHelper.confirm(this, "Bạn có muốn xóa hay không?")) {
            String macd = txtMaCD.getText();
            try {
                dao.delete(macd);
                this.load();
                this.clear();
                DialogHelper.alert(this, "Xóa thành công!");
            } catch (Exception e) {
                DialogHelper.alert(this, "Xóa thất bại!");
            }
        }
    }

    void clear() {
        txtMaCD.setText("");
        txtNameMajor.setText("");
        txtDuration.setText("");
        txtDescreption.setText("");
        lblLogo.setIcon(null);
        lblLogo.setToolTipText("");
        txtFee.setText("");
        this.setStatus(true);
    }

    void edit() {
        try {
            String macd = (String) tblMajor.getValueAt(this.index, 0);
            Major model = dao.findById(macd);
            if (model != null) {
                this.setModel(model);
                this.setStatus(false);
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    void setModel(Major model) {

        txtMaCD.setText(model.getMaCD());
        txtNameMajor.setText(model.getName());
        txtDuration.setText(String.valueOf(model.getDuration()));
        txtFee.setText(String.valueOf(model.getFee()));
        txtDescreption.setText(model.getDescription());
        lblLogo.setToolTipText(model.getPicture());
        if (model.getPicture() != null) {
            ImageIcon icon = ShareHelper.readLogo(model.getPicture());
            lblLogo.setIcon(icon);
        }
    }

    Major getModel() {
        Major model = new Major();
        model.setMaCD(txtMaCD.getText());
        model.setName(txtNameMajor.getText());
        model.setDuration(Integer.valueOf(txtDuration.getText()));
        model.setFee(Double.valueOf(txtFee.getText()));
        model.setPicture(lblLogo.getToolTipText());
        model.setDescription(txtDescreption.getText());
        return model;
    }

    void setStatus(boolean insertable) {
        txtMaCD.setEditable(insertable);
        btnInsert.setEnabled(insertable);
        btnUpdate.setEnabled(!insertable);
        btnDelete.setEnabled(!insertable);
        boolean first = this.index > 0;
        boolean last = this.index < tblMajor.getRowCount() - 1;
        btnFirst.setEnabled(!insertable && first);
        btnPrev.setEnabled(!insertable && first);
        btnLast.setEnabled(!insertable && last);
        btnNext.setEnabled(!insertable && last);
    }
    
    
    void selectImage() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {

            File file = fileChooser.getSelectedFile();
            
            if (ShareHelper.saveLogo(file)) {
                // Hiển thị hình lên form
                lblLogo.setIcon(ShareHelper.readLogo(file.getName()));
                lblLogo.setToolTipText(file.getName());
            }
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

        tabs = new javax.swing.JTabbedPane();
        pnlUpdate = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lblLogo = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtMaCD = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtNameMajor = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtDuration = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtFee = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtDescreption = new javax.swing.JTextArea();
        btnNew = new javax.swing.JButton();
        btnInsert = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnFirst = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        pnlList = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMajor = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("QUẢN LÝ CHUYÊN ĐỀ");
        setPreferredSize(new java.awt.Dimension(750, 680));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        pnlUpdate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlUpdateMouseClicked(evt);
            }
        });

        jLabel2.setText("Mô tả chuyên đề");

        lblLogo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lblLogo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblLogoMouseClicked(evt);
            }
        });

        jLabel4.setText("Mã chuyên đề");

        jLabel5.setText("Tên chuyên đề");

        txtNameMajor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNameMajorActionPerformed(evt);
            }
        });

        jLabel6.setText("Thời lượng (giờ )");

        jLabel7.setText("Học phí");

        jLabel8.setText("Logo");

        txtDescreption.setColumns(20);
        txtDescreption.setRows(5);
        jScrollPane2.setViewportView(txtDescreption);

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

        javax.swing.GroupLayout pnlUpdateLayout = new javax.swing.GroupLayout(pnlUpdate);
        pnlUpdate.setLayout(pnlUpdateLayout);
        pnlUpdateLayout.setHorizontalGroup(
            pnlUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlUpdateLayout.createSequentialGroup()
                .addGroup(pnlUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlUpdateLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(btnInsert)
                        .addGap(15, 15, 15)
                        .addComponent(btnUpdate)
                        .addGap(15, 15, 15)
                        .addComponent(btnDelete)
                        .addGap(15, 15, 15)
                        .addComponent(btnNew)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 190, Short.MAX_VALUE)
                        .addComponent(btnFirst)
                        .addGap(13, 13, 13)
                        .addComponent(btnPrev)
                        .addGap(9, 9, 9)
                        .addComponent(btnNext)
                        .addGap(9, 9, 9)
                        .addComponent(btnLast))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlUpdateLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(lblLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addGroup(pnlUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMaCD)
                            .addComponent(jLabel5)
                            .addComponent(txtNameMajor)
                            .addComponent(jLabel6)
                            .addComponent(txtDuration)
                            .addComponent(jLabel7)
                            .addComponent(txtFee)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlUpdateLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jScrollPane2)))
                .addGap(25, 25, 25))
            .addGroup(pnlUpdateLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(pnlUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlUpdateLayout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(193, 193, 193)
                        .addComponent(jLabel4))
                    .addComponent(jLabel2))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        pnlUpdateLayout.setVerticalGroup(
            pnlUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlUpdateLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(pnlUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel4))
                .addGap(4, 4, 4)
                .addGroup(pnlUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlUpdateLayout.createSequentialGroup()
                        .addComponent(txtMaCD, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(jLabel5)
                        .addGap(4, 4, 4)
                        .addComponent(txtNameMajor, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jLabel6)
                        .addGap(4, 4, 4)
                        .addComponent(txtDuration, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jLabel7)
                        .addGap(4, 4, 4)
                        .addComponent(txtFee, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(20, 20, 20)
                .addComponent(jLabel2)
                .addGap(4, 4, 4)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addGroup(pnlUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnInsert)
                    .addComponent(btnUpdate)
                    .addComponent(btnDelete)
                    .addComponent(btnNew)
                    .addComponent(btnFirst)
                    .addComponent(btnPrev)
                    .addComponent(btnNext)
                    .addComponent(btnLast))
                .addGap(19, 19, 19))
        );

        tabs.addTab("CẬP NHẬT", pnlUpdate);

        tblMajor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "MÃ CD", "TÊN CD", "HỌC PHÍ", "THỜI LƯỢNG", "HỈNH ẢNH"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblMajor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMajorMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblMajor);

        javax.swing.GroupLayout pnlListLayout = new javax.swing.GroupLayout(pnlList);
        pnlList.setLayout(pnlListLayout);
        pnlListLayout.setHorizontalGroup(
            pnlListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 735, Short.MAX_VALUE)
        );
        pnlListLayout.setVerticalGroup(
            pnlListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 534, Short.MAX_VALUE)
        );

        tabs.addTab("DANH SÁCH", pnlList);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 255));
        jLabel1.setText("QUẢN LÝ CHUYÊN ĐỀ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(tabs))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addGap(55, 55, 55)
                .addComponent(tabs)
                .addContainerGap())
        );

        setSize(new java.awt.Dimension(782, 721));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        update();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
       index++;
        edit();
    }//GEN-LAST:event_btnNextActionPerformed

    private void txtNameMajorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNameMajorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNameMajorActionPerformed

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
       index = 0;
       edit();
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        index--;
        edit();
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        index = tblMajor.getRowCount() - 1;
        edit();
    }//GEN-LAST:event_btnLastActionPerformed

    private void pnlUpdateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlUpdateMouseClicked
       
    }//GEN-LAST:event_pnlUpdateMouseClicked

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        load();
        setStatus(true);
    }//GEN-LAST:event_formWindowOpened

    private void tblMajorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMajorMouseClicked
        if (evt.getClickCount() == 2) {
            index = tblMajor.rowAtPoint(evt.getPoint());
            if (this.index >= 0) {
                this.edit();
                tabs.setSelectedIndex(0);
            }
        }
    }//GEN-LAST:event_tblMajorMouseClicked

    private void lblLogoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLogoMouseClicked
       selectImage();
    }//GEN-LAST:event_lblLogoMouseClicked

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
            java.util.logging.Logger.getLogger(MajorManager.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MajorManager.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MajorManager.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MajorManager.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MajorManager().setVisible(true);
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JPanel pnlList;
    private javax.swing.JPanel pnlUpdate;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tblMajor;
    private javax.swing.JTextArea txtDescreption;
    private javax.swing.JTextField txtDuration;
    private javax.swing.JTextField txtFee;
    private javax.swing.JTextField txtMaCD;
    private javax.swing.JTextField txtNameMajor;
    // End of variables declaration//GEN-END:variables
}
