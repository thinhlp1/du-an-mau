/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edusys.dao;

import com.edusys.helper.JDBCHelper;
import com.edusys.model.Major;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author ADMIN
 */
public class MajorDAO extends EdusysDAO<Major>{

    private final String INSERT_MAJOR = "INSERT INTO ChuyenDe (MaCD, TenCD, HocPhi, ThoiLuong, Hinh, MoTa) VALUES (?, ?, ?, ?, ?, ?)";
    private final String UPDATE_MAJOR = "UPDATE ChuyenDe SET TenCD=?, HocPhi=?, ThoiLuong=?, Hinh=?, MoTa=? WHERE MaCD=?";
    private final String DELELTE_MAJOR = "DELETE FROM ChuyenDe WHERE MaCD=?";
    private final String SELECT_ALL = "SELECT * FROM ChuyenDe";
    private final String SELECT_BY_ID = "SELECT * FROM ChuyenDe WHERE MaCD=?";
    
    private final String SELECT_BYNAME ="" ;

    @Override
    public boolean insert(Major major) {
        int rs = JDBCHelper.executeUpdate(INSERT_MAJOR, major.getMaCD(), major.getName(), major.getFee(), major.getDuration(), major.getPicture(), major.getDescription());

        return rs > 0;
    }

    @Override
    public boolean update(Major major) {
        int rs = JDBCHelper.executeUpdate(UPDATE_MAJOR, major.getName(), major.getFee(), major.getDuration(), major.getPicture(), major.getDescription(),major.getMaCD());
        return rs > 0;
    }

    @Override
    public void delete(String id) {
        int rs = JDBCHelper.executeUpdate(DELELTE_MAJOR, id);
    }

    @Override
    public List select() {
        return select(SELECT_ALL);
    }

    @Override
    public Major findById(String id) {
        List<Major> list = select(SELECT_BY_ID, id);
        return list.size() > 0 ? list.get(0) : null;
    }

    private List select(String sql, Object... args) {
        List<Major> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JDBCHelper.executeQuery(sql, args);
                while (rs.next()) {
                    Major major = readFromResultSet(rs);
                    list.add(major);
                }
            } finally {
                rs.getStatement().getConnection().close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return list;
    }

    private Major readFromResultSet(ResultSet rs) throws SQLException {
        Major major = new Major();
   
        major.setMaCD(rs.getString("MaCD"));
        major.setPicture(rs.getString("Hinh"));
        major.setFee(rs.getDouble("HocPhi"));
        major.setDescription(rs.getString("MoTa"));
        major.setName(rs.getString("TenCD"));
        major.setDuration(rs.getInt("ThoiLuong"));
        
        return major;
        
        
    }

}


