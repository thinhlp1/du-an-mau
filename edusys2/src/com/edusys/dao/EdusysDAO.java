/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edusys.dao;

import java.util.List;

/**
 *
 * @author ADMIN
 */
 abstract public class EdusysDAO<T> {

    /**
     * Thêm mới thực thể vào CSDL
     *
     * @param entity là thực thể chứa thông tin bản ghi mới
     */
    abstract public boolean insert(T entity);

    /**
     * Cập nhật thực thể vào CSDL
     *
     * @param entity là thực thể chứa thông tin bản ghi cần cập nhật
     */
    abstract public boolean update(T entity) ;
    

    /**
     * Xóa bản ghi khỏi CSDL
     *
     * @param id là mã của bản ghi cần xóa
     */
     abstract public void delete(String id);
    

    /**
     * Truy vấn tất cả các các thực thể
     *
     * @return danh sách các thực thể
     */
    abstract public List<T> select();

    /**
     * Truy vấn thực thể theo mã
     *
     * @param id là mã của bản ghi được truy vấn
     * @return thực thể chứa thông tin của bản ghi
     */
    abstract public T findById(String id);
}
 