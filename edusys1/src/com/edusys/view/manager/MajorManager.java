/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edusys.view.manager;

import com.edusys.dao.MajorDAO;
import com.edusys.helper.DateHelper;
import com.edusys.helper.DialogHelper;
import com.edusys.helper.ShareHelper;
import com.edusys.model.Major;
import com.edusys.model.Major;
import com.raven.dialog.Message;
import com.raven.main.EdusysApp;
import com.raven.swing.table.EventAction;
import com.raven.swing.table.ModelAction;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ADMIN
 */
public class MajorManager extends javax.swing.JPanel {

    
    private int currentIndex = 0;
    private MajorDAO dao = new MajorDAO();
    List<Major> listAllMajor;

    private DefaultTableModel tblModel;
    private ModelAction data;
    EventAction eventAction;
    
    
    /**
     * Creates new form MajorManager2
     */
    public MajorManager() {
        initComponents();
        
        init();
        isView(true);
        
        
    }
    
    
    
    public void init() {
        
        eventAction = new EventAction() {
            @Override
            public void delete( ) {
                if (showMessage("Delete Row  : " + tblMajor.getValueAt(tblMajor.getSelectedRow(),1))) {
                    MajorManager.this.delete();
                } else {
                    
                }
            }

            @Override
            public void update( ) {
                //System.out.println("update" + tblMajor.getValueAt(tblMajor.getSelectedRow(),1));
                MajorManager.this.currentIndex = tblMajor.getSelectedRow();
                MajorManager.this.edit();
                isUpdate(true);
            }
        };
        data = new ModelAction(eventAction);
       
        
        tblMajor.fixTable(jScrollPane2);
        tblModel = (DefaultTableModel) tblMajor.getModel();
        tblMajor.setAutoscrolls(true);
        loadMajor();
        fillTable(listAllMajor);

    }
    
    
     private boolean showMessage(String message) {
        Message obj = new Message(EdusysApp.getFrames()[0], true);
        obj.showMessage(message);
        return obj.isOk();
    }
     
     void loadMajor(){
         listAllMajor = dao.select();
    }
     
      void filtMajor(String keyword){
        
        List<Major> list = new ArrayList<>();
        keyword = keyword.toUpperCase();
        for (int i = 0; i < listAllMajor.size();i++){
           
            if (listAllMajor.get(i).toString().toUpperCase().contains(keyword)){
              
                list.add(listAllMajor.get(i));
            }
        }
        fillTable(list);
       
    }
      
      void fillTable(List<Major> list) {
      
        tblModel.setRowCount(0);
        try {
            String keyword = txtSearch.getText();
        
             for (Major cd : list) {
                Object[] row = {
                    cd.getMaCD(),
                    cd.getName(),
                    cd.getFee(),
                    cd.getDuration(),
                    cd.getPicture(),
                    data
                };
                tblModel.addRow(row);
            }
        } catch (Exception e) {
            DialogHelper.alertError(this, "Lỗi truy vấn dữ liệu!");
        }
    }
      
      void insert() {
        Major model = getModel();
        try {
            dao.insert(model);
            loadMajor();
            this.fillTable(listAllMajor);
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
            loadMajor();
            this.fillTable(listAllMajor);
            DialogHelper.alert(this, "Cập nhật thành công!");
        } catch (Exception e) {
            e.printStackTrace();
            DialogHelper.alertError(this, "Cập nhật thất bại!");
        }
    }

 

    void delete() {
        if (DialogHelper.confirm(this, "Bạn thực sự muốn xóa người học này?")) {
            String malearner = txtMaCD.getText();
            try {
                dao.delete(malearner);
                loadMajor();
                this.fillTable(listAllMajor);
                this.clear();
                DialogHelper.alert(this, "Xóa thanh công!");
            } catch (HeadlessException e) {
                DialogHelper.alertError(this, "Xóa thất bại!");
            }
        }
    }

    void edit() {
        try {
            String malearner = (String) tblMajor.getValueAt(this.currentIndex, 0);
            Major model = dao.findById(malearner);
            if (model != null) {
                this.setModel(model);
                isView(true);
            }
        } catch (Exception e) {
            DialogHelper.alertError(this, "Lỗi truy vấn dữ liệu!");
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
     
      void isView(boolean is) {
       txtMaCD.setEditable(is);
       txtDescreption.setEditable(is);
       txtDuration.setEditable(is);
       txtFee.setEditable(is);
       txtNameMajor.setEditable(is);
       txtSearch.setEditable(is);
        
       // btnInsert.setEnabled(false);
        btnInsert.setVisible(false);
       // btnUpdate.setEnabled(false);
        btnUpdate.setVisible(false);
        
        
        boolean first = this.currentIndex > 0;
        boolean last = this.currentIndex < tblMajor.getRowCount() - 1;
        btnFirst.setEnabled(true && first);
        btnPrev.setEnabled(true && first);
        btnLast.setEnabled(true && last);
        btnNext.setEnabled(true && last);
    }
    
      void isUpdate(boolean is) {
        txtMaCD.setEditable(is);
       txtDescreption.setEditable(is);
       txtDuration.setEditable(is);
       txtFee.setEditable(is);
       txtNameMajor.setEditable(is);
       txtSearch.setEditable(is);
        
//        btnUpdate.setEnabled(true);
        //btnInsert.setVisible(is);
          btnUpdate.setVisible(is);
        
        //boolean first = this.currentIndex > 0;
       // boolean last = this.currentIndex < tblMajor.getRowCount() - 1;
//        btnFirst.setEnabled(false);
//        btnPrev.setEnabled(false);
//        btnLast.setEnabled(false);
//        btnNext.setEnabled(false);
    }
      
      void isInsert(boolean is){
         txtMaCD.setEditable(is);
       txtDescreption.setEditable(is);
       txtDuration.setEditable(is);
       txtFee.setEditable(is);
       txtNameMajor.setEditable(is);
       txtSearch.setEditable(is);
        
        btnInsert.setVisible(true);
        btnUpdate.setVisible(false);
        boolean first = this.currentIndex > 0;
        boolean last = this.currentIndex < tblMajor.getRowCount() - 1;
        btnFirst.setEnabled(false);
        btnPrev.setEnabled(false);
        btnLast.setEnabled(false);
        btnNext.setEnabled(false);
      }
      
      
       public void setTblSelected(int row){
        tblMajor.setRowSelectionInterval(row, row);
        tblMajor.scrollRectToVisible(new Rectangle(tblMajor.getCellRect(row, 0, true)));
       
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
        btnFirst = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblMajor = new com.raven.swing.table.Table();
        txtSearch = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnInsert = new com.raven.swing.HoverButton();
        btnNew = new com.raven.swing.HoverButton();
        btnUpdate = new com.raven.swing.HoverButton();
        jLabel4 = new javax.swing.JLabel();
        txtMaCD = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtNameMajor = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtDuration = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtFee = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtDescreption = new javax.swing.JTextArea();
        lblLogo = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(1600, 820));

        pnlUpdate3.setBackground(new java.awt.Color(255, 255, 255));
        pnlUpdate3.setMinimumSize(new java.awt.Dimension(1600, 838));

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

        tblMajor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã CD", "Tên CD", "Học phí", "Thời lượng", "Hình ảnh", "Tùy chỉnh"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblMajor.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tblMajor.getTableHeader().setReorderingAllowed(false);
        tblMajor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMajorMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblMajor);

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

        jLabel4.setText("Mã chuyên đề");

        jLabel5.setText("Tên chuyên đề");

        txtNameMajor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNameMajorActionPerformed(evt);
            }
        });

        jLabel6.setText("Thời lượng (giờ )");

        jLabel7.setText("Học phí");

        jLabel2.setText("Mô tả chuyên đề");

        txtDescreption.setColumns(20);
        txtDescreption.setRows(5);
        jScrollPane3.setViewportView(txtDescreption);

        lblLogo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lblLogo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblLogoMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnlUpdate3Layout = new javax.swing.GroupLayout(pnlUpdate3);
        pnlUpdate3.setLayout(pnlUpdate3Layout);
        pnlUpdate3Layout.setHorizontalGroup(
            pnlUpdate3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlUpdate3Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(pnlUpdate3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlUpdate3Layout.createSequentialGroup()
                        .addGroup(pnlUpdate3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlUpdate3Layout.createSequentialGroup()
                                .addGap(130, 130, 130)
                                .addComponent(lblLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel4)
                            .addComponent(txtMaCD, javax.swing.GroupLayout.PREFERRED_SIZE, 451, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(txtNameMajor, javax.swing.GroupLayout.PREFERRED_SIZE, 451, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(txtDuration, javax.swing.GroupLayout.PREFERRED_SIZE, 451, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)
                            .addComponent(txtFee, javax.swing.GroupLayout.PREFERRED_SIZE, 451, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnlUpdate3Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel2))
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnlUpdate3Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(btnInsert, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(5, 5, 5)
                                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(17, 17, 17)
                                .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlUpdate3Layout.createSequentialGroup()
                                .addGap(90, 90, 90)
                                .addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(8, 8, 8)
                                .addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(8, 8, 8)
                                .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(8, 8, 8)
                                .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(39, 39, 39)
                        .addGroup(pnlUpdate3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlUpdate3Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(10, 10, 10)
                                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1060, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlUpdate3Layout.setVerticalGroup(
            pnlUpdate3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlUpdate3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlUpdate3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlUpdate3Layout.createSequentialGroup()
                        .addComponent(lblLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(jLabel4)
                        .addGap(4, 4, 4)
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
                        .addComponent(txtFee, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jLabel2)
                        .addGap(4, 4, 4)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addGroup(pnlUpdate3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnInsert, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(65, 65, 65)
                        .addGroup(pnlUpdate3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlUpdate3Layout.createSequentialGroup()
                        .addGroup(pnlUpdate3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(17, 17, 17)
                        .addComponent(jScrollPane2)
                        .addGap(3, 3, 3)))
                .addGap(4, 4, 4)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlUpdate3, javax.swing.GroupLayout.PREFERRED_SIZE, 1576, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlUpdate3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(26, 26, 26))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        currentIndex = 0;
        setTblSelected(currentIndex);
        isUpdate(false);
        edit();
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        currentIndex--;
        setTblSelected(currentIndex);
        isUpdate(false);
        edit();
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        currentIndex++;
        setTblSelected(currentIndex);
        isUpdate(false);
        edit();
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        currentIndex = tblMajor.getRowCount() - 1;
        setTblSelected(currentIndex);
        isUpdate(false);
        edit();
    }//GEN-LAST:event_btnLastActionPerformed

    private void tblMajorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMajorMouseClicked
        currentIndex = tblMajor.rowAtPoint(evt.getPoint());

        if (this.currentIndex >= 0) {
            this.edit();
            if (evt.getClickCount() == 2) {
                isUpdate(true);

            }
        }
    }//GEN-LAST:event_tblMajorMouseClicked

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchActionPerformed

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        filtMajor(txtSearch.getText());
    }//GEN-LAST:event_txtSearchKeyReleased

    private void btnInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertActionPerformed
        insert();
        isView(true);
    }//GEN-LAST:event_btnInsertActionPerformed

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        clear();
        isInsert(true);
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        isUpdate(true);
        update();
        isView(true);
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void txtNameMajorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNameMajorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNameMajorActionPerformed

    private void lblLogoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLogoMouseClicked
        selectImage();
    }//GEN-LAST:event_lblLogoMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFirst;
    private com.raven.swing.HoverButton btnInsert;
    private javax.swing.JButton btnLast;
    private com.raven.swing.HoverButton btnNew;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private com.raven.swing.HoverButton btnUpdate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JPanel pnlUpdate3;
    private com.raven.swing.table.Table tblMajor;
    private javax.swing.JTextArea txtDescreption;
    private javax.swing.JTextField txtDuration;
    private javax.swing.JTextField txtFee;
    private javax.swing.JTextField txtMaCD;
    private javax.swing.JTextField txtNameMajor;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
