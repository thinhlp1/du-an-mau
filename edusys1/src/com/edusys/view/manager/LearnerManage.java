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
import com.raven.dialog.Message;
import com.raven.main.EdusysApp;
import com.raven.swing.InkwellButton;
import com.raven.swing.table.Action;
import com.raven.swing.table.EventAction;
import com.raven.swing.table.ModelAction;
import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ADMIN
 */
public class LearnerManage extends javax.swing.JPanel {
    
    
     private int currentIndex = 0;
    private LearnerDAO dao = new LearnerDAO();
    List<Learner> listAllLearner ;

    private DefaultTableModel tblModel;
    private ModelAction data;
    EventAction eventAction;
    /**
     * Creates new form LearnerManage2
     */
    public LearnerManage() {
        initComponents();
        init();
        
        isView(true);
    }

    @Override
    public String toString() {
        return "";
    }
    
    
    
    
    public void init() {
        
        eventAction = new EventAction() {
            @Override
            public void delete( ) {
                if (showMessage("Delete Row  : " + tblLearner.getValueAt(tblLearner.getSelectedRow(),1))) {
                    LearnerManage.this.delete();
                } else {
                    
                }
            }

            @Override
            public void update( ) {
                //System.out.println("update" + tblLearner.getValueAt(tblLearner.getSelectedRow(),1));
                LearnerManage.this.currentIndex = tblLearner.getSelectedRow();
                LearnerManage.this.edit();
                isUpdate(true);
            }
        };
        data = new ModelAction(eventAction);
       
        
        tblLearner.fixTable(jScrollPane2);
        tblModel = (DefaultTableModel) tblLearner.getModel();
         tblLearner.setAutoscrolls(true);
        loadLearner();
        fillTable(listAllLearner);

    }
    
     private boolean showMessage(String message) {
        Message obj = new Message(EdusysApp.getFrames()[0], true);
        obj.showMessage(message);
        return obj.isOk();
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
                    DateHelper.toString(learner.getRegistDay()),
                    data,
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
        cbbGender.setSelectedIndex(-1);
        
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
                isView(true);
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
    
    public void setTblSelected(int row){
        tblLearner.setRowSelectionInterval(row, row);
        tblLearner.scrollRectToVisible(new Rectangle(tblLearner.getCellRect(row, 0, true)));
       
    }

    void isView(boolean is) {
        txtMaNH.setEditable(false);
        btnInsert.setEnabled(false);
        txtName.setEditable(false);
        txtBirth.setEditable(false);
        txtEmail.setEditable(false);
        txtNumberPhone.setEditable(false);
        cbbGender.setEnabled(false);
        txtNote.setEditable(false);
        
       // btnInsert.setEnabled(false);
        btnInsert.setVisible(false);
       // btnUpdate.setEnabled(false);
        btnUpdate.setVisible(false);
        
        
        boolean first = this.currentIndex > 0;
        boolean last = this.currentIndex < tblLearner.getRowCount() - 1;
        btnFirst.setEnabled(true && first);
        btnPrev.setEnabled(true && first);
        btnLast.setEnabled(true && last);
        btnNext.setEnabled(true && last);
    }
    
      void isUpdate(boolean is) {
        txtMaNH.setEditable(is);
        btnInsert.setEnabled(is);
        txtName.setEditable(is);
        txtBirth.setEditable(is);
        txtEmail.setEditable(is);
        txtNumberPhone.setEditable(is);
        cbbGender.setEnabled(is);
        txtNote.setEditable(is);
        
//        btnUpdate.setEnabled(true);
        //btnInsert.setVisible(is);
          btnUpdate.setVisible(is);
        
        //boolean first = this.currentIndex > 0;
       // boolean last = this.currentIndex < tblLearner.getRowCount() - 1;
//        btnFirst.setEnabled(false);
//        btnPrev.setEnabled(false);
//        btnLast.setEnabled(false);
//        btnNext.setEnabled(false);
    }
      
      void isInsert(boolean is){
          txtMaNH.setEditable(is);
        btnInsert.setEnabled(is);
        txtName.setEditable(is);
        txtBirth.setEditable(is);
        txtEmail.setEditable(is);
        txtNumberPhone.setEditable(is);
        cbbGender.setEnabled(is);
        txtNote.setEditable(is);
        
        btnInsert.setVisible(true);
        btnUpdate.setVisible(false);
        boolean first = this.currentIndex > 0;
        boolean last = this.currentIndex < tblLearner.getRowCount() - 1;
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

        pnlUpdate3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        txtNumberPhone = new javax.swing.JTextField();
        jScrollPane7 = new javax.swing.JScrollPane();
        txtNote = new javax.swing.JTextArea();
        btnFirst = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        txtEmail = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        txtBirth = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        txtMaNH = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblLearner = new com.raven.swing.table.Table();
        txtSearch = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cbbGender = new com.raven.swing.Combobox();
        btnInsert = new com.raven.swing.HoverButton();
        btnNew = new com.raven.swing.HoverButton();
        btnUpdate = new com.raven.swing.HoverButton();

        pnlUpdate3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel5.setText("Ghi chú");

        jLabel19.setText("Điện thoại");

        txtNote.setColumns(20);
        txtNote.setRows(5);
        jScrollPane7.setViewportView(txtNote);

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

        jLabel21.setText("Địa chỉ mail");

        jLabel24.setText("Ngày sinh");

        jLabel25.setText("Mã người học");

        jLabel26.setText("Giới tính");

        jLabel27.setText("Họ tên");

        tblLearner.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã NH", "Họ Tên", "Giới tính", "Ngày sinh", "Điện thoại", "Email", "Mã NV", "Ngày Đk", "Tùy chọn"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblLearner.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tblLearner.getTableHeader().setReorderingAllowed(false);
        tblLearner.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblLearnerMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblLearner);

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

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setBackground(new java.awt.Color(204, 204, 255));
        jLabel1.setForeground(new java.awt.Color(102, 0, 255));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/3.png"))); // NOI18N
        jLabel1.setAutoscrolls(true);

        cbbGender.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Nam", "Nữ" }));
        cbbGender.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cbbGender.setLabeText("");
        cbbGender.setLineColor(new java.awt.Color(102, 102, 102));
        cbbGender.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbGenderActionPerformed(evt);
            }
        });

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

        javax.swing.GroupLayout pnlUpdate3Layout = new javax.swing.GroupLayout(pnlUpdate3);
        pnlUpdate3.setLayout(pnlUpdate3Layout);
        pnlUpdate3Layout.setHorizontalGroup(
            pnlUpdate3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlUpdate3Layout.createSequentialGroup()
                .addGroup(pnlUpdate3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlUpdate3Layout.createSequentialGroup()
                        .addGroup(pnlUpdate3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlUpdate3Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(pnlUpdate3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(pnlUpdate3Layout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addGroup(pnlUpdate3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtMaNH, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtBirth, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtNumberPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cbbGender, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(pnlUpdate3Layout.createSequentialGroup()
                                .addGap(55, 55, 55)
                                .addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(9, 9, 9)
                                .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(30, 30, 30))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlUpdate3Layout.createSequentialGroup()
                        .addGroup(pnlUpdate3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnlUpdate3Layout.createSequentialGroup()
                                .addComponent(btnInsert, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(5, 5, 5)
                                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(5, 5, 5)
                                .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)))
                .addGroup(pnlUpdate3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlUpdate3Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1180, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(32, Short.MAX_VALUE))
        );
        pnlUpdate3Layout.setVerticalGroup(
            pnlUpdate3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlUpdate3Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(pnlUpdate3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtSearch)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlUpdate3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlUpdate3Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel25)
                        .addGap(4, 4, 4)
                        .addComponent(txtMaNH, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(jLabel27)
                        .addGap(4, 4, 4)
                        .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(jLabel24)
                        .addGap(4, 4, 4)
                        .addComponent(txtBirth, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(jLabel21)
                        .addGap(4, 4, 4)
                        .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(jLabel19)
                        .addGap(4, 4, 4)
                        .addComponent(txtNumberPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(jLabel26)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbbGender, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5)
                        .addGap(4, 4, 4)
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlUpdate3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnInsert, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlUpdate3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnFirst, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnPrev, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnNext, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE))
                .addGap(42, 42, 42))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlUpdate3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlUpdate3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        clear();
        isInsert(true);
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        isUpdate(true);
        update();
        isView(true);
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertActionPerformed
        insert();
        isView(true);
    }//GEN-LAST:event_btnInsertActionPerformed

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchActionPerformed

    private void tblLearnerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblLearnerMouseClicked
          currentIndex = tblLearner.rowAtPoint(evt.getPoint());
        
           if (this.currentIndex >= 0) {
                this.edit();
          if (evt.getClickCount() == 2) {
              isUpdate(true);

           
                

            }
        }
       
        
    }//GEN-LAST:event_tblLearnerMouseClicked

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        currentIndex = tblLearner.getRowCount() - 1;
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

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        filtLearner(txtSearch.getText());
    }//GEN-LAST:event_txtSearchKeyReleased

    private void cbbGenderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbGenderActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbbGenderActionPerformed


       
   
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFirst;
    private com.raven.swing.HoverButton btnInsert;
    private javax.swing.JButton btnLast;
    private com.raven.swing.HoverButton btnNew;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private com.raven.swing.HoverButton btnUpdate;
    private com.raven.swing.Combobox cbbGender;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JPanel pnlUpdate3;
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
