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
import java.sql.SQLException;

/**
 *
 * @author ADMIN
 */
public class StatiscalDAO {

    public List<Object[]> getLearnerStc() {
        List<Object[]> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                String sql = "{call sp_ThongKeNguoiHoc}";
                rs = JDBCHelper.executeQuery(sql);

                while (rs.next()) {
                    Object[] model = {
                        rs.getInt("Nam"),
                        rs.getInt("SoLuong"),
                        rs.getDate("DauTien"),
                        rs.getDate("CuoiCung")
                    };
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

    public List<Object[]> getPoint(Integer makh) {
        List<Object[]> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                String sql = "{call sp_BangDiem (?)}";
                rs = JDBCHelper.executeQuery(sql, makh);
                while (rs.next()) {
                    double point = rs.getDouble("Diem");
                    String rank = "Xuất sắc ";

                    if (point < 0) {
                        rank = "Chưa nhập";
                    } else if (point < 3) {
                        rank = "Kém";

                    } else if (point < 5) {
                        rank = "Yếu";

                    } else if (point < 6.5) {

                        rank = "Trung bình";

                    } else if (point < 7.5) {
                        rank = "Khá";

                    } else if (point < 9) {
                        rank = "Giỏi";

                    }
                    Object[] model = {
                        rs.getString("MaNH"),
                        rs.getString("HoTen"),
                        point,
                        rank
                    };
                    list.add(model);
                }
            } finally {
                rs.getStatement().getConnection().close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        return list;
    }

    public List<Object[]> getPointByMajor() {
        List<Object[]> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                String sql = "{call sp_ThongKeDiem}";
                rs = JDBCHelper.executeQuery(sql);
                while (rs.next()) {
                    Object[] model = {
                        rs.getString("ChuyenDe"),
                        rs.getInt("SoHV"),
                        rs.getDouble("ThapNhat"),
                        rs.getDouble("CaoNhat"),
                        rs.getDouble("TrungBinh")
                    };
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

    public List<Object[]> getRevenue(int nam) {
        List<Object[]> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                String sql = "{call sp_ThongKeDoanhThu (?)}";
                rs = JDBCHelper.executeQuery(sql, nam);
                while (rs.next()) {
                    int trainner = rs.getInt("SoHV");
                    double revenue = rs.getDouble("DoanhThu");
                    if (trainner == 0){
                        revenue = 0;
                    }
                    Object[] model = {
                        rs.getString("ChuyenDe"),
                        rs.getInt("SoKH"),
                         trainner,
                        revenue,
                         rs.getDouble("ThapNhat"),
                         rs.getDouble("CaoNhat"),
                         rs.getDouble("TrungBinh")
                    };  
                    
                    list.add(model);
                }
            } finally {
                rs.getStatement().getConnection().close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}


