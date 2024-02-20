package com.ra.service.impl;

import com.ra.entity.Category;
import com.ra.service.CategoryService;
import com.ra.util.MySQLConnect;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryServiceImpl implements CategoryService {
    @Override
    public List<Category> findAll() {
        List<Category> result = new ArrayList<>();
        Connection conn = null;
        try{
            conn = MySQLConnect.open();
            String sql = "{call cat_getAllCategories()}";
           // PreparedStatement statement = conn.prepareStatement("select * from Categories");
            CallableStatement statement = conn.prepareCall(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                Category category = new Category();
                category.setCategoryID(rs.getInt("CategoryID"));
                category.setCategoryName(rs.getString("CategoryName"));
                category.setDescription(rs.getString("Description"));
                category.setParentCategoryID(rs.getInt("ParentCategoryID"));
                result.add(category);
            }
        }
        catch(Exception e){
            e.printStackTrace();
            MySQLConnect.close(conn);
        }
        return result;
    }

    @Override
    public List<Category> findByName(String name) {
        List<Category> result = new ArrayList<>();
        Connection conn = null;
        try{
            conn = MySQLConnect.open();
            PreparedStatement statement = conn.prepareStatement("select * from Categories where CategoryName like ?");
            statement.setString(1,"%"+name+"%");//%: wildcard character (ký tự đại diện)
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                Category category = new Category();
                category.setCategoryID(rs.getInt("CategoryID"));
                category.setCategoryName(rs.getString("CategoryName"));
                category.setDescription(rs.getString("Description"));
                category.setParentCategoryID(rs.getInt("ParentCategoryID"));
                result.add(category);
            }
        }
        catch(Exception e){
            e.printStackTrace();
            MySQLConnect.close(conn);
        }
        return result;
    }

    @Override
    public Category insert(Category category) {
        Connection conn = null;
        try{
            conn = MySQLConnect.open();
            //String sql = "insert into Categories(CategoryName,Description,ParentCategoryID) values(?,?,?)";
            String sql = "{call cate_add(?,?,?,?)}";
            //PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            CallableStatement statement = conn.prepareCall(sql);
            statement.registerOutParameter(1, Types.INTEGER);
            statement.setString(2,category.getCategoryName());//%: wildcard character (ký tự đại diện)
            statement.setString(3,category.getDescription());//%: wildcard character (ký tự đại diện)
            statement.setInt(4,category.getParentCategoryID());//%: wildcard character (ký tự đại diện)
            int result = statement.executeUpdate();
            if(result > 0){
//                try(ResultSet generatedKeys = statement.getGeneratedKeys()){
//                    if(generatedKeys.next()){
//                        category.setCategoryID(generatedKeys.getInt(1));
//                    }
//                    else{
//                        throw new Exception("Creating category failed, no ID obtained.");
//                    }
//                }
                category.setCategoryID(statement.getInt(1));
            }
            return category;

        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            MySQLConnect.close(conn);
        }
        return null;
    }

    @Override
    public void update(Category category) {
        Connection conn = null;
        try{
            conn = MySQLConnect.open();
            String sql = "update Categories set CategoryName = ?, Description = ?, ParentCategoryID = ? where CategoryID = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1,category.getCategoryName());
            statement.setString(2,category.getDescription());
            statement.setInt(3,category.getParentCategoryID());
            statement.setInt(4,category.getCategoryID());
            statement.executeUpdate();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            MySQLConnect.close(conn);
        }

    }

    @Override
    public void delete(int id) {
        Connection conn = null;
        try{
            conn = MySQLConnect.open();
            String sql = "delete from Categories where CategoryID = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1,id);
            statement.executeUpdate();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            MySQLConnect.close(conn);
        }


    }

    @Override
    public Category findById(int id) {
        Connection conn = null;
        try{
            conn = MySQLConnect.open();
           // String sql = "select * from Categories where CategoryID = ?";
            String sql = "{call find_categoryById(?)}";
//            PreparedStatement statement = conn.prepareStatement(sql);
            CallableStatement statement = conn.prepareCall(sql);
            statement.setInt(1,id);
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                Category category = new Category();
                category.setCategoryID(rs.getInt("CategoryID"));
                category.setCategoryName(rs.getString("CategoryName"));
                category.setDescription(rs.getString("Description"));
                category.setParentCategoryID(rs.getInt("ParentCategoryID"));
                return category;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            MySQLConnect.close(conn);
        }
        return null;
    }

    @Override
    public void updateById(int id) {
        Connection conn = null;
        try{
            conn = MySQLConnect.open();
            String sql = "update Categories set CategoryName = ? where CategoryID = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1,"CategoryName");
            statement.setInt(2,id);
            statement.executeUpdate();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            MySQLConnect.close(conn);
        }
    }
}
