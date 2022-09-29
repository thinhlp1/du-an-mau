/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edusys.dao;

import com.edusys.helper.JDBCHelper;
import com.edusys.model.Learner;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author ADMIN
 */
public class LearnerDAO extends EdusysDAO<Learner>{

    private final String INSERT_LEARNER = "INSERT INTO NguoiHoc (MaNH, HoTen, NgaySinh, GioiTinh, DienThoai, Email, GhiChu, MaNV) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private final String UPDATE_LEARNER = "UPDATE NguoiHoc SET HoTen=?, NgaySinh=?, GioiTinh=?, DienThoai=?, Email=?, GhiChu=?,\n"
            + "MaNV=? WHERE MaNH=?";
    private final String DELELTE_LEARNER = "DELETE FROM NguoiHoc WHERE MaNH=?";
    private final String SELECT_ALL = "SELECT * FROM NguoiHoc";
    private final String SELECT_BY_ID = "SELECT * FROM NguoiHoc WHERE MaNH=?";
    private final String SELECT_BY_NAME = "SELECT * FROM NguoiHoc WHERE HoTen LIKE ?";
    private final String SELECT_BY_COURSE = "SELECT * FROM NguoiHoc WHERE MaNH NOT IN (SELECT MaNH FROM HocVien WHERE MaKH=?)";

    public boolean insert(Learner learner) {
        int rs = JDBCHelper.executeUpdate(INSERT_LEARNER, learner.getMaNH(), learner.getName(), learner.getDob(),
                learner.getGender(), learner.getNumberPhone(),learner.getEmail(), learner.getNote(), learner.getMaNV());

        return rs > 0;
    }

    @Override
    public boolean update(Learner learner) {
        int rs = JDBCHelper.executeUpdate(UPDATE_LEARNER, learner.getName(), learner.getDob(),
                learner.getGender(), learner.getNumberPhone(),learner.getEmail(), learner.getNote(), learner.getMaNV(), learner.getMaNH());
        return rs > 0;
    }

    public void delete(String id) {
        int rs = JDBCHelper.executeUpdate(DELELTE_LEARNER, id);
    }

    public List select() {
        return select(SELECT_ALL);
    }

    public Learner findById(String id) {
        List<Learner> list = select(SELECT_BY_ID, id);
        return list.size() > 0 ? list.get(0) : null;
    }

    public List<Learner> selectByName(String keyword) {
        String sql = SELECT_BY_NAME;
        return select(sql, "%" + keyword + "%");
    }

    public List<Learner> selectByCourse(Integer makh) {
        String sql = SELECT_BY_COURSE;
        return select(sql, makh);
    }

    private List select(String sql, Object... args) {
        List<Learner> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JDBCHelper.executeQuery(sql, args);
                while (rs.next()) {
                    Learner learner = readFromResultSet(rs);
                    list.add(learner);
                }
            } finally {
                rs.getStatement().getConnection().close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return list;
    }

    private Learner readFromResultSet(ResultSet rs) throws SQLException {
        Learner learner = new Learner();

        learner.setMaNH(rs.getString("MaNH"));
        learner.setName(rs.getString("HoTen"));
        learner.setDob(rs.getDate("NgaySinh"));
        learner.setGender(rs.getBoolean("GioiTinh"));
        learner.setNumberPhone(rs.getString("DienThoai"));
        learner.setEmail(rs.getString("Email"));
        learner.setNote(rs.getString("GhiChu"));
        learner.setMaNV(rs.getString("MaNV"));
        learner.setRegistDay(rs.getDate("NgayDK"));

        return learner;

    }

}
