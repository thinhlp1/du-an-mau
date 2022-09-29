/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edusys.view.manager;

import com.edusys.dao.AppDAO;
import com.edusys.dao.CourseDAO;
import com.edusys.dao.StatiscalDAO;
import com.edusys.helper.AppStatus;
import com.edusys.helper.DateHelper;
import com.edusys.helper.DialogHelper;
import com.edusys.model.Course;
import java.awt.Dialog;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ADMIN
 */
public class StatisticalManager extends javax.swing.JFrame {

    private List<Object[]> pointData = null;
    private List<Object[]> learnerData = null;
    private List<Object[]> pointByMajorData = null;
    private List<Object[]> revenueData = null;

    private StatiscalDAO dao = new StatiscalDAO();
    private CourseDAO courseDao = new CourseDAO();

    /**
     * Creates new form StatisticalManager
     */
    public StatisticalManager(int index) {
        initComponents();
        init(index);
    }

    public void init(int index) {
        tabbStatiscal.setSelectedIndex(index);
        changeTab(index);
    }

    public void load(int index) {
        switch (index) {
            case 0:
                if (learnerData == null) {
                    System.out.println("load" + index);
                    learnerData = dao.getLearnerStc();
                }
                break;
            case 1:
                if (pointData == null) {
                    System.out.println("load" + index);
                    pointData = dao.getPoint(getMaKHSelected());
                }
                break;
            case 2:
                if (pointByMajorData == null) {
                    System.out.println("load" + index);
                    pointByMajorData = dao.getPointByMajor();
                }
                break;
            case 3:
                if (revenueData == null) {
                    System.out.println("load" + index);
                        revenueData = dao.getRevenue(Calendar.YEAR);
                }
                break;
            default:
                break;
        }
    }

    public void changeTab(int index) {
       
        switch (index) {
            case 0:
                if (learnerData == null) {
                     load(index);
                    fillLearnerTable();
                }
                break;
            case 1:
                if (pointData == null) {
                     
                     fillCourseCbb();
                     load(index);
                    fillPointTable();
                }
                break;
            case 2:
                if (pointByMajorData == null) {
                     load(index);
                    fillPointByMajorData();
                }
                break;
            case 3:

                if (revenueData == null) {
                     
                    fillYearCbb();
                    load(index);
                    fillRevenueTalbe();
                }
                break;
            default:
                break;
        }
    }

    public void fillLearnerTable() {
        DefaultTableModel tblModel = (DefaultTableModel) tblLearnerStc.getModel();
        tblModel.setRowCount(0);

        for (Object[] row : learnerData) {
            tblModel.addRow(row);
        }
    }

    public void fillPointTable() {
         DefaultTableModel tblModel = (DefaultTableModel) tblPoint.getModel();
        tblModel.setRowCount(0);

        for (Object[] row : pointData) {
            tblModel.addRow(row);
        }
    }

    public void fillPointByMajorData() {
         DefaultTableModel tblModel = (DefaultTableModel) tblSumPoint.getModel();
        tblModel.setRowCount(0);

        for (Object[] row : pointByMajorData) {
            tblModel.addRow(row);
        }
    }

    public void fillRevenueTalbe() {
         DefaultTableModel tblModel = (DefaultTableModel) tblRevenue.getModel();
        tblModel.setRowCount(0);

        for (Object[] row : revenueData) {
            tblModel.addRow(row);
        }
    }

    public void fillYearCbb() {
        DefaultComboBoxModel cbbModel = (DefaultComboBoxModel) cbbYear.getModel();
        cbbModel.removeAllElements();
        try {
            int firstYear = AppDAO.getFirstYear();
            int currentYear = Calendar.getInstance().get(Calendar.YEAR);
            for (int i = firstYear; i <= currentYear; i++) {
                cbbModel.addElement(i);
            }
            cbbModel.setSelectedItem(currentYear);

        } catch (SQLException ex) {
           
            DialogHelper.alertError(this, "Lỗi truy vấn");
        }catch (Exception ex) {
           
            DialogHelper.alertError(this, "Có lỗi xảy ra. Vui lòng liên hệ hỗ trợ viên");
        }
    }

    public void fillCourseCbb() {
        DefaultComboBoxModel cbbModel = (DefaultComboBoxModel) cbbCourse.getModel();
       
        try {
             cbbModel.removeAllElements();
            List<Course> list= courseDao.select();
            for (Course course : list){
                cbbModel.addElement(course);
            }
            cbbModel.setSelectedItem(list.get(list.size()-1));
        } catch (Exception e) {
             DialogHelper.alertError(this, "Có lỗi xảy ra. Vui lòng liên hệ hỗ trợ viên");
        }
    }
    
    public int getMaKHSelected(){
        Course course = (Course) cbbCourse.getSelectedItem();
        return course.getMaKH();
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
        tabbStatiscal = new javax.swing.JTabbedPane();
        pnlLearner = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblLearnerStc = new javax.swing.JTable();
        pnlPointStc = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblPoint = new javax.swing.JTable();
        cbbCourse = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        btnSearch = new javax.swing.JButton();
        pnlSumPointStc = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblSumPoint = new javax.swing.JTable();
        pnlRevenue = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblRevenue = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        cbbYear = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("THỐNG KÊ");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 255));
        jLabel1.setText("QUẢN LÝ KHÓA HỌC");

        tabbStatiscal.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tabbStatiscalStateChanged(evt);
            }
        });

        tblLearnerStc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "NĂM", "SỐ NGƯỜI HỌC", "ĐÀU TIÊN", "SAU CÙNG"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblLearnerStc);

        javax.swing.GroupLayout pnlLearnerLayout = new javax.swing.GroupLayout(pnlLearner);
        pnlLearner.setLayout(pnlLearnerLayout);
        pnlLearnerLayout.setHorizontalGroup(
            pnlLearnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 983, Short.MAX_VALUE)
        );
        pnlLearnerLayout.setVerticalGroup(
            pnlLearnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 526, Short.MAX_VALUE)
        );

        tabbStatiscal.addTab("NGƯỜI HỌC", pnlLearner);

        tblPoint.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "MÃ NH", "HỌ VÀ TÊN", "ĐIỂM", "XẾP LOẠI"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane5.setViewportView(tblPoint);

        cbbCourse.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " " }));
        cbbCourse.setSelectedIndex(-1);
        cbbCourse.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbbCourseItemStateChanged(evt);
            }
        });

        jLabel3.setText("Khóa học");

        btnSearch.setText("Tìm nhanh");

        javax.swing.GroupLayout pnlPointStcLayout = new javax.swing.GroupLayout(pnlPointStc);
        pnlPointStc.setLayout(pnlPointStcLayout);
        pnlPointStcLayout.setHorizontalGroup(
            pnlPointStcLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 983, Short.MAX_VALUE)
            .addGroup(pnlPointStcLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(cbbCourse, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSearch)
                .addContainerGap())
        );
        pnlPointStcLayout.setVerticalGroup(
            pnlPointStcLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlPointStcLayout.createSequentialGroup()
                .addGap(0, 6, Short.MAX_VALUE)
                .addGroup(pnlPointStcLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbbCourse, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(btnSearch))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 485, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        tabbStatiscal.addTab("BẢNG ĐIỂM", pnlPointStc);

        tblSumPoint.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CHUYÊN ĐỀ", "TỐNG SỐ HV", "CAO NHẤT", "THẤP NHẤT", "ĐIỂM TRUNG BÌNH"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(tblSumPoint);

        javax.swing.GroupLayout pnlSumPointStcLayout = new javax.swing.GroupLayout(pnlSumPointStc);
        pnlSumPointStc.setLayout(pnlSumPointStcLayout);
        pnlSumPointStcLayout.setHorizontalGroup(
            pnlSumPointStcLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 983, Short.MAX_VALUE)
        );
        pnlSumPointStcLayout.setVerticalGroup(
            pnlSumPointStcLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 526, Short.MAX_VALUE)
        );

        tabbStatiscal.addTab("TỔNG HỢP ĐIỂM", pnlSumPointStc);

        tblRevenue.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CHUYÊN ĐỀ", "SỐ KHÓA", "SỐ HV", "DOANH THU", "HP CAO NHẤT", "HP THẤP NHẤT", "HP TRUNG BÌNH"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tblRevenue);

        jLabel2.setText("Năm");

        cbbYear.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbbYearItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout pnlRevenueLayout = new javax.swing.GroupLayout(pnlRevenue);
        pnlRevenue.setLayout(pnlRevenueLayout);
        pnlRevenueLayout.setHorizontalGroup(
            pnlRevenueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 983, Short.MAX_VALUE)
            .addGroup(pnlRevenueLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbbYear, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlRevenueLayout.setVerticalGroup(
            pnlRevenueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlRevenueLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlRevenueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlRevenueLayout.createSequentialGroup()
                        .addGap(0, 3, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlRevenueLayout.createSequentialGroup()
                        .addComponent(cbbYear)
                        .addGap(3, 3, 3)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 463, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        tabbStatiscal.addTab("DOANH THU", pnlRevenue);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tabbStatiscal)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(tabbStatiscal, javax.swing.GroupLayout.PREFERRED_SIZE, 556, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void tabbStatiscalStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tabbStatiscalStateChanged
        System.out.println(tabbStatiscal.getSelectedIndex());
        changeTab(tabbStatiscal.getSelectedIndex());
    }//GEN-LAST:event_tabbStatiscalStateChanged

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        AppStatus.OPEN_CONTROLLER.closeOpeningWindown();
    }//GEN-LAST:event_formWindowClosing

    private void cbbYearItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbbYearItemStateChanged
          revenueData = dao.getRevenue((int) cbbYear.getSelectedItem());
          fillRevenueTalbe();
    }//GEN-LAST:event_cbbYearItemStateChanged

    private void cbbCourseItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbbCourseItemStateChanged
        pointData = dao.getPoint(getMaKHSelected());
        fillPointTable();
    }//GEN-LAST:event_cbbCourseItemStateChanged

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
            java.util.logging.Logger.getLogger(StatisticalManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StatisticalManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(StatisticalManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StatisticalManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new StatisticalManager(0).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSearch;
    private javax.swing.JComboBox<String> cbbCourse;
    private javax.swing.JComboBox<String> cbbYear;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JPanel pnlLearner;
    private javax.swing.JPanel pnlPointStc;
    private javax.swing.JPanel pnlRevenue;
    private javax.swing.JPanel pnlSumPointStc;
    private javax.swing.JTabbedPane tabbStatiscal;
    private javax.swing.JTable tblLearnerStc;
    private javax.swing.JTable tblPoint;
    private javax.swing.JTable tblRevenue;
    private javax.swing.JTable tblSumPoint;
    // End of variables declaration//GEN-END:variables
}
