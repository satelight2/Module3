package com.ra.service.impl;

import com.ra.entity.Category;
import com.ra.service.CategoryService;
import com.ra.util.MySQLConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CategoryServiceImpl implements CategoryService {
    @Override
    public List<Category> findAll() {
        List<Category> result = new ArrayList<>();
        Connection conn = null;
        try{
            conn = MySQLConnect.open();
            PreparedStatement statement = conn.prepareStatement("select * from Categories");
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
            String sql = "insert into Categories(CategoryName,Description,ParentCategoryID) values(?,?,?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1,category.getCategoryName());//%: wildcard character (ký tự đại diện)
            statement.setString(2,category.getDescription());//%: wildcard character (ký tự đại diện)
            statement.setInt(3,category.getParentCategoryID());//%: wildcard character (ký tự đại diện)
            int result = statement.executeUpdate();
            if(result > 0){
                try(ResultSet generatedKeys = statement.getGeneratedKeys()){
                    if(generatedKeys.next()){
                        category.setCategoryID(generatedKeys.getInt(1));
                    }
                    else{
                        throw new Exception("Creating category failed, no ID obtained.");
                    }
                }
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
}
