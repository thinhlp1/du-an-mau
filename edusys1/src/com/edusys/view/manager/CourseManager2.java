/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edusys.view.manager;

import com.edusys.dao.CourseDAO;
import com.edusys.dao.CourseDAO;
import com.edusys.dao.LearnerDAO;
import com.edusys.dao.MajorDAO;
import com.edusys.dao.TraineeDAO;
import com.edusys.helper.AppStatus;
import com.edusys.helper.DateHelper;
import com.edusys.helper.DialogHelper;
import com.edusys.model.Course;
import com.edusys.model.Course;
import com.edusys.model.Learner;
import com.edusys.model.Major;
import com.edusys.model.Traineer;

import com.ui.swing.datechooser.DateChooser;
import com.ui.swing.table.Table;
import java.awt.CardLayout;

import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ADMIN
 */
public class CourseManager2 extends javax.swing.JPanel {

    private int currentIndex = 0;
    private int currentTrainnerIndex = 0;
    private CourseDAO dao = new CourseDAO();
    private MajorDAO mjDAO = new MajorDAO();
    private TraineeDAO trDAO = new TraineeDAO();
    private LearnerDAO learnerDAO = new LearnerDAO();
    List<Course> listAllCourse;
    List<Major> listAllMajor;
    private List<Traineer> ListAllTraineer;

    private List<Learner> listPossibleLearner = new ArrayList<>();
    private DefaultTableModel tblCourseModel;
    private DefaultTableModel tblTraineerModel;
    DefaultListModel listModel = new DefaultListModel();

    private DateChooser dtChooser = new DateChooser();
    private Integer maKH;
    
    

    public CourseManager2() {
        initComponents();

        init();

        isView(true);
        btnTraineerManager.setVisible(false);
    }

    public void init() {

        tblCourse.fixTable(jScrollPane2);
        tblTrainner.fixTable(jScrollPane4);
        tblCourse.fixTable(jScrollPane1);
        tblCourseModel = (DefaultTableModel) tblCourse.getModel();
        
        tblTraineerModel = (DefaultTableModel) tblTrainner.getModel();
        


        loadCourse();
        fillCourseTable(listAllCourse);

        txtFee.setEditable(false);
        txtDuration.setEditable(false);
        txtCreateDay.setEditable(false);
        txtMaNV.setEditable(false);

        listAllMajor = mjDAO.select();
        fillComboBox();

        dtChooser.setTextRefernce(txtOpeningDay);

    }

    void loadCourse() {
        listAllCourse = dao.select();
    }

    void loadTrainner() {
        ListAllTraineer = trDAO.selectByCourse(maKH.toString());
    }

    void loadPossibleLearner() {
        listPossibleLearner = learnerDAO.selectByCourse(maKH);
       /// System.out.println("length" + listPossibleLearner.size());
    }

    void filtCourse(String keyword) {
        List<Course> list = new ArrayList<>();
        keyword = keyword.toUpperCase();
        for (int i = 0; i < listAllCourse.size(); i++) {

            if (listAllCourse.get(i).info().toUpperCase().contains(keyword)) {

                list.add(listAllCourse.get(i));
            }
        }
        fillCourseTable(list);

    }
    
     void filtTraineer(String keyword) {
        List<Traineer> list = new ArrayList<>();
        keyword = keyword.toUpperCase();
        for (int i = 0; i < ListAllTraineer.size(); i++) {

            if (ListAllTraineer.get(i).toString().toUpperCase().contains(keyword)) {

                list.add(ListAllTraineer.get(i));
            }
        }
        fillTraineerTable(list);

    }
     
     
        void filtLearner(String keyword) {
        List<Learner> list = new ArrayList<>();
        keyword = keyword.toUpperCase();
        for (int i = 0; i < listPossibleLearner.size();i++){
           
            if (listPossibleLearner.get(i).toString().toUpperCase().contains(keyword)){
              
                list.add(listPossibleLearner.get(i));
            }
        }
            fillListLearner(list);

    }

    void fillCourseTable(List<Course> list) {

        tblCourseModel.setRowCount(0);
        try {
            String keyword = txtSearch.getText();

            for (Course kh : list) {
                Object[] row = {
                    kh.getMaKH(),
                    kh.getMaCD(),
                    kh.getDuration(),
                    kh.getFee(),
                    DateHelper.toString(kh.getOpeningDay(), "dd/MM/yyyy"),
                    kh.getMaNV(),
                    DateHelper.toString(kh.getCreateDay(), "dd/MM/yyyy"),};
                tblCourseModel.addRow(row);
            }
        } catch (Exception e) {
            DialogHelper.alertError(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    void fillTraineerTable(List<Traineer> list) {

        tblTraineerModel.setRowCount(0);
        try {
           // String keyword = txtSearch.getText();

            for (Traineer tr : list) {
                Object[] row = {
                    tr.getMaHV(),
                    tr.getMaNH(),
                    tr.getName(),
                    tr.getPoint()
                };
                tblTraineerModel.addRow(row);
            }
        } catch (Exception e) {
            DialogHelper.alertError(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    public void fillListLearner(List<Learner> list) {
        
        listModel.clear();
        
        for (int i = 0; i < list.size(); i++) {
            listModel.addElement(list.get(i));

        }
        lstLearner.setModel(listModel);
    }
    
    public List filtPoint(){
         tblTraineerModel.setRowCount(0);
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
            
            return listFilted;
    }

    void insert() {
        Course model = getModel();
        try {
            dao.insert(model);
            loadCourse();
            this.fillCourseTable(listAllCourse);
            this.clear();
            DialogHelper.alert(this, "Thêm mới thành công!");
        } catch (Exception e) {
            DialogHelper.alertError(this, "Thêm mới thất bại!");
        }
    }

    void insertTrainner() {

        Learner nguoiHoc = (Learner) lstLearner.getSelectedValue();
        Traineer model = new Traineer();
        model.setMaKH(maKH);
        model.setMaNH(nguoiHoc.getMaNH());
        
        try {
            String strPoint  = DialogHelper.prompt(this, "Nhập điểm");
            int point = Integer.parseInt(strPoint);
             model.setPoint(point);
            
        } catch (Exception e) {
            model.setPoint(-1);
        }
        
       
        model.setName(nguoiHoc.getName());
        try {
            trDAO.insert(model);
            loadTrainner();
            this.fillTraineerTable(ListAllTraineer);
            loadPossibleLearner();
            fillListLearner(listPossibleLearner);
        } catch (Exception e) {
            DialogHelper.alertError(this, "Lỗi thêm học viên vào khóa học!");
        }
    }

    void update() {
        Course model = getModel();
        try {
            dao.update(model);
            loadCourse();
            this.fillCourseTable(listAllCourse);
            DialogHelper.alert(this, "Cập nhật thành công!");
        } catch (Exception e) {
            e.printStackTrace();
            DialogHelper.alertError(this, "Cập nhật thất bại!");
        }
    }
    
     void updateTrainner() {
        Traineer model = getTraineerModel();
        try {
           ;
            System.out.println( trDAO.update(model));
                    
            loadTrainner();
            this.fillTraineerTable(ListAllTraineer);
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
        combobox1.setSelectedIndex(-1);

        txtCreateDay.setText(DateHelper.toString(DateHelper.now(), "dd/MM/yyyy"));
        txtMaNV.setText(AppStatus.USER.getEmpID());

        maKH = null;

    }
    
    
    void clearTr() {
        txtMaHV.setText("");
        txtMaNH.setText("");
        txtTrName.setText("");
        txtPoint.setText("");
        
    }

    void delete() {
        if (DialogHelper.confirm(this, "Bạn thực sự muốn xóa người học này?")) {
            String makh = maKH.toString();
            try {
                dao.delete(makh);
                loadCourse();
                this.fillCourseTable(listAllCourse);
                this.clear();
                DialogHelper.alert(this, "Xóa thanh công!");
            } catch (HeadlessException e) {
                DialogHelper.alertError(this, "Xóa thất bại!");
            }
        }
    }
    
     void deleteTraineer() {
        if (DialogHelper.confirm(this, "Xoá học viên khỏi khóa học này?")) {
           
            try {
                trDAO.delete(maKH.toString(),txtMaHV.getText());
                loadTrainner();
                loadPossibleLearner();
                this.fillTraineerTable(ListAllTraineer);
                fillListLearner(listPossibleLearner);
                this.clearTr();
                isUpdate(false);
                DialogHelper.alert(this, "Xóa thanh công!");
            } catch (HeadlessException e) {
                DialogHelper.alertError(this, "Xóa thất bại!");
            }
        }
    }

    void edit() {
        try {
            Integer makh = (Integer) tblCourse.getValueAt(this.currentIndex, 0);
            Course model = dao.findById(makh.toString());
            if (model != null) {
                this.setModel(model);
                isView(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            DialogHelper.alertError(this, "Lỗi truy vấn dữ liệu khi fill texxt!");
        }
    }

    void editTrainner() {
        try {
            String maNH = tblTrainner.getValueAt(this.currentTrainnerIndex, 1).toString();
            Traineer model = trDAO.findById(maNH,maKH.toString());
            if (model != null) {
                this.setModel(model);
                isUpdateTr(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            DialogHelper.alertError(this, "Lỗi truy vấn dữ liệu khi fill texxt!");
        }
    }

    void setModel(Course model) {
        txtFee.setText(String.valueOf(model.getFee()));
        txtDuration.setText(String.valueOf(model.getDuration()));

        for (int i = 0; i < listAllMajor.size(); i++) {
            if (model.getMaCD().equals(listAllMajor.get(i).getMaCD())) {
                combobox1.setSelectedIndex(i);
            }
        }

        txtOpeningDay.setText(model.getOpeningDay() != null ? DateHelper.toString(model.getOpeningDay(), "dd/MM/yyyy") : "");
        txtMaNV.setText(model.getMaNV());
        txtCreateDay.setText(DateHelper.toString(model.getCreateDay(), "dd/MM/yyyy"));
        txtNote.setText(model.getNote());

        maKH = model.getMaKH();
    }

    void setModel(Traineer model) {
        txtMaHV.setText(model.getMaHV() + "");
        txtMaNH.setText(model.getMaNH() + "");
        txtTrName.setText(model.getName());
        txtPoint.setText(model.getPoint() + "");
    }

    Course getModel() {
        Course model = new Course();
        Major major = (Major) combobox1.getSelectedItem();
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

    Traineer getTraineerModel() {

        Traineer traineer = new Traineer();
        traineer.setMaHV(Integer.valueOf(txtMaHV.getText()));
        traineer.setMaKH(maKH);
        traineer.setMaNH(txtMaNH.getText());
        traineer.setName(txtTrName.getText());
        traineer.setPoint(Integer.valueOf(txtPoint.getText()));

        return traineer;
    }

    public void setTblCourseSelected(int row) {
        tblCourse.setRowSelectionInterval(row, row);
        tblCourse.scrollRectToVisible(new Rectangle(tblCourse.getCellRect(row, 0, true)));

    }

    void isView(boolean is) {
        combobox1.setEnabled(false);
        // txtFee.setEditable(false);
        //  txtDuration.setEditable(false);
        txtOpeningDay.setEditable(false);
        txtNote.setEditable(false);

        btnTraineerManager.setVisible(true);
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
        combobox1.setEnabled(true);
        // txtFee.setEditable(true);
        //  txtDuration.setEditable(true);
        txtOpeningDay.setEditable(true);
        txtNote.setEditable(true);
        btnTraineerManager.setVisible(true);

        btnUpdate.setVisible(true);
        btnDelete.setVisible(true);
        btnCalendar.setVisible(true);

    }
    
     void isUpdateTr(boolean is) {
      
         btnDeleteTr.setVisible(is);
         btnUpdateTr.setVisible(is);
         
         

    }

    void isInsert(boolean is) {
        combobox1.setEnabled(true);
        txtFee.setEditable(true);
        //  txtDuration.setEditable(true);
        //  txtOpeningDay.setEditable(true);
        txtNote.setEditable(true);

        btnCalendar.setVisible(true);
        btnTraineerManager.setVisible(false);
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
    
    void isInsertTr(boolean is) {
       btnInsertTr.setVisible(is);
       
    }

    public void showCalendar() {
        dtChooser.showPopup();
    }

    void selectComboBox() {
        if (combobox1.getSelectedIndex() != -1) {
            Major chuyenDe = (Major) combobox1.getSelectedItem();
            txtDuration.setText(String.valueOf(chuyenDe.getDuration()));
            txtFee.setText(String.valueOf(chuyenDe.getFee()));
        }
    }

    void fillComboBox() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) combobox1.getModel();
        model.removeAllElements();
        try {

            for (Major cd : listAllMajor) {
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

        filtGrp = new javax.swing.ButtonGroup();
        pnlCourse = new javax.swing.JPanel();
        btnCalendar = new javax.swing.JButton();
        btnFirst = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblCourse = new com.ui.swing.table.Table();
        txtSearch = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnInsert = new com.ui.swing.HoverButton();
        btnNew = new com.ui.swing.HoverButton();
        btnUpdate = new com.ui.swing.HoverButton();
        btnDelete = new com.ui.swing.HoverButton();
        jLabel12 = new javax.swing.JLabel();
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
        combobox1 = new com.ui.swing.Combobox();
        btnTraineerManager = new com.ui.swing.component.Tab();
        jLabel5 = new javax.swing.JLabel();
        pnlTraineer = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblTrainner = new com.ui.swing.table.Table();
        txtSearch1 = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstLearner = new javax.swing.JList<>();
        btnInsertTr = new com.ui.swing.HoverButton();
        jLabel4 = new javax.swing.JLabel();
        txtSearch2 = new javax.swing.JTextField();
        inkwellButton2 = new com.ui.swing.InkwellButton();
        inkwellButton3 = new com.ui.swing.InkwellButton();
        jLabel25 = new javax.swing.JLabel();
        txtMaHV = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        txtMaNH = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        txtTrName = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        txtPoint = new javax.swing.JTextField();
        btnDeleteTr = new com.ui.swing.HoverButton();
        btnUpdateTr = new com.ui.swing.HoverButton();
        rdbAll = new javax.swing.JRadioButton();
        rdbHasPoint = new javax.swing.JRadioButton();
        rdbNotPoint = new javax.swing.JRadioButton();

        setLayout(new java.awt.CardLayout());

        pnlCourse.setBackground(new java.awt.Color(255, 255, 255));

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

        jLabel11.setText("Ngày khai giảng");

        jLabel6.setText("Học phí");

        txtFee.setEditable(false);

        jLabel9.setText("Thời lượng (giờ )");

        txtDuration.setEditable(false);

        jLabel7.setText("Người tạo");

        txtMaNV.setEditable(false);

        jLabel10.setText("Ngày tạo");

        txtCreateDay.setEditable(false);
        txtCreateDay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCreateDayActionPerformed(evt);
            }
        });

        jLabel2.setText("Ghi chú");

        txtNote.setColumns(20);
        txtNote.setRows(5);
        jScrollPane3.setViewportView(txtNote);

        combobox1.setLabeText("");

        btnTraineerManager.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnTraineerManagerMouseClicked(evt);
            }
        });
        btnTraineerManager.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Học Viên");
        btnTraineerManager.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        javax.swing.GroupLayout pnlCourseLayout = new javax.swing.GroupLayout(pnlCourse);
        pnlCourse.setLayout(pnlCourseLayout);
        pnlCourseLayout.setHorizontalGroup(
            pnlCourseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCourseLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(pnlCourseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCourseLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(combobox1, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlCourseLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jLabel6))
                    .addGroup(pnlCourseLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(txtFee, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlCourseLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlCourseLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(txtDuration, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlCourseLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlCourseLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(txtOpeningDay, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7)
                        .addComponent(btnCalendar, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlCourseLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jLabel7))
                    .addGroup(pnlCourseLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlCourseLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlCourseLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(txtCreateDay, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlCourseLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jLabel2))
                    .addGroup(pnlCourseLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlCourseLayout.createSequentialGroup()
                        .addComponent(btnInsert, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(17, 17, 17)
                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlCourseLayout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)
                        .addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)
                        .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)
                        .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(16, 16, 16)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1172, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnlCourseLayout.createSequentialGroup()
                .addGroup(pnlCourseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCourseLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(238, 238, 238))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCourseLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnTraineerManager, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(143, 143, 143)))
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pnlCourseLayout.setVerticalGroup(
            pnlCourseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCourseLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(pnlCourseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTraineerManager, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addComponent(jLabel12)
                .addGap(4, 4, 4)
                .addGroup(pnlCourseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCourseLayout.createSequentialGroup()
                        .addComponent(combobox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(jLabel6)
                        .addGap(4, 4, 4)
                        .addComponent(txtFee, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21)
                        .addComponent(jLabel9)
                        .addGap(4, 4, 4)
                        .addComponent(txtDuration, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13)
                        .addComponent(jLabel11)
                        .addGap(4, 4, 4)
                        .addGroup(pnlCourseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtOpeningDay, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCalendar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(7, 7, 7)
                        .addComponent(jLabel7)
                        .addGap(7, 7, 7)
                        .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(jLabel10)
                        .addGap(7, 7, 7)
                        .addComponent(txtCreateDay, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(jLabel2)
                        .addGap(13, 13, 13)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)
                        .addGroup(pnlCourseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnInsert, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(55, 55, 55)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(pnlCourseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 610, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        add(pnlCourse, "card2");

        pnlTraineer.setBackground(new java.awt.Color(255, 255, 255));
        pnlTraineer.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblTrainner.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã HV", "Mã NH", "Họ Tên", "Điểm"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblTrainner.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tblTrainner.getTableHeader().setReorderingAllowed(false);
        tblTrainner.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblTrainnerMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblTrainner);

        pnlTraineer.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 135, 770, 540));

        txtSearch1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txtSearch1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearch1ActionPerformed(evt);
            }
        });
        txtSearch1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearch1KeyReleased(evt);
            }
        });
        pnlTraineer.add(txtSearch1, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 80, 388, 33));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        pnlTraineer.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 704, 230, -1));

        jLabel3.setBackground(new java.awt.Color(204, 204, 255));
        jLabel3.setForeground(new java.awt.Color(102, 0, 255));
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/assets/Search.png"))); // NOI18N
        jLabel3.setAutoscrolls(true);
        pnlTraineer.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 80, -1, 33));

        lstLearner.setFixedCellHeight(30);
        lstLearner.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lstLearnerMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(lstLearner);

        pnlTraineer.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1210, 140, 330, 540));

        btnInsertTr.setBackground(new java.awt.Color(0, 153, 51));
        btnInsertTr.setForeground(new java.awt.Color(255, 255, 255));
        btnInsertTr.setText("Thêm");
        btnInsertTr.setBorderColor(new java.awt.Color(255, 255, 255));
        btnInsertTr.setColor(new java.awt.Color(0, 153, 51));
        btnInsertTr.setColorClick(new java.awt.Color(0, 204, 0));
        btnInsertTr.setColorOver(new java.awt.Color(0, 204, 0));
        btnInsertTr.setLableColorClick(java.awt.Color.white);
        btnInsertTr.setRadius(12);
        btnInsertTr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertTrActionPerformed(evt);
            }
        });
        pnlTraineer.add(btnInsertTr, new org.netbeans.lib.awtextra.AbsoluteConstraints(1350, 690, 90, 40));

        jLabel4.setBackground(new java.awt.Color(204, 204, 255));
        jLabel4.setForeground(new java.awt.Color(102, 0, 255));
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/assets/Search.png"))); // NOI18N
        jLabel4.setAutoscrolls(true);
        pnlTraineer.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(1220, 80, -1, 33));

        txtSearch2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txtSearch2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearch2ActionPerformed(evt);
            }
        });
        txtSearch2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearch2KeyReleased(evt);
            }
        });
        pnlTraineer.add(txtSearch2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1270, 80, 240, 33));

        inkwellButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/assets/back.png"))); // NOI18N
        pnlTraineer.add(inkwellButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1130, 420, -1, -1));

        inkwellButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/assets/back.png"))); // NOI18N
        pnlTraineer.add(inkwellButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(32, 13, -1, -1));

        jLabel25.setText("Mã học viên");
        pnlTraineer.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 90, 20));

        txtMaHV.setEditable(false);
        pnlTraineer.add(txtMaHV, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 260, 30));

        jLabel26.setText("Mã người học");
        pnlTraineer.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, 90, 20));

        txtMaNH.setEditable(false);
        pnlTraineer.add(txtMaNH, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 230, 260, 30));

        jLabel27.setText("Họ tên");
        pnlTraineer.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, 90, 20));

        txtTrName.setEditable(false);
        pnlTraineer.add(txtTrName, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 300, 260, 30));

        jLabel28.setText("Điểm");
        pnlTraineer.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 350, 90, 20));
        pnlTraineer.add(txtPoint, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 380, 260, 30));

        btnDeleteTr.setForeground(new java.awt.Color(255, 255, 255));
        btnDeleteTr.setText("Xóa");
        btnDeleteTr.setBorderColor(new java.awt.Color(255, 255, 255));
        btnDeleteTr.setColor(new java.awt.Color(255, 0, 51));
        btnDeleteTr.setColorClick(new java.awt.Color(255, 51, 0));
        btnDeleteTr.setColorOver(new java.awt.Color(255, 51, 0));
        btnDeleteTr.setLabelColor(new java.awt.Color(255, 255, 255));
        btnDeleteTr.setLableColorClick(java.awt.Color.white);
        btnDeleteTr.setRadius(12);
        btnDeleteTr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteTrActionPerformed(evt);
            }
        });
        pnlTraineer.add(btnDeleteTr, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 450, 70, 30));

        btnUpdateTr.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdateTr.setText("Lưu");
        btnUpdateTr.setBorderColor(new java.awt.Color(255, 255, 255));
        btnUpdateTr.setColor(new java.awt.Color(0, 153, 0));
        btnUpdateTr.setColorClick(new java.awt.Color(0, 255, 0));
        btnUpdateTr.setColorOver(new java.awt.Color(51, 255, 51));
        btnUpdateTr.setLabelColor(new java.awt.Color(255, 255, 255));
        btnUpdateTr.setLableColorClick(java.awt.Color.white);
        btnUpdateTr.setRadius(12);
        btnUpdateTr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateTrActionPerformed(evt);
            }
        });
        pnlTraineer.add(btnUpdateTr, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 450, 70, 30));

        filtGrp.add(rdbAll);
        rdbAll.setText("Tất cả");
        rdbAll.setContentAreaFilled(false);
        rdbAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbAllActionPerformed(evt);
            }
        });
        pnlTraineer.add(rdbAll, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 690, -1, -1));

        filtGrp.add(rdbHasPoint);
        rdbHasPoint.setText("Đã có điểm");
        rdbHasPoint.setContentAreaFilled(false);
        rdbHasPoint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbHasPointActionPerformed(evt);
            }
        });
        pnlTraineer.add(rdbHasPoint, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 690, -1, -1));

        filtGrp.add(rdbNotPoint);
        rdbNotPoint.setText("Chưa có điểm");
        rdbNotPoint.setContentAreaFilled(false);
        rdbNotPoint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbNotPointActionPerformed(evt);
            }
        });
        pnlTraineer.add(rdbNotPoint, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 690, -1, -1));

        add(pnlTraineer, "card2");
    }// </editor-fold>//GEN-END:initComponents

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        delete();
    }//GEN-LAST:event_btnDeleteActionPerformed

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
        filtCourse(txtSearch.getText());
    }//GEN-LAST:event_txtSearchKeyReleased

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchActionPerformed

    private void tblCourseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCourseMouseClicked
        currentIndex = tblCourse.rowAtPoint(evt.getPoint());
        //maKH = (Integer) tblCourse.getValueAt(currentIndex, 0);
        if (this.currentIndex >= 0) {
            maKH = Integer.valueOf(tblCourse.getValueAt(currentIndex,0).toString());
            this.edit();
            if (evt.getClickCount() == 2) {
                isUpdate(true);

            }
        }
    }//GEN-LAST:event_tblCourseMouseClicked

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        currentIndex = tblCourse.getRowCount() - 1;
        setTblCourseSelected(currentIndex);
        isUpdate(false);
        edit();
    }//GEN-LAST:event_btnLastActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        currentIndex++;
        setTblCourseSelected(currentIndex);
        isUpdate(false);
        edit();
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        currentIndex--;
        setTblCourseSelected(currentIndex);
        isUpdate(false);
        edit();
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        currentIndex = 0;
        setTblCourseSelected(currentIndex);
        isUpdate(false);
        edit();
    }//GEN-LAST:event_btnFirstActionPerformed

    private void tblTrainnerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblTrainnerMouseClicked
        currentTrainnerIndex = tblTrainner.rowAtPoint(evt.getPoint());
        editTrainner();
        isUpdateTr(true);
        
       // isInsertTr(true);
    }//GEN-LAST:event_tblTrainnerMouseClicked

    private void txtSearch1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearch1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearch1ActionPerformed

    private void txtSearch1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearch1KeyReleased
        filtTraineer(txtSearch1.getText());
    }//GEN-LAST:event_txtSearch1KeyReleased

    private void btnInsertTrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertTrActionPerformed
       insertTrainner();
       
    }//GEN-LAST:event_btnInsertTrActionPerformed

    private void txtCreateDayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCreateDayActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCreateDayActionPerformed

    private void btnCalendarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCalendarActionPerformed
        showCalendar();
    }//GEN-LAST:event_btnCalendarActionPerformed

    private void btnTraineerManagerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTraineerManagerMouseClicked
        CardLayout layout = (CardLayout) getLayout();
        loadPossibleLearner();
        loadTrainner();
        fillListLearner(listPossibleLearner);
        fillTraineerTable(ListAllTraineer);
        isInsertTr(false);
        isUpdateTr(false);
        layout.next(this);
    }//GEN-LAST:event_btnTraineerManagerMouseClicked

    private void txtSearch2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearch2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearch2ActionPerformed

    private void txtSearch2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearch2KeyReleased
        filtLearner(txtSearch2.getText());
    }//GEN-LAST:event_txtSearch2KeyReleased

    private void btnDeleteTrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteTrActionPerformed
        deleteTraineer();
    }//GEN-LAST:event_btnDeleteTrActionPerformed

    private void btnUpdateTrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateTrActionPerformed
       updateTrainner();
    }//GEN-LAST:event_btnUpdateTrActionPerformed

    private void lstLearnerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lstLearnerMouseClicked
        clearTr();
        isInsertTr(true);
    }//GEN-LAST:event_lstLearnerMouseClicked

    private void rdbAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbAllActionPerformed
        fillTraineerTable(filtPoint());
    }//GEN-LAST:event_rdbAllActionPerformed

    private void rdbHasPointActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbHasPointActionPerformed
         fillTraineerTable(filtPoint());
    }//GEN-LAST:event_rdbHasPointActionPerformed

    private void rdbNotPointActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbNotPointActionPerformed
        fillTraineerTable(filtPoint());
    }//GEN-LAST:event_rdbNotPointActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCalendar;
    private com.ui.swing.HoverButton btnDelete;
    private com.ui.swing.HoverButton btnDeleteTr;
    private javax.swing.JButton btnFirst;
    private com.ui.swing.HoverButton btnInsert;
    private com.ui.swing.HoverButton btnInsertTr;
    private javax.swing.JButton btnLast;
    private com.ui.swing.HoverButton btnNew;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private com.ui.swing.component.Tab btnTraineerManager;
    private com.ui.swing.HoverButton btnUpdate;
    private com.ui.swing.HoverButton btnUpdateTr;
    private com.ui.swing.Combobox combobox1;
    private javax.swing.ButtonGroup filtGrp;
    private com.ui.swing.InkwellButton inkwellButton2;
    private com.ui.swing.InkwellButton inkwellButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JList<Object> lstLearner;
    private javax.swing.JPanel pnlCourse;
    private javax.swing.JPanel pnlTraineer;
    private javax.swing.JRadioButton rdbAll;
    private javax.swing.JRadioButton rdbHasPoint;
    private javax.swing.JRadioButton rdbNotPoint;
    private com.ui.swing.table.Table tblCourse;
    private com.ui.swing.table.Table tblTrainner;
    private javax.swing.JTextField txtCreateDay;
    private javax.swing.JTextField txtDuration;
    private javax.swing.JTextField txtFee;
    private javax.swing.JTextField txtMaHV;
    private javax.swing.JTextField txtMaNH;
    private javax.swing.JTextField txtMaNV;
    private javax.swing.JTextArea txtNote;
    private javax.swing.JTextField txtOpeningDay;
    private javax.swing.JTextField txtPoint;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtSearch1;
    private javax.swing.JTextField txtSearch2;
    private javax.swing.JTextField txtTrName;
    // End of variables declaration//GEN-END:variables
}
