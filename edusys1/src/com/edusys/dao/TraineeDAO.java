/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edusys.dao;

import com.edusys.helper.JDBCHelper;
import com.edusys.model.Traineer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author ADMIN
 */
public class TraineeDAO extends EdusysDAO<Traineer>{

    private final String INSERT_TRAINEE = "INSERT INTO HocVien(MaKH, MaNH, Diem) VALUES(?, ?, ?)";

    private final String UPDATE_TRAINEE = "UPDATE HocVien SET MaKH=?, MaNH=?, Diem=? WHERE MaHV=?";
    private final String DELELTE_TRAINEE = "DELETE FROM HocVien WHERE MaHV=? AND MaKH = ?";
    private final String SELECT_ALL = "SELECT hv.*,nh.HoTen FROM HocVien hv INNER JOIN NguoiHoc nh ON nh.MaNH=hv.MaNH"; // dont use
    private final String SELECT_BY_ID = "SELECT hv.*,nh.HoTen FROM HocVien hv INNER JOIN NguoiHoc nh ON nh.MaNH=hv.MaNH WHERE hv.MaNH = ? AND hv.MaKH = ?";
    private final String SELECT_BY_COURSE = "SELECT hv.*,nh.HoTen FROM HocVien hv INNER JOIN NguoiHoc nh ON nh.MaNH=hv.MaNH WHERE hv.MaKH=?";
    private final String SELECT_BY_COURSE_A = "SELECT hv.*,nh.HoTen FROM HocVien hv INNER JOIN NguoiHoc nh ON nh.MaNH=hv.MaNH WHERE hv.MaNH NOT IN (?)";

    public boolean insert(Traineer traineer) {
        int rs = JDBCHelper.executeUpdate(INSERT_TRAINEE, traineer.getMaKH(), traineer.getMaNH(), traineer.getPoint());

        return rs > 0;
    }

    public boolean update(Traineer traineer) {
        int rs = JDBCHelper.executeUpdate(UPDATE_TRAINEE, traineer.getMaKH(), traineer.getMaNH(), traineer.getPoint(), traineer.getMaHV());
        return rs > 0;
    }

    public boolean delete(String maKH,String maHV ) {
        int rs = JDBCHelper.executeUpdate(DELELTE_TRAINEE, maHV,maKH);
        return rs > 0;
    }
    
     @Override
    public void delete(String id) {
      
    }

    public List select() {
        return select(SELECT_ALL);
    }
    
    public List selectAnotherTraineer(String maKH){
        return select(SELECT_BY_COURSE_A,maKH);
    }

    public Traineer findById(String id, String maKH) {
        List<Traineer> list = select(SELECT_BY_ID, id,maKH);
        return list.size() > 0 ? list.get(0) : null;
    }
    
    public Traineer findById(String id) {
        List<Traineer> list = select(SELECT_BY_ID, id);
        return list.size() > 0 ? list.get(0) : null;
    }


    public List<Traineer> selectByCourse(String maKH) {
        return select(SELECT_BY_COURSE, maKH);
    }

    private List select(String sql, Object... args) {
        List<Traineer> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JDBCHelper.executeQuery(sql, args);
                while (rs.next()) {
                    Traineer traineer = readFromResultSet(rs);
                    list.add(traineer);
                }
            } finally {
                rs.getStatement().getConnection().close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return list;
    }

    private Traineer readFromResultSet(ResultSet rs) throws SQLException {
        Traineer traineer = new Traineer();

        traineer.setMaHV(rs.getInt("MaHV"));
        traineer.setMaKH(rs.getInt("MaKH"));
        traineer.setMaNH(rs.getString("MaNH"));
        traineer.setPoint(rs.getDouble("Diem"));
        traineer.setName(rs.getString("HoTen"));

        return traineer;

    }

   

}
