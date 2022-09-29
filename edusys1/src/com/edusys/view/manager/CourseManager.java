/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edusys.view.manager;

import com.edusys.dao.CourseDAO;
import com.edusys.dao.MajorDAO;
import com.edusys.helper.AppStatus;
import com.edusys.helper.DateHelper;
import com.edusys.helper.DialogHelper;
import com.edusys.helper.ShareHelper;
import com.edusys.model.Course;
import com.edusys.model.Major;
import java.awt.HeadlessException;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ADMIN
 */
public class CourseManager extends javax.swing.JFrame {

    private int index = 0;
    private CourseDAO dao = new CourseDAO();
    private MajorDAO mjDAO = new MajorDAO();
    private DefaultTableModel tblModel;
    private Integer maKH ;

    /**
     * Creates new form CourseManager
     */
    public CourseManager() {
        initComponents();
        init();
    }

    public void init() {
        setIconImage(ShareHelper.APP_ICON);
        tblModel = (DefaultTableModel) tblCourse.getModel();
        fillComboBox();
        clear();
    }

    void load() {

        tblModel.setRowCount(0);
        try {
            List<Course> list = dao.select();
            for (Course kh : list) {
                Object[] row = {
                    kh.getMaKH(),
                    kh.getMaCD(),
                    kh.getDuration(),
                    kh.getFee(),
                    DateHelper.toString(kh.getOpeningDay(),"dd/MM/yyyy"),
                    kh.getMaNV(),
                    DateHelper.toString(kh.getCreateDay(),"dd/MM/yyyy")
                };
                tblModel.addRow(row);
            }
        } catch (Exception e) {
            DialogHelper.alertError(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    void insert() {
        Course model = getModel();
        model.setCreateDay(new Date());
        try {
            dao.insert(model);
            this.load();
            this.clear();
            DialogHelper.alert(this, "Thêm mới thành công!");
        } catch (HeadlessException e) {
            DialogHelper.alertError(this, "Thêm mới thất bại!");
        }
    }

    void update() {
        Course model = getModel();

        try {
            dao.update(model);
            this.load();
            DialogHelper.alert(this, "Cập nhật thành công!");
        } catch (Exception e) {
            DialogHelper.alertError(this, "Cập nhật thất bại!");
        }
    }

    void delete() {
        if (DialogHelper.confirm(this, "Bạn thực sự muốn xóa khóa học này?")) {
            
            try {
                dao.delete(this.maKH.toString());
                this.load();
                this.clear();
                DialogHelper.alert(this, "Xóa thành công!");
            } catch (Exception e) {
                DialogHelper.alert(this, "Xóa thất bại!");
            }
        }
    }

    void clear() {
        Course model = new Course();
//        String major = (String) cbbMajor.getSelectedItem();
//        String maCD;
//        List<Major> list = mjDAO.select();
//        for (Major mj : list){
//            if (mj.getName().equals(major)){
//                model.setFee(index);
//            }
//        }
        //model.setMaCD(chuyenDe.getMaCD());
        txtDuration.setText("");
        txtFee.setText("");
        txtNote.setText("");
        txtOpeningDay.setText("");
        cbbMajor.setSelectedIndex(-1);
        
        txtCreateDay.setText(DateHelper.toString(DateHelper.now(), "dd/MM/yyyy"));
        txtMaNV.setText(AppStatus.USER.getEmpID());
        
        maKH = null;
    }

    void edit() {
        try {

            Integer makh = (Integer) tblCourse.getValueAt(this.index, 0);
            Course model = dao.findById(makh.toString());
            if (model != null) {
                this.setModel(model);
                this.setStatus(false);
            }
        } catch (Exception e) {
            DialogHelper.alertError(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    void setModel(Course model) {
        //cbbMajor.setToolTipText(String.valueOf(model.getMaKH()));
       
         List<Major> list = mjDAO.select();
            for (int i = 0; i < list.size();i++) {
               if (model.getMaCD().equals(list.get(i).getMaCD())){
                   cbbMajor.setSelectedIndex(i);
               }
            }
        
        txtOpeningDay.setText(DateHelper.toString(model.getOpeningDay(),"dd/MM/yyyy"));
        txtFee.setText(String.valueOf(model.getFee()));
        txtDuration.setText(String.valueOf(model.getDuration()));
        txtMaNV.setText(model.getMaNV());
        txtCreateDay.setText(DateHelper.toString(model.getCreateDay(),"dd/MM/yyyy"));
        txtNote.setText(model.getNote());
        
        maKH = model.getMaKH();
    }

    Course getModel() {
        Course model = new Course();
        Major chuyenDe = (Major) cbbMajor.getSelectedItem();
        model.setMaCD(chuyenDe.getMaCD());
        model.setOpeningDay(DateHelper.toDate(txtOpeningDay.getText()));
        model.setFee(Double.valueOf(txtFee.getText()));
        model.setDuration(Integer.valueOf(txtDuration.getText()));
        model.setNote(txtNote.getText());
        model.setMaNV(AppStatus.USER.getEmpID());
        model.setCreateDay(DateHelper.toDate(txtCreateDay.getText()));
        model.setMaKH(this.maKH != null ? this.maKH : -1);

        return model;
    }

    void setStatus(boolean insertable) {
        btnInsert.setEnabled(insertable);
        btnUpdate.setEnabled(!insertable);
        btnDelete.setEnabled(!insertable);
        boolean first = this.index > 0;
        boolean last = this.index < tblCourse.getRowCount() - 1;
        btnFirst.setEnabled(!insertable && first);
        btnPrev.setEnabled(!insertable && first);
        btnLast.setEnabled(!insertable && last);
        btnNext.setEnabled(!insertable && last);
        btnTraineer.setVisible(!insertable);
    }

    void selectComboBox() {
       if (cbbMajor.getSelectedIndex() != -1){
            Major chuyenDe = (Major) cbbMajor.getSelectedItem();
        txtDuration.setText(String.valueOf(chuyenDe.getDuration()));
        txtFee.setText(String.valueOf(chuyenDe.getFee()));
       }
    }

    void openTraineer() {
        
        AppStatus.OPEN_CONTROLLER.openTraineerManager(maKH);
    }

    void fillComboBox() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cbbMajor.getModel();
        model.removeAllElements();
        try {
            List<Major> list = mjDAO.select();
            for (Major cd : list) {
                model.addElement(cd);
            }
        } catch (Exception e) {
            DialogHelper.alertError(this, "Lỗi truy vấn dữ liệu!");
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
        jLabel6 = new javax.swing.JLabel();
        txtFee = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtMaNV = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtNote = new javax.swing.JTextArea();
        btnNew = new javax.swing.JButton();
        btnInsert = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnFirst = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        txtDuration = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtCreateDay = new javax.swing.JTextField();
        txtOpeningDay = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        cbbMajor = new javax.swing.JComboBox<>();
        btnTraineer = new javax.swing.JButton();
        pnlList = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCourse = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("QUẢN LÝ KHÓA HỌC");
        setPreferredSize(new java.awt.Dimension(750, 680));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        pnlUpdate.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setText("Ghi chú");
        pnlUpdate.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, -1, -1));

        jLabel6.setText("Học phí");
        pnlUpdate.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, -1, -1));

        txtFee.setEditable(false);
        pnlUpdate.add(txtFee, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 340, 30));

        jLabel7.setText("Người tạo");
        pnlUpdate.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, -1, -1));

        txtMaNV.setEditable(false);
        pnlUpdate.add(txtMaNV, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 340, 30));

        txtNote.setColumns(20);
        txtNote.setRows(5);
        jScrollPane2.setViewportView(txtNote);

        pnlUpdate.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, 710, 140));

        btnNew.setText("Mới");
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });
        pnlUpdate.add(btnNew, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 410, -1, -1));

        btnInsert.setText("Thêm");
        btnInsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertActionPerformed(evt);
            }
        });
        pnlUpdate.add(btnInsert, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 410, -1, -1));

        btnUpdate.setText("Sữa");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });
        pnlUpdate.add(btnUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 410, -1, -1));

        btnDelete.setText("Xóa");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        pnlUpdate.add(btnDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 410, -1, -1));

        btnFirst.setText("|<");
        pnlUpdate.add(btnFirst, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 410, -1, -1));

        btnPrev.setText("<<");
        pnlUpdate.add(btnPrev, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 410, -1, -1));

        btnNext.setText(">>");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });
        pnlUpdate.add(btnNext, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 410, -1, -1));

        btnLast.setText(">|");
        pnlUpdate.add(btnLast, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 410, -1, -1));

        txtDuration.setEditable(false);
        pnlUpdate.add(txtDuration, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 110, 350, 30));

        jLabel9.setText("Thời lượng (giờ )");
        pnlUpdate.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 90, 130, -1));

        jLabel10.setText("Ngày tạo");
        pnlUpdate.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 150, 80, -1));

        txtCreateDay.setEditable(false);
        txtCreateDay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCreateDayActionPerformed(evt);
            }
        });
        pnlUpdate.add(txtCreateDay, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 170, 350, 30));
        pnlUpdate.add(txtOpeningDay, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 40, 350, 30));

        jLabel11.setText("Ngày khai giảng");
        pnlUpdate.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 20, 130, -1));

        jLabel12.setText("Chuyên đề");
        pnlUpdate.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 130, -1));

        cbbMajor.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbbMajorItemStateChanged(evt);
            }
        });
        pnlUpdate.add(cbbMajor, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 340, 30));

        btnTraineer.setText("Học viên");
        btnTraineer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTraineerActionPerformed(evt);
            }
        });
        pnlUpdate.add(btnTraineer, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 410, -1, -1));

        tabs.addTab("CẬP NHẬT", pnlUpdate);

        tblCourse.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "MÃ KH", "CHUYÊN ĐỀ", "THỜI LƯỢNG", "HỌC PHÍ", "KHAI GIẢNG", "TẠO BỞI", "NGÀY TẠO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblCourse.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCourseMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblCourse);

        javax.swing.GroupLayout pnlListLayout = new javax.swing.GroupLayout(pnlList);
        pnlList.setLayout(pnlListLayout);
        pnlListLayout.setHorizontalGroup(
            pnlListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 745, Short.MAX_VALUE)
        );
        pnlListLayout.setVerticalGroup(
            pnlListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlListLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 456, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        tabs.addTab("DANH SÁCH", pnlList);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 255));
        jLabel1.setText("QUẢN LÝ KHÓA HỌC");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(tabs))
                .addGap(13, 13, 13))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addGap(28, 28, 28)
                .addComponent(tabs, javax.swing.GroupLayout.PREFERRED_SIZE, 486, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(791, 616));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        update();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnNextActionPerformed

    private void txtCreateDayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCreateDayActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCreateDayActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened

        setStatus(true);
        load();
    }//GEN-LAST:event_formWindowOpened

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

    private void btnTraineerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTraineerActionPerformed
        openTraineer();
    }//GEN-LAST:event_btnTraineerActionPerformed

    private void tblCourseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCourseMouseClicked
         if (evt.getClickCount() == 2) {
            index = tblCourse.rowAtPoint(evt.getPoint());
            if (index >= 0) {
                this.edit();
                tabs.setSelectedIndex(0);
            }
        }
    }//GEN-LAST:event_tblCourseMouseClicked

    private void cbbMajorItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbbMajorItemStateChanged
       selectComboBox();
    }//GEN-LAST:event_cbbMajorItemStateChanged

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
            java.util.logging.Logger.getLogger(CourseManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CourseManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CourseManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CourseManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CourseManager().setVisible(true);
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
    private javax.swing.JButton btnTraineer;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> cbbMajor;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel pnlList;
    private javax.swing.JPanel pnlUpdate;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tblCourse;
    private javax.swing.JTextField txtCreateDay;
    private javax.swing.JTextField txtDuration;
    private javax.swing.JTextField txtFee;
    private javax.swing.JTextField txtMaNV;
    private javax.swing.JTextArea txtNote;
    private javax.swing.JTextField txtOpeningDay;
    // End of variables declaration//GEN-END:variables
}
