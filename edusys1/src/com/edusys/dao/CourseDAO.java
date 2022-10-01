/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edusys.dao;

import com.edusys.helper.JDBCHelper;
import com.edusys.model.Course;
import com.edusys.model.Course;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author ADMIN
 */
public class CourseDAO {

    private final String INSERT_COURSE = "INSERT INTO KhoaHoc (MaCD, HocPhi, ThoiLuong, NgayKG, GhiChu, MaNV) VALUES (?, ?, ?, ?, ?, ?)";

    private final String UPDATE_COURSE = "UPDATE KhoaHoc SET MaCD=?, HocPhi=?, ThoiLuong=?, NgayKG=?, GhiChu=?, MaNV=? WHERE\n"
            + "MaKH=?";
    private final String DELELTE_COURSE = "DELETE FROM KhoaHoc WHERE MaKH=?";
    private final String SELECT_ALL = "SELECT * FROM KhoaHoc";
    private final String SELECT_BY_ID = "SELECT * FROM KhoaHoc WHERE MaKH=?";
   

    public boolean insert(Course course) {
        int rs = JDBCHelper.executeUpdate(INSERT_COURSE, course.getMaCD(), course.getFee(), course.getDuration(),
                course.getOpeningDay(), course.getNote(), course.getMaNV());

        return rs > 0;
    }

    public boolean update(Course course) {
        int rs = JDBCHelper.executeUpdate(UPDATE_COURSE, course.getMaCD(), course.getFee(), course.getDuration(),
                course.getOpeningDay(), course.getNote(), course.getMaNV(), course.getMaKH());
        return rs > 0;
    }

    public void delete(String id) {
        int rs = JDBCHelper.executeUpdate(DELELTE_COURSE, id);
    }

    public List select() {
        return select(SELECT_ALL);
    }

    public Course findById(String id) {
        List<Course> list = select(SELECT_BY_ID, id);
        return list.size() > 0 ? list.get(0) : null;
    }

    private List select(String sql, Object... args) {
        List<Course> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JDBCHelper.executeQuery(sql, args);
                while (rs.next()) {
                    Course course = readFromResultSet(rs);
                    list.add(course);
                }
            } finally {
                rs.getStatement().getConnection().close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return list;
    }

    private Course readFromResultSet(ResultSet rs) throws SQLException {
        Course course = new Course();

        course.setMaKH(rs.getInt("MaKH"));
        course.setFee(rs.getDouble("HocPhi"));
        course.setDuration(rs.getInt("ThoiLuong"));
        course.setOpeningDay(rs.getDate("NgayKG"));
        course.setNote(rs.getString("GhiChu"));
       
        course.setMaNV(rs.getString("MaNV"));
        course.setCreateDay(rs.getDate("NgayTao"));
        course.setMaCD(rs.getString("MaCD"));

        return course;

    }

}
