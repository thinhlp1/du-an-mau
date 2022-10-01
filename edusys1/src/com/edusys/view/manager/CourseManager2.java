/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edusys.view.manager;

import com.edusys.dao.CourseDAO;
import com.edusys.dao.CourseDAO;
import com.edusys.dao.MajorDAO;
import com.edusys.helper.AppStatus;
import com.edusys.helper.DateHelper;
import com.edusys.helper.DialogHelper;
import com.edusys.model.Course;
import com.edusys.model.Course;
import com.edusys.model.Major;
import com.raven.dialog.Message;
import com.raven.main.EdusysApp;
import com.raven.swing.datechooser.DateChooser;
import com.raven.swing.table.EventAction;
import com.raven.swing.table.ModelAction;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ADMIN
 */
public class CourseManager2 extends javax.swing.JPanel {

   
      private int currentIndex = 0;
     private CourseDAO dao = new CourseDAO();
    private MajorDAO mjDAO = new MajorDAO();
    List<Course> listAllCourse;

    private DefaultTableModel tblModel;
    private ModelAction data;
    EventAction eventAction;
    
    private DateChooser dtChooser = new DateChooser();
    private Integer maKH;
    
    public CourseManager2() {
        initComponents();
    }
    
    
      public void init() {
        
        eventAction = new EventAction() {
            @Override
            public void delete( ) {
                if (showMessage("Delete Row  : " + tblCourse.getValueAt(tblCourse.getSelectedRow(),1))) {
                    CourseManager2.this.delete();
                } else {
                    
                }
            }

            @Override
            public void update( ) {
                //System.out.println("update" + tblCourse.getValueAt(tblCourse.getSelectedRow(),1));
                CourseManager2.this.currentIndex = tblCourse.getSelectedRow();
                CourseManager2.this.edit();
                isUpdate(true);
            }
        };
        data = new ModelAction(eventAction);
       
        
        tblCourse.fixTable(jScrollPane2);
        tblModel = (DefaultTableModel) tblCourse.getModel();
        tblCourse.setAutoscrolls(true);
        loadCourse();
        fillTable(listAllCourse);
        
        dtChooser.setTextRefernce(txtOpeningDay);
        
        
        

    }
      
      
        private boolean showMessage(String message) {
        Message obj = new Message(EdusysApp.getFrames()[0], true);
        obj.showMessage(message);
        return obj.isOk();
    }
    
    
    
    void loadCourse(){
         listAllCourse = dao.select();
    }
    
    void filtCourse(String keyword){
         List<Course> list = new ArrayList<>();
        keyword = keyword.toUpperCase();
        for (int i = 0; i < listAllCourse.size();i++){
           
            if (listAllCourse.get(i).info().toUpperCase().contains(keyword)){
              
                list.add(listAllCourse.get(i));
            }
        }
        fillTable(list);
       
    }

    void fillTable(List<Course> list) {
      
        tblModel.setRowCount(0);
        try {
            String keyword = txtSearch.getText();
        
             for (Course kh : list) {
                Object[] row = {
                    kh.getMaKH(),
                    kh.getMaCD(),
                    kh.getDuration(),
                    kh.getFee(),
                    DateHelper.toString(kh.getOpeningDay(),"dd/MM/yyyy"),
                    kh.getMaNV(),
                    DateHelper.toString(kh.getCreateDay(),"dd/MM/yyyy"),
                    data
                };
                tblModel.addRow(row);
            }
        } catch (Exception e) {
            DialogHelper.alertError(this, "Lỗi truy vấn dữ liệu!");
        }
    }
    
    
   

    void insert() {
        Course model = getModel();
        try {
            dao.insert(model);
            loadCourse();
            this.fillTable(listAllCourse);
            this.clear();
            DialogHelper.alert(this, "Thêm mới thành công!");
        } catch (Exception e) {
            DialogHelper.alertError(this, "Thêm mới thất bại!");
        }
    }

    void update() {
        Course model = getModel();
        try {
            dao.update(model);
            loadCourse();
            this.fillTable(listAllCourse);
            DialogHelper.alert(this, "Cập nhật thành công!");
        } catch (Exception e) {
            e.printStackTrace();
            DialogHelper.alertError(this, "Cập nhật thất bại!");
        }
    }

    void clear() {
         txtDuration.setText("");
        txtFee.setText("");
        txtNote.setText("");
        txtOpeningDay.setText("");
        cbbMajor.setSelectedIndex(-1);
        
        txtCreateDay.setText(DateHelper.toString(DateHelper.now(), "dd/MM/yyyy"));
        txtMaNV.setText(AppStatus.USER.getEmpID());
        
        maKH = null;
        
    }

    void delete() {
        if (DialogHelper.confirm(this, "Bạn thực sự muốn xóa người học này?")) {
            String makh = maKH.toString();
            try {
                dao.delete(makh);
                 loadCourse();
                this.fillTable(listAllCourse);
                this.clear();
                DialogHelper.alert(this, "Xóa thanh công!");
            } catch (HeadlessException e) {
                DialogHelper.alertError(this, "Xóa thất bại!");
            }
        }
    }

    void edit() {
        try {
            String makh = (String) tblCourse.getValueAt(this.currentIndex, 0);
            Course model = dao.findById(makh);
            if (model != null) {
                this.setModel(model);
                isView(true);
            }
        } catch (Exception e) {
            DialogHelper.alertError(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    void setModel(Course model) {
        txtFee.setText(String.valueOf(model.getFee()));
        txtDuration.setText(String.valueOf(model.getDuration()));
        cbbMajor.setSelectedItem(mjDAO.findById(String.valueOf(model.getMaCD())));
        txtOpeningDay.setText(model.getOpeningDay()!= null ? DateHelper.toString(model.getOpeningDay(),"dd/MM/yyyy"): "");
        txtMaNV.setText(model.getMaNV());
        txtCreateDay.setText(DateHelper.toString(model.getCreateDay(), "dd/MM/yyyy"));
        txtNote.setText(model.getNote());
        
        maKH = model.getMaKH();
    }

    Course getModel() {
         Course model = new Course();
        Major major = (Major) cbbMajor.getSelectedItem();
        model.setMaCD(major.getMaCD());
        model.setOpeningDay(DateHelper.toDate(txtOpeningDay.getText()));
        model.setFee(Double.valueOf(txtFee.getText()));
        model.setDuration(Integer.valueOf(txtDuration.getText()));
        model.setNote(txtNote.getText());
        model.setMaNV(AppStatus.USER.getEmpID());
        model.setCreateDay(DateHelper.toDate(txtCreateDay.getText()));
        model.setMaKH(this.maKH != null ? this.maKH : -1);

        return model;
    }
    
    public void setTblSelected(int row){
        tblCourse.setRowSelectionInterval(row, row);
        tblCourse.scrollRectToVisible(new Rectangle(tblCourse.getCellRect(row, 0, true)));
       
    }
    
    
    void isView(boolean is) {
        cbbMajor.setEditable(false);
        txtFee.setEditable(false);
        txtDuration.setEditable(false);
        txtOpeningDay.setEditable(false);
        txtNote.setEditable(false);
        
        
          btnCalendar.setVisible(false);
        btnDelete.setVisible(false);
       // btnInsert.setEnabled(false);
        btnInsert.setVisible(false);
       // btnUpdate.setEnabled(false);
        btnUpdate.setVisible(false);
        
        
        boolean first = this.currentIndex > 0;
        boolean last = this.currentIndex < tblCourse.getRowCount() - 1;
        btnFirst.setEnabled(true && first);
        btnPrev.setEnabled(true && first);
        btnLast.setEnabled(true && last);
        btnNext.setEnabled(true && last);
    }
    
      void isUpdate(boolean is) {
        cbbMajor.setEditable(true);
        txtFee.setEditable(true);
        txtDuration.setEditable(true);
        txtOpeningDay.setEditable(true);
        txtNote.setEditable(true);
        

          btnUpdate.setVisible(true);
          btnDelete.setVisible(true);
            btnCalendar.setVisible(true);
        

    }
      
      void isInsert(boolean is){
        cbbMajor.setEditable(true);
        txtFee.setEditable(true);
        txtDuration.setEditable(true);
        txtOpeningDay.setEditable(true);
        txtNote.setEditable(true);
        
        btnCalendar.setVisible(true);
        
        btnInsert.setVisible(true);
        btnUpdate.setVisible(false);
        btnDelete.setVisible(false);
        boolean first = this.currentIndex > 0;
        boolean last = this.currentIndex < tblCourse.getRowCount() - 1;
        btnFirst.setEnabled(false);
        btnPrev.setEnabled(false);
        btnLast.setEnabled(false);
        btnNext.setEnabled(false);
      }
    
    
    
      public void showCalendar(){
          dtChooser.showPopup();
      }
      
        void selectComboBox() {
       if (cbbMajor.getSelectedIndex() != -1){
            Major chuyenDe = (Major) cbbMajor.getSelectedItem();
        txtDuration.setText(String.valueOf(chuyenDe.getDuration()));
        txtFee.setText(String.valueOf(chuyenDe.getFee()));
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

        pnlUpdate3 = new javax.swing.JPanel();
        btnCalendar = new javax.swing.JButton();
        btnFirst = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblCourse = new com.raven.swing.table.Table();
        txtSearch = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnInsert = new com.raven.swing.HoverButton();
        btnNew = new com.raven.swing.HoverButton();
        btnUpdate = new com.raven.swing.HoverButton();
        btnDelete = new com.raven.swing.HoverButton();
        jLabel12 = new javax.swing.JLabel();
        cbbMajor = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        txtOpeningDay = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtFee = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtDuration = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtMaNV = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtCreateDay = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtNote = new javax.swing.JTextArea();

        pnlUpdate3.setBackground(new java.awt.Color(255, 255, 255));

        btnCalendar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/assets/Calendar.png"))); // NOI18N
        btnCalendar.setContentAreaFilled(false);
        btnCalendar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCalendarActionPerformed(evt);
            }
        });

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

        tblCourse.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã KH", "Chuyên đề", "Thời lượng", "Học phí", "Khai giảng", "Người tạo", "Ngày tạo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblCourse.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tblCourse.getTableHeader().setReorderingAllowed(false);
        tblCourse.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCourseMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblCourse);

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
        btnUpdate.setColor(new java.awt.Color(204, 255, 204));
        btnUpdate.setColorClick(new java.awt.Color(0, 255, 0));
        btnUpdate.setColorOver(new java.awt.Color(51, 255, 51));
        btnUpdate.setLabelColor(new java.awt.Color(0, 0, 0));
        btnUpdate.setLableColorClick(java.awt.Color.white);
        btnUpdate.setRadius(12);
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
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

        jLabel12.setText("Chuyên đề");

        cbbMajor.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbbMajorItemStateChanged(evt);
            }
        });
        cbbMajor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbMajorActionPerformed(evt);
            }
        });

        jLabel11.setText("Ngày khai giảng");

        jLabel6.setText("Học phí");

        txtFee.setEditable(false);

        jLabel9.setText("Thời lượng (giờ )");

        txtDuration.setEditable(false);

        jLabel7.setText("Người tạo");

        txtMaNV.setEditable(false);
        txtMaNV.setEnabled(false);

        jLabel10.setText("Ngày tạo");

        txtCreateDay.setEditable(false);
        txtCreateDay.setEnabled(false);
        txtCreateDay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCreateDayActionPerformed(evt);
            }
        });

        jLabel2.setText("Ghi chú");

        txtNote.setColumns(20);
        txtNote.setRows(5);
        jScrollPane3.setViewportView(txtNote);

        javax.swing.GroupLayout pnlUpdate3Layout = new javax.swing.GroupLayout(pnlUpdate3);
        pnlUpdate3.setLayout(pnlUpdate3Layout);
        pnlUpdate3Layout.setHorizontalGroup(
            pnlUpdate3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlUpdate3Layout.createSequentialGroup()
                .addGroup(pnlUpdate3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlUpdate3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlUpdate3Layout.createSequentialGroup()
                            .addGroup(pnlUpdate3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(pnlUpdate3Layout.createSequentialGroup()
                                    .addContainerGap()
                                    .addGroup(pnlUpdate3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel7)
                                        .addComponent(txtMaNV)))
                                .addGroup(pnlUpdate3Layout.createSequentialGroup()
                                    .addGap(10, 10, 10)
                                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(pnlUpdate3Layout.createSequentialGroup()
                                    .addGap(55, 55, 55)
                                    .addGroup(pnlUpdate3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(pnlUpdate3Layout.createSequentialGroup()
                                            .addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(9, 9, 9)
                                            .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGroup(pnlUpdate3Layout.createSequentialGroup()
                                    .addContainerGap()
                                    .addGroup(pnlUpdate3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtDuration, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(pnlUpdate3Layout.createSequentialGroup()
                                            .addComponent(txtOpeningDay, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(btnCalendar, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGap(11, 11, 11))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlUpdate3Layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(pnlUpdate3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlUpdate3Layout.createSequentialGroup()
                                    .addComponent(btnInsert, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(5, 5, 5)
                                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(172, 172, 172))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlUpdate3Layout.createSequentialGroup()
                                    .addComponent(txtCreateDay, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(27, 27, 27)))))
                    .addGroup(pnlUpdate3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(pnlUpdate3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(jLabel2)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnlUpdate3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(cbbMajor, javax.swing.GroupLayout.Alignment.LEADING, 0, 350, Short.MAX_VALUE)
                                .addComponent(txtFee, javax.swing.GroupLayout.Alignment.LEADING)))))
                .addGap(8, 8, 8)
                .addGroup(pnlUpdate3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlUpdate3Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1172, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlUpdate3Layout.setVerticalGroup(
            pnlUpdate3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlUpdate3Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(pnlUpdate3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtSearch)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE))
                .addGap(22, 22, 22)
                .addComponent(jLabel12)
                .addGap(4, 4, 4)
                .addGroup(pnlUpdate3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlUpdate3Layout.createSequentialGroup()
                        .addComponent(cbbMajor, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(jLabel6)
                        .addGap(4, 4, 4)
                        .addComponent(txtFee, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21)
                        .addComponent(jLabel9)
                        .addGap(4, 4, 4)
                        .addComponent(txtDuration, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel11)
                        .addGap(4, 4, 4)
                        .addGroup(pnlUpdate3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtOpeningDay, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCalendar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(jLabel10)
                        .addGap(4, 4, 4)
                        .addComponent(txtCreateDay, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(pnlUpdate3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnInsert, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnlUpdate3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(25, 25, 25)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlUpdate3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnFirst, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnPrev, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnNext, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(51, Short.MAX_VALUE))
                    .addGroup(pnlUpdate3Layout.createSequentialGroup()
                        .addComponent(jScrollPane2)
                        .addGap(51, 51, 51))))
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
        currentIndex = tblCourse.getRowCount() - 1;
        setTblSelected(currentIndex);
        isUpdate(false);
        edit();
    }//GEN-LAST:event_btnLastActionPerformed

    private void tblCourseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCourseMouseClicked
        currentIndex = tblCourse.rowAtPoint(evt.getPoint());

        if (this.currentIndex >= 0) {
            this.edit();
            if (evt.getClickCount() == 2) {
                isUpdate(true);

            }
        }

    }//GEN-LAST:event_tblCourseMouseClicked

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchActionPerformed

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        filtCourse(txtSearch.getText());
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

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        delete();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void cbbMajorItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbbMajorItemStateChanged
        selectComboBox();
    }//GEN-LAST:event_cbbMajorItemStateChanged

    private void cbbMajorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbMajorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbbMajorActionPerformed

    private void txtCreateDayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCreateDayActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCreateDayActionPerformed

    private void btnCalendarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCalendarActionPerformed
        showCalendar();
    }//GEN-LAST:event_btnCalendarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCalendar;
    private com.raven.swing.HoverButton btnDelete;
    private javax.swing.JButton btnFirst;
    private com.raven.swing.HoverButton btnInsert;
    private javax.swing.JButton btnLast;
    private com.raven.swing.HoverButton btnNew;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private com.raven.swing.HoverButton btnUpdate;
    private javax.swing.JComboBox<String> cbbMajor;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPanel pnlUpdate3;
    private com.raven.swing.table.Table tblCourse;
    private javax.swing.JTextField txtCreateDay;
    private javax.swing.JTextField txtDuration;
    private javax.swing.JTextField txtFee;
    private javax.swing.JTextField txtMaNV;
    private javax.swing.JTextArea txtNote;
    private javax.swing.JTextField txtOpeningDay;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
