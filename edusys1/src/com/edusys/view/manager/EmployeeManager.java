/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edusys.view.manager;

import com.edusys.dao.EmployeeDAO;
import com.edusys.helper.DateHelper;
import com.edusys.helper.DialogHelper;
import com.edusys.model.Employee;
import com.edusys.model.Employee;


import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ADMIN
 */
public class EmployeeManager extends javax.swing.JPanel {
    
    private int currentIndex = 0;
    private EmployeeDAO dao = new EmployeeDAO();
    private DefaultTableModel tblModel;
    private List<Employee> listAllEmployee;


    /**
     * Creates new form EmployeeManager2
     */
    public EmployeeManager() {
        initComponents();
         init();
        
        isView(true);
    }
    
    public void init() {
        
     
        tblEmployee.fixTable(jScrollPane2);
        tblModel = (DefaultTableModel) tblEmployee.getModel();
        tblEmployee.setAutoscrolls(true);
        loadEmployee();
        fillTable(listAllEmployee);
        
    }
    
    
    void loadEmployee() {
        listAllEmployee = dao.select();
    }
    
    void filtEmployee(String keyword) {
        List<Employee> list = new ArrayList<>();
        keyword = keyword.toUpperCase();
        for (int i = 0; i < listAllEmployee.size(); i++) {
            
            if (listAllEmployee.get(i).toString().toUpperCase().contains(keyword)) {
                
                list.add(listAllEmployee.get(i));
            }
        }
        fillTable(list);
        
    }
    
    void fillTable(List<Employee> list) {
        
        tblModel.setRowCount(0);
        try {
            String keyword = txtSearch.getText();
            
            for (Employee emp : list) {
                Object[] row = {
                    emp.getEmpID(),
                    emp.getPassword(),
                    emp.getName(),
                    emp.isLore() ? "Trưởng phòng" : "Nhân viên",
                 
                };
                tblModel.addRow(row);
            }
        } catch (Exception e) {
            DialogHelper.alertError(this, "Lỗi truy vấn dữ liệu!");
        }
    }
//    
//    void load() {
//        tblModel.setRowCount(0);
//        try {
//            List<Employee> list = dao.select();
//            for (Employee emp : list) {
//                Object[] row = {
//                    emp.getEmpID(),
//                    emp.getPassword(),
//                    emp.getName(),
//                    emp.isLore() ? "Trưởng phòng" : "Nhân viên"
//                };
//                tblModel.addRow(row);
//            }
//        } catch (Exception e) {
//            DialogHelper.alertError(this, "Có lỗi xảy ra. Vui lòng liên hệ với kỹ thuật hỗ trợ!");
//        }
//    }
    
    void insert() {
        Employee model = getModel();
        String confirm = new String(txtComfirmPass.getPassword());
        if (confirm.equals(model.getPassword())) {
            try {
                boolean rs = dao.insert(model);
                this.fillTable(listAllEmployee);
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
        
    }
    
    void edit() {
        try {
            String manv = (String) tblEmployee.getValueAt(this.currentIndex, 0);
            Employee model = dao.findById(manv);
            if (model != null) {
                this.setModel(model);
                isView(true);
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
                this.fillTable(listAllEmployee);
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
                this.fillTable(listAllEmployee);
                clear();
                DialogHelper.alert(this, "Cập nhật thành công!");
            } catch (Exception e) {
                DialogHelper.alertError(this, "Cập nhật thất bại!");
            }
        }
    }
    
    public void setTblSelected(int row) {
        tblEmployee.setRowSelectionInterval(row, row);
        tblEmployee.scrollRectToVisible(new Rectangle(tblEmployee.getCellRect(row, 0, true)));
        
    }
    
    void isView(boolean is) {
        txtMaNV.setEditable(false);     
        txtName.setEditable(false);
        txtComfirmPass.setEnabled(false);
        txtPass.setEditable(false);

        // btnInsert.setEnabled(false);
        btnInsert.setVisible(false);
        // btnUpdate.setEnabled(false);
        btnUpdate.setVisible(false);
          btnDelete.setVisible(false);
        
        boolean first = this.currentIndex > 0;
        boolean last = this.currentIndex < tblEmployee.getRowCount() - 1;
        btnFirst.setEnabled(true && first);
        btnPrev.setEnabled(true && first);
        btnLast.setEnabled(true && last);
        btnNext.setEnabled(true && last);
    }
    
    void isUpdate(boolean is) {
         txtMaNV.setEditable(is);     
        txtName.setEditable(is);
        txtComfirmPass.setEnabled(is);
        txtPass.setEditable(is);


        btnUpdate.setVisible(is);
        btnDelete.setVisible(true);


    }
    
    void isInsert(boolean is) {
         txtMaNV.setEditable(is);     
        txtName.setEditable(is);
        txtComfirmPass.setEnabled(is);
        txtPass.setEditable(is);
        
        btnInsert.setVisible(true);
        btnUpdate.setVisible(false);
          btnDelete.setVisible(true);
     
        btnFirst.setEnabled(false);
        btnPrev.setEnabled(false);
        btnLast.setEnabled(false);
        btnNext.setEnabled(false);
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
        pnlUpdate3 = new javax.swing.JPanel();
        btnFirst = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblEmployee = new com.ui.swing.table.Table();
        txtSearch = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        btnInsert = new com.ui.swing.HoverButton();
        btnNew = new com.ui.swing.HoverButton();
        btnUpdate = new com.ui.swing.HoverButton();
        txtMaNV = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        txtPass = new javax.swing.JPasswordField();
        jLabel28 = new javax.swing.JLabel();
        txtComfirmPass = new javax.swing.JPasswordField();
        jLabel30 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        rdbAdmin = new javax.swing.JRadioButton();
        rdbEmployee = new javax.swing.JRadioButton();
        btnDelete = new com.ui.swing.HoverButton();

        setPreferredSize(new java.awt.Dimension(1600, 838));

        pnlUpdate3.setBackground(new java.awt.Color(255, 255, 255));
        pnlUpdate3.setMinimumSize(new java.awt.Dimension(1600, 838));
        pnlUpdate3.setPreferredSize(new java.awt.Dimension(1600, 838));

        btnFirst.setBackground(new java.awt.Color(255, 255, 255));
        btnFirst.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnFirst.setForeground(new java.awt.Color(255, 51, 0));
        btnFirst.setText("|<");
        btnFirst.setBorder(null);
        btnFirst.setBorderPainted(false);
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });

        btnPrev.setBackground(new java.awt.Color(255, 255, 255));
        btnPrev.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnPrev.setForeground(new java.awt.Color(255, 51, 0));
        btnPrev.setText("<<");
        btnPrev.setBorder(null);
        btnPrev.setBorderPainted(false);
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });

        btnNext.setBackground(new java.awt.Color(255, 255, 255));
        btnNext.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnNext.setForeground(new java.awt.Color(255, 51, 0));
        btnNext.setText(">>");
        btnNext.setBorder(null);
        btnNext.setBorderPainted(false);
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnLast.setBackground(new java.awt.Color(255, 255, 255));
        btnLast.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnLast.setForeground(new java.awt.Color(255, 51, 0));
        btnLast.setText(">|");
        btnLast.setBorder(null);
        btnLast.setBorderPainted(false);
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });

        tblEmployee.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã NV", "Họ Tên", "Mật Khẩu", "Vai Trò"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblEmployee.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tblEmployee.getTableHeader().setReorderingAllowed(false);
        tblEmployee.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblEmployeeMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblEmployee);

        txtSearch.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });

        jLabel1.setBackground(new java.awt.Color(204, 204, 255));
        jLabel1.setForeground(new java.awt.Color(102, 0, 255));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/assets/Search.png"))); // NOI18N
        jLabel1.setAutoscrolls(true);

        btnInsert.setText("Thêm");
        btnInsert.setBorderColor(new java.awt.Color(255, 255, 255));
        btnInsert.setColor(new java.awt.Color(225, 255, 226));
        btnInsert.setColorClick(new java.awt.Color(0, 204, 0));
        btnInsert.setColorOver(new java.awt.Color(0, 204, 0));
        btnInsert.setLableColorClick(java.awt.Color.white);
        btnInsert.setRadius(12);
        btnInsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertActionPerformed(evt);
            }
        });

        btnNew.setText("Mới");
        btnNew.setBorderColor(new java.awt.Color(153, 153, 153));
        btnNew.setColorClick(new java.awt.Color(102, 102, 102));
        btnNew.setColorOver(new java.awt.Color(102, 102, 102));
        btnNew.setLableColorClick(java.awt.Color.white);
        btnNew.setRadius(12);
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });

        btnUpdate.setText("Lưu");
        btnUpdate.setBorderColor(new java.awt.Color(255, 255, 255));
        btnUpdate.setColor(new java.awt.Color(255, 204, 204));
        btnUpdate.setColorClick(new java.awt.Color(255, 51, 0));
        btnUpdate.setColorOver(new java.awt.Color(255, 51, 0));
        btnUpdate.setLabelColor(new java.awt.Color(0, 0, 0));
        btnUpdate.setLableColorClick(java.awt.Color.white);
        btnUpdate.setRadius(12);
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        txtMaNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaNVActionPerformed(evt);
            }
        });

        jLabel25.setText("Mã nhân viên");

        jLabel27.setText("Mật khẩu");

        txtPass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPassActionPerformed(evt);
            }
        });

        jLabel28.setText("Xác nhận mật khẩu");

        jLabel30.setText("Họ tên");

        txtName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNameActionPerformed(evt);
            }
        });

        jLabel29.setText("Vai trò");

        btngLore.add(rdbAdmin);
        rdbAdmin.setText("Trưởng phòng");
        rdbAdmin.setContentAreaFilled(false);
        rdbAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbAdminActionPerformed(evt);
            }
        });

        btngLore.add(rdbEmployee);
        rdbEmployee.setText("Nhân viên");
        rdbEmployee.setContentAreaFilled(false);
        rdbEmployee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbEmployeeActionPerformed(evt);
            }
        });

        btnDelete.setText("Xóa");
        btnDelete.setBorderColor(new java.awt.Color(255, 255, 255));
        btnDelete.setColor(new java.awt.Color(255, 204, 204));
        btnDelete.setColorClick(new java.awt.Color(255, 51, 0));
        btnDelete.setColorOver(new java.awt.Color(255, 51, 0));
        btnDelete.setLabelColor(new java.awt.Color(0, 0, 0));
        btnDelete.setLableColorClick(java.awt.Color.white);
        btnDelete.setRadius(12);
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlUpdate3Layout = new javax.swing.GroupLayout(pnlUpdate3);
        pnlUpdate3.setLayout(pnlUpdate3Layout);
        pnlUpdate3Layout.setHorizontalGroup(
            pnlUpdate3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlUpdate3Layout.createSequentialGroup()
                .addGap(380, 380, 380)
                .addComponent(jLabel1)
                .addGap(20, 20, 20)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnlUpdate3Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(pnlUpdate3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtComfirmPass, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlUpdate3Layout.createSequentialGroup()
                        .addComponent(rdbAdmin)
                        .addGap(21, 21, 21)
                        .addComponent(rdbEmployee))
                    .addGroup(pnlUpdate3Layout.createSequentialGroup()
                        .addComponent(btnInsert, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7)
                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlUpdate3Layout.createSequentialGroup()
                        .addGap(80, 80, 80)
                        .addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)
                        .addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)
                        .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)
                        .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(30, 30, 30)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1200, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pnlUpdate3Layout.setVerticalGroup(
            pnlUpdate3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlUpdate3Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(pnlUpdate3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addGroup(pnlUpdate3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlUpdate3Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel25)
                        .addGap(4, 4, 4)
                        .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(jLabel27)
                        .addGap(4, 4, 4)
                        .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel28)
                        .addGap(4, 4, 4)
                        .addComponent(txtComfirmPass, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel30)
                        .addGap(4, 4, 4)
                        .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(jLabel29)
                        .addGap(14, 14, 14)
                        .addGroup(pnlUpdate3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rdbAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rdbEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(9, 9, 9)
                        .addGroup(pnlUpdate3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnInsert, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(75, 75, 75)
                        .addGroup(pnlUpdate3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlUpdate3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlUpdate3, javax.swing.GroupLayout.PREFERRED_SIZE, 825, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        delete();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void rdbEmployeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbEmployeeActionPerformed

    }//GEN-LAST:event_rdbEmployeeActionPerformed

    private void rdbAdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbAdminActionPerformed

    }//GEN-LAST:event_rdbAdminActionPerformed

    private void txtNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNameActionPerformed

    }//GEN-LAST:event_txtNameActionPerformed

    private void txtPassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPassActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPassActionPerformed

    private void txtMaNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaNVActionPerformed

    }//GEN-LAST:event_txtMaNVActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        isUpdate(true);
        update();
        isView(true);
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        clear();
        isInsert(true);
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertActionPerformed
        insert();
        isView(true);
    }//GEN-LAST:event_btnInsertActionPerformed

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        filtEmployee(txtSearch.getText());
    }//GEN-LAST:event_txtSearchKeyReleased

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchActionPerformed

    private void tblEmployeeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblEmployeeMouseClicked
        currentIndex = tblEmployee.rowAtPoint(evt.getPoint());

        if (this.currentIndex >= 0) {
            this.edit();
            if (evt.getClickCount() == 2) {
                isUpdate(true);

            }
        }
    }//GEN-LAST:event_tblEmployeeMouseClicked

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        currentIndex = tblEmployee.getRowCount() - 1;
        setTblSelected(currentIndex);
        isUpdate(false);
        edit();
    }//GEN-LAST:event_btnLastActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        currentIndex++;
        setTblSelected(currentIndex);
        isUpdate(false);
        edit();
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        currentIndex--;
        setTblSelected(currentIndex);
        isUpdate(false);
        edit();
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        currentIndex = 0;
        setTblSelected(currentIndex);
        isUpdate(false);
        edit();
    }//GEN-LAST:event_btnFirstActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.ui.swing.HoverButton btnDelete;
    private javax.swing.JButton btnFirst;
    private com.ui.swing.HoverButton btnInsert;
    private javax.swing.JButton btnLast;
    private com.ui.swing.HoverButton btnNew;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private com.ui.swing.HoverButton btnUpdate;
    private javax.swing.ButtonGroup btngLore;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel pnlUpdate3;
    private javax.swing.JRadioButton rdbAdmin;
    private javax.swing.JRadioButton rdbEmployee;
    private com.ui.swing.table.Table tblEmployee;
    private javax.swing.JPasswordField txtComfirmPass;
    private javax.swing.JTextField txtMaNV;
    private javax.swing.JTextField txtName;
    private javax.swing.JPasswordField txtPass;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
