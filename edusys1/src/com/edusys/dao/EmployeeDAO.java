/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edusys.dao;

import com.edusys.helper.JDBCHelper;
import com.edusys.model.Employee;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author ADMIN
 */
public class EmployeeDAO extends EdusysDAO<Employee>{

    private final String INSERT_EMP = "INSERT INTO NhanVien (MaNV, MatKhau, HoTen, VaiTro) VALUES (?, ?, ?, ?)";
    private final String UPDATE_EMP = "UPDATE NhanVien SET MatKhau=?, HoTen=?, VaiTro=? WHERE MaNV=?";
    private final String DELELTE_EMP = "DELETE FROM NhanVien WHERE MaNV=?";
    private final String SELECT_ALL = "SELECT * FROM NhanVien";
    private final String SELECT_BY_ID = "SELECT * FROM NhanVien WHERE MaNV=?";


    @Override
    public boolean insert(Employee employee) {
        int rs = JDBCHelper.executeUpdate(INSERT_EMP, employee.getEmpID(),employee.getPassword(),employee.getName(),employee.isLore());
        return rs > 0;
    }


    @Override
    public boolean update(Employee employee) {
        int rs = JDBCHelper.executeUpdate(UPDATE_EMP, employee.getPassword(),employee.getName(),employee.isLore(),employee.getEmpID());
        return rs > 0;
    }


    @Override
    public void delete(String id) {
        int rs = JDBCHelper.executeUpdate(DELELTE_EMP, id);
    }


    @Override
    public List select() {
      return select(SELECT_ALL);
    }


    @Override
    public Employee findById(String id) {
          List<Employee> list = select(SELECT_BY_ID, id);
         return list.size() > 0 ? list.get(0) : null;
    }

    private List select(String sql, Object... args) {
        List<Employee> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JDBCHelper.executeQuery(sql, args);
                while (rs.next()) {
                    Employee model = readFromResultSet(rs);
                    list.add(model);
                }
            } finally {
                rs.getStatement().getConnection().close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return list;
    }

    private Employee readFromResultSet(ResultSet rs) throws SQLException {
        Employee model = new Employee();
        model.setEmpID(rs.getString("MaNV"));
        model.setPassword(rs.getString("MatKhau"));
        model.setName(rs.getString("HoTen"));
        model.setLore(rs.getBoolean("VaiTro"));
        return model;
    }

}
