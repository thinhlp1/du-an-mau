/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edusys.dao;

import com.edusys.helper.JDBCHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class AppDAO {
    final static String GET_FIRST_YEAR = " SELECT TOP 1 YEAR(NgayKG) FROM KhoaHoc ORDER BY NgayKG";
    
    public static int getFirstYear() throws SQLException{
        ResultSet rs = JDBCHelper.executeQuery(GET_FIRST_YEAR);
        rs.next();
         return (int) rs.getObject(1);
        
    }
    
    
}
